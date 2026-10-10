public class Fractions
{
    private int numerator;
    private int denominator;

    public Fractions(int numerator, int denominator)
    {
        this.numerator = numerator;
        this.denominator = denominator;

        int gcd = gcd(numerator, denominator);

        this.numerator /= gcd;
        this.denominator /= gcd;

    }

    private int gcd(int num, int den)
    {
        for (int i = num; i > 1; i--)
        {
            if ((num % i == 0.0) && (den % i == 0.0))
            {
                return i;
            }
        }

        return 1;
    }

    @Override
    public String toString()
    {
        return numerator + "/" + denominator;
    }

    @Override
    public boolean equals(Object f)
    {
        if ((((Fractions)f).toString()).equals(this.toString()))
        {
            return true;
        }
        else{
            return false;
        }
    }
}