package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.*;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
    public static String name = "you";
    public static Character appearance = '@';

    private TETile[][] world = new TETile[WIDTH][HEIGHT];
    private Avatar avatar;
    private StringBuilder record = new StringBuilder();
    private static final Font TITLE_FONT = new Font("Monaco", Font.BOLD, 40);
    private static final Font SUBTITLE_FONT = new Font("Monaco", Font.BOLD, 25);
    private static final Font REGULAR_FONT = new Font("Monaco", Font.BOLD, 10);
    private Avatar miniAvatar;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        InputSource inputSource = new KeyboardInputSource();
        ter.initialize(WIDTH, HEIGHT);
        displayMenu();
        while (inputSource.possibleNextInput()) {
            char key = inputSource.getNextKey();
            if (key == 'N' || key == 'n') {
                record.append(key);
                makeNewWorld(inputSource);
                break;
            } else if (key == 'L' || key == 'l') {
                world = interactWithInputString(load());
                ter.renderFrame(world);
                break;
            } else if (key == 'Q' || key == 'q') {
                System.exit(0);
            } else if (key == 'A' || key == 'a') {
                record.append(key);
                makeNewWorldName(inputSource);
                break;
            } else if (key == 'C' || key == 'c') {
                record.append(key);
                makeNewWorldAvatar(inputSource);
                break;
            } else {
                throw new IllegalArgumentException();
            }
        }
        Position mouse;
        while (true) {
            mouse = new Position((int)StdDraw.mouseX(), (int)StdDraw.mouseY());
            infoTitle(mouse);
            if (StdDraw.hasNextKeyTyped()) {
                char key = inputSource.getNextKey();
                if (key == ':' && inputSource.getNextKey() == 'Q') {
                    save(record.toString());
                    System.exit(0);
                }
                if (key != ':') {
                    record.append(key);
                }
                avatar.move(key);
                ter.renderFrame(world);
                if (avatar.afterEncounter) {
                    //miniGame(inputSource);
                    avatar.afterEncounter = false;
                }
                if (avatar.isReachDoor() && haveKey()) {
                    finishGame();
                    quitGame(inputSource);
                }
            }
        }

    }

    private void makeNewWorldAvatar(InputSource inputSource) {
        appearance = typeAppearance(inputSource);
        long seed  = typeSeed(inputSource);
        NewWorld newWorld = new NewWorld(seed);
        world = newWorld.getWorld();
        Position avatarPosition = Position.random();
        while (world[avatarPosition.x][avatarPosition.y] != Tileset.FLOOR) {
            avatarPosition = Position.random();
        }
        avatar = new Avatar(Tileset.AVATAR, avatarPosition, world, ter);
        avatar.appear();
    }

    private void makeNewWorldName(InputSource inputSource) {
        name = typeName(inputSource);
        long seed  = typeSeed(inputSource);
        NewWorld newWorld = new NewWorld(seed);
        world = newWorld.getWorld();
        Position avatarPosition = Position.random();
        while (world[avatarPosition.x][avatarPosition.y] != Tileset.FLOOR) {
            avatarPosition = Position.random();
        }
        avatar = new Avatar(Tileset.AVATAR, avatarPosition, world, ter);
        avatar.appear();
    }

    /*private void miniGame(InputSource inputSource) {
        MiniWorld mini = new MiniWorld();
        world = mini.getMiniWorld();
        Position avatarPosition = Position.random();
        while (world[avatarPosition.x][avatarPosition.y] != Tileset.FLOOR) {
            avatarPosition = Position.random();
        }
        miniAvatar = new Avatar(Tileset.AVATAR, avatarPosition, world, ter);
        miniAvatar.appear();
        if (miniAvatar.keyNum == 3) {
            getKeyString = "Have key";
            StdDraw.clear();
            ter.initialize(WIDTH, HEIGHT);
            ter.renderFrame(world);
            StdDraw.show();
        }
    } */

    private boolean haveKey() {
        return avatar.getKeyString == "Have key";
    }

    private void quitGame(InputSource inputSource) {
        while (inputSource.possibleNextInput()) {
            char key = inputSource.getNextKey();
            if (key == 'Q') {
                System.exit(0);
            }
            if (key == 'R') {
                interactWithKeyboard();
            }
        }
    }

    private void finishGame() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(SUBTITLE_FONT);
        StdDraw.text(WIDTH / 2, HEIGHT - 15, "GAME CLEAR");
        StdDraw.text(WIDTH / 2, 5, "New game (R)");
        StdDraw.text(WIDTH / 2, 3, "Exit the game (Q)");
        StdDraw.setFont(REGULAR_FONT);
        StdDraw.show();
    }

    private void infoTitle(Position mouse) {
        StdDraw.clear(StdDraw.BLACK);
        ter.renderFrame(world);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(75, HEIGHT - 1, avatar.getKeyString);
        StdDraw.text(40, HEIGHT - 1, name + ", find the key and go to the door");
        StdDraw.textLeft(1, HEIGHT - 1 , world[mouse.x][mouse.y].description());
        StdDraw.show();
    }

    private void makeNewWorld(InputSource inputSource) {
        long seed  = typeSeed(inputSource);
        NewWorld newWorld = new NewWorld(seed);
        world = newWorld.getWorld();
        Position avatarPosition = Position.random();
        while (world[avatarPosition.x][avatarPosition.y] != Tileset.FLOOR) {
            avatarPosition = Position.random();
        }
        avatar = new Avatar(Tileset.AVATAR, avatarPosition, world, ter);
        avatar.appear();
    }

    private Character typeAppearance(InputSource inputSource) {
        if (inputSource.getClass().equals(KeyboardInputSource.class)) {
            draw("Choose your avatar \"1: @, 2: ♥, 3: ♠, 4: ♣\"");
        }
        char key = inputSource.getNextKey();
        record.append(key);
        if (key == '1') {
            appearance = '@';
        }
        if (key == '2') {
            appearance = '♥';
        }
        if (key == '3') {
            appearance = '♠';
        }
        if (key == '4') {
            appearance = '♣';
        }

        return appearance;
    }

    private String typeName(InputSource inputSource) {
        StringBuilder nameString = new StringBuilder();
        if (inputSource.getClass().equals(KeyboardInputSource.class)) {
            draw("Enter your avatar name and press \"S\" ");
        }
        while (inputSource.possibleNextInput()) {
            char key = inputSource.getNextKey();
            record.append(key);
            if (key == 'S' || key == 's') {
                break;
            }
            nameString.append(key);
            if (inputSource.getClass().equals(KeyboardInputSource.class)) {
                draw("Avatar name: " + nameString);
            }
        }
        return String.valueOf(nameString);
    }

    private long typeSeed(InputSource inputSource) {
        StringBuilder seedString = new StringBuilder();
        if (inputSource.getClass().equals(KeyboardInputSource.class)) {
            draw("Enter a seed and press \"S\" to start");
        }
        while (inputSource.possibleNextInput()) {
            char key = inputSource.getNextKey();
            record.append(key);
            if (key == 'S' || key == 's') {
                break;
            }
            seedString.append(key);
            if (inputSource.getClass().equals(KeyboardInputSource.class)) {
                draw("Seed: " + seedString.toString());
            }
        }
        return Long.parseLong(seedString.toString());
    }

    private void draw(String string) {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setFont(SUBTITLE_FONT);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, string);
        StdDraw.show();
        StdDraw.setFont(REGULAR_FONT);
    }

    private void displayMenu() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);

        StdDraw.setFont(TITLE_FONT);
        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 4, "CS61B: THE GAME");

        StdDraw.setFont(SUBTITLE_FONT);
        StdDraw.text(WIDTH / 2, HEIGHT * 0.6, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT * 0.5, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT * 0.4, "Avatar Name (A)");
        StdDraw.text(WIDTH / 2, HEIGHT * 0.3, "Choose Avatar (C)");
        StdDraw.text(WIDTH / 2, HEIGHT * 0.2, "Quit (Q)");
        StdDraw.show();
        StdDraw.setFont(REGULAR_FONT);
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        StringInputSource inputSource = new StringInputSource(input);
        while (inputSource.possibleNextInput()) {
            char key = inputSource.getNextKey();
            if (key == 'N' || key == 'n') {
                record.append(key);
                makeNewWorld(inputSource);
                break;
            }
            if (key == 'L' || key == 'l') {
                interactWithInputString(load());
                break;
            }
        }
        loadWorld(inputSource);
        return this.world;
    }

    private void loadWorld(StringInputSource inputSource) {
        while (inputSource.possibleNextInput()) {
            char key = inputSource.getNextKey();
            if (key == ':' && inputSource.getNextKey() == 'Q') {
                save(record.toString());
                break;
            }
            if (key != ':') {
                record.append(key);
            }
            avatar.move(key);
        }
    }


    private static String load() {
        File file = new File("./save_date.txt");
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                return (String) objectInputStream.readObject();
            } catch (FileNotFoundException excp) {
                throw new IllegalArgumentException(excp.getMessage());
            } catch (IOException excp) {
                System.out.println(excp);
                System.exit(0);
            } catch (ClassNotFoundException excp) {
                throw new IllegalArgumentException(excp.getMessage());
            }
        }
        return "";
    }

    private static void save(String record) {
        File file = new File("./save_date.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(record);
        } catch (FileNotFoundException excp) {
            throw new IllegalArgumentException(excp.getMessage());
        } catch (IOException excp) {
            System.out.println(excp);
            System.exit(0);
        }
    }
}

