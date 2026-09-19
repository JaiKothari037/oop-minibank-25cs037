public class Card
{
    private String rank;
    private String suit;

    public Card(String rank, String suit)
    {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString()
    {
        return rank + " of " + suit;
    }

    @Override
    public boolean equals(Card c)
    {
        if ((rank == c.rank) and (suit == c.suit))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}