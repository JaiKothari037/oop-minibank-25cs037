import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

// ==========================================
// PRACTICAL 7: ANNOTATIONS & REFLECTION
// ==========================================
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Id {}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Positive {
    String message() default "must be > 0";
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface MaxLength {
    int value();
}

class AnnotationValidator {
    public static String[] validate(Object obj) {
        List<String> errors = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.isAnnotationPresent(Positive.class)) {
                    Positive pos = field.getAnnotation(Positive.class);
                    if (field.getType() == long.class || field.getType() == double.class) {
                        long val = field.getLong(obj);
                        if (val <= 0) {
                            errors.add(field.getName() + " " + pos.message());
                        }
                    }
                }
                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength max = field.getAnnotation(MaxLength.class);
                    if (field.getType() == String.class) {
                        String val = (String) field.get(obj);
                        if (val != null && val.length() > max.value()) {
                            errors.add(field.getName() + " exceeds max length " + max.value());
                        }
                    }
                }
            } catch (Exception e) {
                // Ignore reflection access errors for simple runtime check
            }
        }
        return errors.toArray(new String[0]);
    }
}

// ==========================================
// PRACTICAL 8: CUSTOM EXCEPTIONS
// ==========================================
class BankException extends Exception {
    public BankException(String msg) { super(msg); }
}

class InsufficientFundsException extends BankException {
    private final long shortfall;
    public InsufficientFundsException(long shortfall) {
        super("Insufficient funds! Shortfall: " + shortfall);
        this.shortfall = shortfall;
    }
    public long getShortfall() { return shortfall; }
}

class AccountNotFoundException extends BankException {
    public AccountNotFoundException(String accNo) {
        super("Account not found: " + accNo);
    }
}

class InvalidAmountException extends BankException {
    public InvalidAmountException(String msg) {
        super(msg);
    }
}

// ==========================================
// PRACTICAL 6: INTERFACES
// ==========================================
interface Transactable {
    void deposit(long amount) throws InvalidAmountException;
    boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException;
}

interface InterestBearing {
    double interestRate();
    default double yearlyInterest(long balance) {
        return balance * (interestRate() / 100.0);
    }
}

@FunctionalInterface
interface WithdrawRule {
    boolean allow(Account account, long amount);
}

interface Premium {} // Marker interface

// ==========================================
// PRACTICAL 2, 3, 5, 9, 11, 12: MODEL CLASSES
// ==========================================
class Customer implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    
    @MaxLength(30)
    private String name;
    private String email;
    private String mobile;
    
    @Id
    private String customerId;
    private static long customerCounter = 100;
    private Address address;

    public static class Address implements Serializable {
        private static final long serialVersionUID = 1L;
        private String line;
        private String city;
        private String pincode;

        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }
        public String getLine() { return line; }
        public String getCity() { return city; }
        public String getPincode() { return pincode; }
        
        @Override
        public String toString() {
            return line + ", " + city + " - " + pincode;
        }
    }

    public Customer(String name, String email, String mobile, Address address) {
        generateCustomerId();
        this.customerId = "CUST" + customerCounter;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
    }

    private static synchronized void generateCustomerId() {
        customerCounter++;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public String getCustomerId() { return customerId; }
    public Address getAddress() { return address; }

    @Override
    public Customer clone() {
        try {
            return (Customer) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Customer(this.name, this.email, this.mobile, this.address);
        }
    }
}

abstract class Account implements Transactable, InterestBearing, Serializable, Comparable<Account> {
    private static final long serialVersionUID = 1L;

    @Id
    private final String accountNumber;
    private String ownerName;
    
    @Positive
    protected long balance;
    private boolean active;
    private static int counter = 0;

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public Account(String ownerName, long balance) {
        synchronized(Account.class) {
            counter++;
            this.accountNumber = "AC" + String.format("%04d", counter);
        }
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }
    public boolean isActive() { return active; }

    public abstract boolean canWithdraw(long amount);

    @Override
    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive.");
        }
        balance += amount;
    }

    @Override
    public synchronized boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }
        if (!canWithdraw(amount)) {
            throw new InsufficientFundsException(amount - balance);
        }
        balance -= amount;
        return true;
    }

    public synchronized void transfer(Account to, long amount) throws BankException {
        if (to == null) throw new AccountNotFoundException("Target");
        // Lock ordering by accountNumber to avoid deadlocks
        Account first = this.accountNumber.compareTo(to.accountNumber) < 0 ? this : to;
        Account second = this.accountNumber.compareTo(to.accountNumber) < 0 ? to : this;

        synchronized (first) {
            synchronized (second) {
                this.withdraw(amount);
                to.deposit(amount);
            }
        }
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | Bal: " + balance + " | Type: " + getClass().getSimpleName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }
}

class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;
    private long minBalance = 1000;

    public SavingsAccount(String ownerName, long balance) {
        super(ownerName, balance);
    }

    @Override
    public double interestRate() { return 4.0; }

    @Override
    public boolean canWithdraw(long amount) {
        return (balance - amount) >= minBalance;
    }
}

class CurrentAccount extends Account implements Premium {
    private static final long serialVersionUID = 1L;
    private long overdraftLimit = 5000;

    public CurrentAccount(String ownerName, long balance) {
        super(ownerName, balance);
    }

    @Override
    public double interestRate() { return 0.0; }

    @Override
    public boolean canWithdraw(long amount) {
        return (balance - amount) >= -overdraftLimit;
    }
}

class FixedDepositAccount extends Account {
    private static final long serialVersionUID = 1L;

    public FixedDepositAccount(String ownerName, long balance) {
        super(ownerName, balance);
    }

    @Override
    public double interestRate() { return 7.0; }

    @Override
    public boolean canWithdraw(long amount) {
        return false; // FD locked
    }
}

// ==========================================
// PRACTICAL 4: VALIDATION & PARSING
// ==========================================
class Validator {
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9]\\d{9}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PAN_PATTERN = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
    private static final Pattern IFSC_PATTERN = Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");

    public static boolean isValidMobile(String mobile) {
        return mobile != null && MOBILE_PATTERN.matcher(mobile).matches();
    }
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    public static boolean isValidPan(String pan) {
        return pan != null && PAN_PATTERN.matcher(pan).matches();
    }
    public static boolean isValidIfsc(String ifsc) {
        return ifsc != null && IFSC_PATTERN.matcher(ifsc).matches();
    }
}

enum TransactionType { DEPOSIT, WITHDRAW, TRANSFER }

record Command(TransactionType type, String accountNumber, long amount) {}

class CommandParser {
    public static Command parse(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.trim().split("\\s+");
        if (parts.length < 3) return null;
        try {
            TransactionType type = TransactionType.valueOf(parts[0].toUpperCase());
            String acc = parts[1];
            long amt = Long.parseLong(parts[2]);
            return new Command(type, acc, amt);
        } catch (Exception e) {
            return null;
        }
    }
}

class StatementFormatter {
    public static String buildStatement(Account account) {
        StringBuilder sb = new StringBuilder();
        sb.append("====================================\n");
        sb.append("         ACCOUNT STATEMENT          \n");
        sb.append("====================================\n");
        sb.append("Account Number : ").append(account.getAccountNumber()).append("\n");
        sb.append("Owner Name     : ").append(account.getOwnerName()).append("\n");
        sb.append("Account Type   : ").append(account.getClass().getSimpleName()).append("\n");
        sb.append("Current Balance: ").append(account.getBalance()).append(" INR\n");
        sb.append("Interest Rate  : ").append(account.interestRate()).append("%\n");
        sb.append("Est. Yearly Int: ").append(account.yearlyInterest(account.getBalance())).append(" INR\n");
        sb.append("====================================\n");
        return sb.toString();
    }
}

// ==========================================
// PRACTICAL 11: FILE I/O & REPORTING
// ==========================================
class StatePersister {
    public static void save(List<Account> accounts, Path file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
            oos.writeObject(accounts);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Account> load(Path file) throws IOException, ClassNotFoundException {
        if (!Files.exists(file)) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))) {
            return (List<Account>) ois.readObject();
        }
    }
}

class TransactionLog {
    public static synchronized void append(Path file, String line) {
        try {
            if (file.getParent() != null) Files.createDirectories(file.getParent());
            Files.writeString(file, line + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Failed to write to log: " + e.getMessage());
        }
    }
}

class ReportGenerator {
    public static String generateReport(Path logsDir) throws IOException {
        long totalDeposits = 0;
        long totalWithdrawals = 0;
        int count = 0;

        if (Files.exists(logsDir) && Files.isDirectory(logsDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsDir, "*.log")) {
                for (Path entry : stream) {
                    List<String> lines = Files.readAllLines(entry);
                    for (String line : lines) {
                        Command cmd = CommandParser.parse(line);
                        if (cmd != null) {
                            count++;
                            if (cmd.type() == TransactionType.DEPOSIT) totalDeposits += cmd.amount();
                            else if (cmd.type() == TransactionType.WITHDRAW) totalWithdrawals += cmd.amount();
                        }
                    }
                }
            }
        }
        return "=== END OF DAY REPORT ===\nTotal Transactions: " + count + 
               "\nTotal Deposited   : " + totalDeposits + 
               "\nTotal Withdrawn   : " + totalWithdrawals + 
               "\nNet Change        : " + (totalDeposits - totalWithdrawals) + "\n";
    }
}

// ==========================================
// PRACTICAL 10: THREAD POOL ENGINE
// ==========================================
class TransactionProcessor {
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public void submit(Runnable task) {
        executor.execute(task);
    }

    public void stop() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}

// ==========================================
// PRACTICAL 13: GENERIC REPOSITORY
// ==========================================
class Repository<T> {
    private final Map<String, T> storage = new ConcurrentHashMap<>();

    public T save(T entity) {
        String id = extractId(entity);
        if (id != null) {
            storage.put(id, entity);
            return entity;
        }
        return null;
    }

    public T findById(String id) {
        return storage.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) save(item);
    }

    private String extractId(T entity) {
        for (Field field : entity.getClass().getSuperclass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                try {
                    return (String) field.get(entity);
                } catch (Exception ignored) {}
            }
        }
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                try {
                    return (String) field.get(entity);
                } catch (Exception ignored) {}
            }
        }
        return null;
    }

    public static <N extends Number> double average(N[] values) {
        double sum = 0;
        for (N val : values) sum += val.doubleValue();
        return values.length == 0 ? 0 : sum / values.length;
    }
}

// ==========================================
// PRACTICAL 14: BANK SERVICE LAYER
// ==========================================
class BankService {
    private final Repository<Account> accountRepo = new Repository<>();
    private final Path storageFile = Paths.get("accounts.dat");
    private final Path logFile = Paths.get("logs", "transactions.log");

    public BankService() {
        loadData();
    }

    public Account openAccount(String type, String ownerName, long initialBalance) throws BankException {
        if (initialBalance < 0) throw new InvalidAmountException("Opening balance cannot be negative.");
        
        Account acc = switch (type.toLowerCase()) {
            case "savings", "1" -> new SavingsAccount(ownerName, initialBalance);
            case "current", "2" -> new CurrentAccount(ownerName, initialBalance);
            case "fixed", "3" -> new FixedDepositAccount(ownerName, initialBalance);
            default -> new SavingsAccount(ownerName, initialBalance);
        };

        String[] errs = AnnotationValidator.validate(acc);
        if (errs.length > 0) {
            throw new BankException("Validation failed: " + String.join(", ", errs));
        }

        accountRepo.save(acc);
        TransactionLog.append(logFile, "OPEN " + acc.getAccountNumber() + " " + initialBalance);
        return acc;
    }

    public void deposit(String accNo, long amount) throws BankException {
        Account acc = getAccount(accNo);
        acc.deposit(amount);
        TransactionLog.append(logFile, "DEPOSIT " + accNo + " " + amount);
    }

    public void withdraw(String accNo, long amount) throws BankException {
        Account acc = getAccount(accNo);
        acc.withdraw(amount);
        TransactionLog.append(logFile, "WITHDRAW " + accNo + " " + amount);
    }

    public void transfer(String fromAcc, String toAcc, long amount) throws BankException {
        Account src = getAccount(fromAcc);
        Account dest = getAccount(toAcc);
        src.transfer(dest, amount);
        TransactionLog.append(logFile, "TRANSFER " + fromAcc + " " + amount);
    }

    public Account getAccount(String accNo) throws AccountNotFoundException {
        Account acc = accountRepo.findById(accNo);
        if (acc == null) throw new AccountNotFoundException(accNo);
        return acc;
    }

    public List<Account> listAccountsSortedByBalance() {
        List<Account> list = accountRepo.findAll();
        list.sort(Comparator.comparingLong(Account::getBalance).reversed());
        return list;
    }

    public void saveData() {
        try {
            StatePersister.save(accountRepo.findAll(), storageFile);
        } catch (IOException e) {
            System.err.println("Error persisting data: " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            List<Account> list = StatePersister.load(storageFile);
            for (Account acc : list) {
                accountRepo.save(acc);
            }
        } catch (Exception e) {
            System.out.println("Starting with fresh account database.");
        }
    }
}

// ==========================================
// PRACTICAL 1: MAIN ENTRY & MENU SHELL
// ==========================================
public class MiniBank {
    public record BankInfo(String name, String branch) {}
    public enum MenuOption { OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, STATEMENT, REPORT, EXIT }

    public static void main(String[] args) {
        BankInfo bank = new BankInfo("HDFC Bank", "Ahmedabad Branch");
        System.out.println("====================================");
        System.out.println(" Welcome to " + bank.name() + " (" + bank.branch() + ")");
        System.out.println("====================================");

        BankService service = new BankService();
        Scanner sc = new Scanner(System.in);
        int chosen = 0;

        do {
            System.out.println("\nMENU");
            System.out.println("1. OPEN_ACCOUNT");
            System.out.println("2. DEPOSIT");
            System.out.println("3. WITHDRAW");
            System.out.println("4. TRANSFER");
            System.out.println("5. PRINT STATEMENT");
            System.out.println("6. GENERATE REPORT");
            System.out.println("7. EXIT");
            System.out.print("Enter choice: ");

            if (!sc.hasNextInt()) {
                sc.next();
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            chosen = sc.nextInt();

            try {
                switch (chosen) {
                    case 1 -> {
                        System.out.println("Select Account Type: 1. Savings  2. Current  3. Fixed Deposit");
                        String type = sc.next();
                        System.out.print("Enter owner name: ");
                        String name = sc.next();
                        System.out.print("Enter initial balance: ");
                        long balance = sc.nextLong();
                        Account acc = service.openAccount(type, name, balance);
                        System.out.println("Account Created Successfully! Account No: " + acc.getAccountNumber());
                    }
                    case 2 -> {
                        System.out.print("Enter account number: ");
                        String accNo = sc.next();
                        System.out.print("Enter amount to deposit: ");
                        long amt = sc.nextLong();
                        service.deposit(accNo, amt);
                        System.out.println("Deposit successful!");
                    }
                    case 3 -> {
                        System.out.print("Enter account number: ");
                        String accNo = sc.next();
                        System.out.print("Enter amount to withdraw: ");
                        long amt = sc.nextLong();
                        service.withdraw(accNo, amt);
                        System.out.println("Withdrawal successful!");
                    }
                    case 4 -> {
                        System.out.print("Enter sender account number: ");
                        String from = sc.next();
                        System.out.print("Enter receiver account number: ");
                        String to = sc.next();
                        System.out.print("Enter amount to transfer: ");
                        long amt = sc.nextLong();
                        service.transfer(from, to, amt);
                        System.out.println("Transfer successful!");
                    }
                    case 5 -> {
                        System.out.print("Enter account number: ");
                        String accNo = sc.next();
                        Account acc = service.getAccount(accNo);
                        System.out.println(StatementFormatter.buildStatement(acc));
                    }
                    case 6 -> {
                        String report = ReportGenerator.generateReport(Paths.get("logs"));
                        System.out.println(report);
                    }
                    case 7 -> {
                        service.saveData();
                        System.out.println("Data persisted successfully.");
                    }
                    default -> System.out.println("Invalid menu choice. Try again.");
                }
            } catch (BankException e) {
                System.err.println("Transaction Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }

        } while (chosen != 7);

        System.out.println("Goodbye! Thank you for using MiniBank.");
        sc.close();
    }
}