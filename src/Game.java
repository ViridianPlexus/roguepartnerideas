import java.util.Random;
import java.util.Scanner;

public class Game {

    // constants
    private final static int WIDTH = 10;
    private final static String WALL_CHARACTER = "M";
    private final static String EMPTY_CHARACTER = " ";

    private final Scanner console = new Scanner(System.in);
    private Hero hero;
    private Treasure treasure1;
    private Treasure treasure2;
    private boolean treasure1Obtained;
    private boolean treasure2Obtained;
    private boolean isOver;

    public void run() {
        setUp();
        while (!isOver) {
            printWorld();
            move();
        }
        printWorld();
    }

    private void setUp() {
        System.out.print("What is the name of your hero?: ");
        String name = console.nextLine();
        System.out.print("What symbol would you would you like your hero to have?: ");
        char symbol = HeroHelper.getSymbol(console.nextLine());

        Random rand = new Random();
        int x = rand.nextInt(WIDTH);
        int y = rand.nextInt(WIDTH);

        hero = new Hero(name, x, y, symbol);

        do {
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(WIDTH);
        } while (x == hero.getX() && y == hero.getY());

        treasure1 = new Treasure(x, y);

        do {
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(WIDTH);
        } while ((x == hero.getX() && y == hero.getY()) || (x == treasure1.getX() && y == treasure1.getY()));

        treasure2 = new Treasure(x, y);
    }

    private void printWorld() {
        // top wall border
        System.out.println(WALL_CHARACTER.repeat(WIDTH + 2));

        for (int row = 0; row < WIDTH; row++) {
            // left wall border
            System.out.print(WALL_CHARACTER);
            for (int col = 0; col < WIDTH; col++) {
                if (row == hero.getY() && col == hero.getX()) {
                    System.out.print(hero.getSymbol());
                } else if ((row == treasure1.getY() && col == treasure1.getX()) || (row == treasure2.getY() && col == treasure2.getX())) {
                    System.out.print("T");
                } else {
                    System.out.print(EMPTY_CHARACTER);
                }
            }

            // right wall border
            System.out.println(WALL_CHARACTER);
        }

        // bottom wall border
        System.out.println(WALL_CHARACTER.repeat(WIDTH + 2));
    }

    private void move() {

        System.out.print(hero.getName() + ", move [WASD]: ");
        String move = console.nextLine().trim().toUpperCase();

        if (move.length() != 1) {
            return;
        }

        switch (move.charAt(0)) {
            case 'W':
                hero.moveUp();
                break;
            case 'A':
                hero.moveLeft();
                break;
            case 'S':
                hero.moveDown();
                break;
            case 'D':
                hero.moveRight();
                break;
        }

        if (hero.getX() < 0 || hero.getX() >= WIDTH
                || hero.getY() < 0 || hero.getY() >= WIDTH) {
            System.out.println(hero.getName() + " touched lava! You lose.");
            isOver = true;
        } else if ((hero.getX() == treasure1.getX() && hero.getY() == treasure1.getY())) {
            System.out.println(hero.getName() + " found a treasure! It's a golden sword! O=|::::>");
            treasure1Obtained = true;
        } else if ((hero.getX() == treasure2.getX() && hero.getY() == treasure2.getY())) {
            System.out.println(hero.getName() + " found a treasure! It's a golden shield! <");
            treasure2Obtained = true;
        }
        if (treasure1Obtained && treasure2Obtained) {
            System.out.println("You have found both treasures! You win!");
            isOver = true;
        }
    }
}
