class OutOfStockException extends Exception
{
    private final int shortfall;

    public OutOfStockException(String message, int shortfall)
    {
        super(message);
        this.shortfall = shortfall;
    }

    public int getShortfall()
    {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception
{
    public InvalidQuantityException(String message)
    {
        super(message);
    }
}

class Warehouse
{
    private int stock = 10;

    public void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException
    {
        if (qty <= 0)
        {
            throw new InvalidQuantityException("quantity must be positive, got: " + qty);
        }

        if (qty > stock)
        {
            throw new OutOfStockException("insufficient stock for " + item, qty - stock);
        }

        stock -= qty;
        System.out.println("issued " + qty + " of " + item + " | remaining stock: " + stock);
    }
}

public class stock_issue
{
    public static void main(String[] args)
    {
        Warehouse warehouse = new Warehouse();

        String[] items = { "laptop", "mouse", "keyboard", "monitor" };
        int[] quantities = { 3, -1, 10, 2 };

        for (int i = 0; i < items.length; i++)
        {
            try
            {
                warehouse.issue(items[i], quantities[i]);
            }
            catch (InvalidQuantityException e)
            {
                System.out.println("rejected: " + e.getMessage());
            }
            catch (OutOfStockException e)
            {
                System.out.println("rejected: " + e.getMessage() + " (shortfall: " + e.getShortfall() + ")");
            }
        }
    }
}