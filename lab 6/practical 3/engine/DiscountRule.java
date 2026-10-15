package engine;

@FunctionalInterface
public interface DiscountRule
{
    double apply(double price);
}