import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Game starting!");
        Game.main(args); // Start the game from the Game class
    }

    public interface Movable {
        void move(int deltaX, int deltaY, char[][] structure);
    }

    public static class Upgrade extends Item {
        public Upgrade(int x, int y) {
            super(x, y);
        }
    }

    public static class Treasure extends Item {
        private String type;

        public Treasure(int x, int y, String type) {
            super(x, y);
            this.type = type;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return "Main.Treasure{" + "type=" + type + ", position=("
                    + x + ", " + y + ")}";
        }
    }

    public static class Player implements Movable {
        private String name;
        private int x;
        private int y;
        private int health;
        private int strength;
        private String object;

        public Player(String name, int x, int y, int health, int strength) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.health = health;
            this.strength = strength;
            this.object = null; // Ingen föremål initialt
        }

        @Override
        public void move(int deltaX, int deltaY, char[][] structure) {
            int newX = x + deltaX;
            int newY = y + deltaY;

            // Kolla om nästa position är en vägg ('#')
            if (newX >= 0 && newX < structure.length && newY >= 0 && newY < structure[0].length
                    && structure[newX][newY] != '#') {
                x = newX;
                y = newY;
            } else {
                System.out.println("You can not go through the wall!");
            }
        }

        // Getter och Setter metoder
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getHealth() {
            return health;
        }

        public void setHealth(int health) {
            this.health = health;
        }

        public int getStrength() {
            return strength;
        }

        public void setStrength(int strength) {
            this.strength = strength;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public void showInfo() {
            System.out.println("Name: " + name);
            System.out.println("Position: " + x + "," + y);
            System.out.println("Health: " + health);
            System.out.println("Strength: " + strength);
            System.out.println("Object: " + object);
        }
    }

    public static class Monster extends Item implements Movable {
        private int strength;

        public Monster(int x, int y, int strength) {
            super(x, y);
            this.strength = strength;
        }

        @Override
        public void move(int deltaX, int deltaY, char[][] structure) {
            int newX = x + deltaX;
            int newY = y + deltaY;

            // Kolla om nästa position är en vägg ('#')
            if (newX >= 0 && newX < structure.length && newY >= 0 && newY < structure[0].length
                    && structure[newX][newY] != '#') {
                x = newX;
                y = newY;
            } else {
                System.out.println("Monster cannot go through the wall!");
            }
        }

        public int getStrength() {
            return strength;
        }

        public void setStrength(int strength) {
            this.strength = strength;
        }

        @Override
        public String toString() {
            return "Main.Monster{" + "strength=" + strength +
                    ", position=(" + x + ", " + y + ")}";
        }
    }

    public static class Item {
        protected int x;
        protected int y;

        public Item(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    public static class Maze {
        private char[][] structure;

        public Maze(char[][] structure) {
            this.structure = structure;
        }

        public char[][] getStructure() {
            return structure;
        }

        public void displayMaze() {
            for (char[] row : structure) {
                for (char cell : row) {
                    System.out.print(cell + " ");
                }
                System.out.println();
            }
        }
    }

    public static class Game {
        public static void main(String[] args) {
            // Skapa en enkel labyrintstruktur
            char[][] labyrint = {
                    {'#', '#', '#', '#', '#'},
                    {'#', ' ', ' ', 'T', '#'},
                    {'#', ' ', '#', ' ', '#'},
                    {'#', 'M', ' ', ' ', '#'},
                    {'#', '#', '#', '#', '#'}
            };

            Maze maze = new Maze(labyrint);
            Player player = new Player("Mikaela", 1, 1, 100, 75);

            // Skapa ett monster
            Monster monster = new Monster(3, 1, 50);

            // Skapa en skatt
            Treasure treasure = new Treasure(1, 3, "Gold");

            Scanner scanner = new Scanner(System.in);
            boolean spelAktivt = true;

            while (spelAktivt) {
                maze.displayMaze();
                player.showInfo();
                System.out.println("Write a direction using (w/a/s/d) to move ");
                char riktning = scanner.next().charAt(0);

                switch (riktning) {
                    case 'w':
                        player.move(-1, 0, maze.getStructure());
                        break;
                    case 'a':
                        player.move(0, -1, maze.getStructure());
                        break;
                    case 's':
                        player.move(1, 0, maze.getStructure());
                        break;
                    case 'd':
                        player.move(0, 1, maze.getStructure());
                        break;
                    default:
                        System.out.println("Not correct direction!");
                }

                if (player.getX() == treasure.getX() && player.getY() == treasure.getY()) {
                    System.out.println("You found the treasure!");
                    spelAktivt = false;
                }

                // Check for monster interaction (basic example)
                if (player.getX() == monster.getX() && player.getY() == monster.getY()) {
                    System.out.println("You encountered a monster!");
                    // Implement combat or interaction logic here
                    spelAktivt = false;
                }
            }

            scanner.close();
        }
    }
}
