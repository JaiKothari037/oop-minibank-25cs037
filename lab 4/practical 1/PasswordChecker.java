
public class PasswordChecker
{
    private String pwd;
    public int rules_passed = 0;
    public String label;
    // public int rule1 = 0; //length greater than 8
    // public int rule2 = 0; //contains uppercase letter
    // public int rule3 = 0; //contains digit
    // public int rule4 = 0; //contains special character

    PasswordChecker(String pwd)
    {
        this.pwd = pwd;
        checkrules();
        strength();
        System.out.println("label = " + label);

    }

    public void strength()
    {
        if ((rules_passed == 0) || (rules_passed == 1))
        {
            label = "weak";
        }
        else if (rules_passed == 2 || rules_passed == 3)
        {
            label = "medium";
        }
        else
        {
            label = "strong";
        }
    }

    public void checkrules()
    {
        //check strength using regex and string methods, and increment rules passed

        if (pwd.length() >= 8)
        {
            rules_passed++;
            System.out.println("first rule passed");
        }
        else
        {
            System.out.println("first rule failed");
        }

        if (pwd.matches("^(?=.*[A-Z]).*$"))
        {
            rules_passed++;
            System.out.println("second rule passed");
        }
        else
        {
            System.out.println("second rule failed");
        }

        if (pwd.matches("^(?=.*[0-9]).*$"))
        {
            rules_passed++;
            System.out.println("third rule passed");
        }
        else
        {
            System.out.println("third rule failed");
        }

        if(pwd.matches("^(?=.*[^A-Za-z0-9]).*$"))
        {
            rules_passed++;
            System.out.println("fourth rule passed");
        }

        else
        {
            System.out.println("fourth rule failed");
        }
    }

}