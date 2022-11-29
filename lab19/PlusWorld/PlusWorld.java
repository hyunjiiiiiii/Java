package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final int WIDTH = 50;
    private static final int midWidth = WIDTH / 2;
    private static final int HEIGHT = 50;
    private static final int midHeight = HEIGHT / 2;

    public static void main(String[] args) {
        addPlus(5);
    }

    public static void addPlus(int size) {
        if (size > WIDTH / 3) {
            throw new IllegalArgumentException();
        }
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        int out = size + (size / 2);
        int in = size / 2;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        for (int x = midHeight - out; x < midHeight + out; x += 1) {
            for (int y = midHeight - in; y < midHeight + in; y += 1) {
                world[x][y] = Tileset.WALL;
            }
        }

        for (int x = midWidth - in; x < midWidth + in; x += 1) {
            for (int y = midWidth - out; y < midWidth + out; y += 1) {
                world[x][y] = Tileset.WALL;
            }
        }
        ter.renderFrame(world);
    }
}