import java.util.Random;
import java.util.Scanner;

public class RPSLS
{
    public enum Move {
        ROCK, PAPER, SCISSORS, LIZARD, SPOCK;

        private static final Move[] values = values(); //returns values of the enum and stores in array values of the type move
        private static final Random random = new Random(); //random object created

        public static Move randomvalue()
        {
            return values[random.nextInt(values.length)];
        }
    }

    public static int winner(Move a, Move b) {

        if (a == b)
            return 0;

        return switch (a) {
            case ROCK ->
                (b == Move.SCISSORS || b == Move.LIZARD) ? 1 : -1;

            case PAPER ->
                (b == Move.ROCK || b == Move.SPOCK) ? 1 : -1;

            case SCISSORS ->
                (b == Move.PAPER || b == Move.LIZARD) ? 1 : -1;

            case LIZARD ->
                (b == Move.SPOCK || b == Move.PAPER) ? 1 : -1;

            case SPOCK ->
                (b == Move.SCISSORS || b == Move.ROCK) ? 1 : -1;
        };
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int you = 0;
        int pc = 0;
        
        for (int i = 0; i < 5; i++)
        {
            System.out.println("new round");
            System.out.print("your turn : ");
            String input = sc.next().toUpperCase();

            Move move;

            try
            {
                move = Move.valueOf(input);
            }
            catch(IllegalArgumentException e)
            {
                System.out.println("not valid input");
                continue;
            }

            Move currentMove = Move.randomvalue();

            System.out.println("computer : " + currentMove);

            int champion = RPSLS.winner(move, currentMove);

            if (champion == 1)
            {
                System.out.println("you won");
                you++;
            }
            else if (champion == -1)
            {
                System.out.println("computer won");
                pc++;
            }
            else
            {
                System.out.println("draw");
            }

        }

        System.out.println("game over");
        if (pc > you)
        {
            System.out.println("pc won");
        }
        else if (you > pc)
        {
            System.out.println("you won");
        }
        else
        {
            System.out.println("draw");
        }
    }

}