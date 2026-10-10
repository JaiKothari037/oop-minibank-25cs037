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
    public boolean equals(Object c)
    {
        if ((rank.equals(((Card)c).rank) /*== ((Card)c).rank*/) && (suit.equals(((Card)c).suit)) /*== ((Card)c).suit)*/)
        {
            return true;
        }
        else
        {
            return false;
        }
    }  

}