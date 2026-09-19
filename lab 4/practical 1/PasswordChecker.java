import java.util.Scanner;
import.java.util.regex.Pattern;

public class PasswordChecker
{
    private String pwd;
    public int rules_passed = 0
    public String label;
    public int rule1 = 0; //length greater than 8
    public int rule2 = 0; //contains uppercase letter
    public int rule3 = 0; //contains digit
    public int rule4 = 0; //contains special character

    PasswordChecker(String pwd)
    {
        this.pwd = pwd;
    }

    public void strength()
    {
        if ((rules_passed) == 0 or (rules_passed == 1))
        {
            label = "weak";
        }
        else if (rules_passed == 2 or rules_passed == 3)
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
        
    }

}