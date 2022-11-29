package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Avatar {

    private TETile figure;
    private TETile[][] world;
    private Position pos;
    boolean afterEncounter;
    String getKeyString = "No key";
    public Avatar(TETile figure, Position pos, TETile[][] world, TERenderer ter) {
        this.figure = figure;
        this.world = world;
        this.pos = pos;
    }

    public void appear() {
        world[pos.x][pos.y] = figure;
    }


    public void move(char key) {
        world[pos.x][pos.y] = Tileset.FLOOR;
        switch (key) {
            case 'W':
                moveUp();
                break;
            case 'S':
                moveDown();
                break;
            case 'A':
                moveLeft();
                break;
            case 'D':
                moveRight();
                break;
            default:
                break;
        }
        if (world[pos.x][pos.y] == Tileset.LOCKED_DOOR) {
            world[pos.x][pos.y] = Tileset.UNLOCKED_DOOR;
        } else {
            world[pos.x][pos.y] = figure;
        }
    }

    public boolean isReachDoor() {
        return world[pos.x][pos.y] == Tileset.UNLOCKED_DOOR;
    }


    private void moveLeft() {
        if (world[pos.x - 1][pos.y] == Tileset.FLOOR
                || world[pos.x - 1][pos.y] == Tileset.LOCKED_DOOR
                || world[pos.x - 1][pos.y] == Tileset.KEY) {
            pos.x--;
        }
        if (world[pos.x][pos.y] == Tileset.KEY) {
            getKeyString = "Have key";
        }
    }

    private void moveRight() {
        if (world[pos.x + 1][pos.y] == Tileset.FLOOR
                || world[pos.x + 1][pos.y] == Tileset.LOCKED_DOOR
                || world[pos.x + 1][pos.y] == Tileset.KEY) {
            pos.x++;
        }
        if (world[pos.x][pos.y] == Tileset.KEY) {
            getKeyString = "Have key";
        }
    }

    private void moveUp() {
        if (world[pos.x][pos.y + 1] == Tileset.FLOOR
                || world[pos.x][pos.y + 1] == Tileset.LOCKED_DOOR
                || world[pos.x][pos.y + 1] == Tileset.KEY) {
            pos.y++;
        }
        if (world[pos.x][pos.y] == Tileset.KEY) {
            getKeyString = "Have key";
        }
    }

    private void moveDown() {
        if (world[pos.x][pos.y - 1] == Tileset.FLOOR
                || world[pos.x][pos.y - 1] == Tileset.LOCKED_DOOR
                || world[pos.x][pos.y - 1] == Tileset.KEY) {
            pos.y--;
        }
        if (world[pos.x][pos.y] == Tileset.KEY) {
            getKeyString = "Have key";
        }
    }
}