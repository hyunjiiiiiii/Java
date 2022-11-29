package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

import static byow.Core.Engine.HEIGHT;
import static byow.Core.Engine.WIDTH;

public class NewWorld {
    static Random random;
    static Room roomSpace;
    static Room background;
    static final int roomSize = 16;
    static final int hall = 16;
    private TETile[][] world;


    public NewWorld(long seed) {
        random = new Random(seed);
        Position original = new Position(0, 0);
        Position dia = new Position(WIDTH - 1, HEIGHT - 1);
        roomSpace =  new Room(original.move(new Position(1, 1)), dia.move(new Position(-1, -1)));
        background = new Room(original, dia);
        world = new TETile[WIDTH][HEIGHT];
        makeBackground();
        makeRooms();
        makeWalls();
        makeLockDoor();
        makeKey();
    }

    private void makeKey() {
        Position position = Position.random();
        while (world[position.x][position.y] != Tileset.FLOOR) {
            position = Position.random();
        }
        world[position.x][position.y] = Tileset.KEY;
    }

    private void makeLockDoor() {
        Position position = Position.random();
        while (world[position.x][position.y] != Tileset.WALL) {
            position = Position.random();
        }
        world[position.x][position.y] = Tileset.LOCKED_DOOR;
    }

    private void makeWalls() {
        for (int i = 0; i != WIDTH; i++) {
            for (int j = 0; j != HEIGHT; j++) {
                Position pos = new Position(i, j);
                if (pos.isNothing(world) && pos.isNearFloor(world)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    private void makeRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        Room r = new Room();
        rooms.add(r);
        r.draw(world);
        int roomNum = 30;

        while (roomNum > 0) {
            int index = RandomUtils.uniform(random, rooms.size());
            Room current = rooms.get(index);
            Room next = current.emerge();
            if (next.isValid() && !next.isIntersect(rooms, current)) {
                rooms.add(next);
                next.draw(world);
                roomNum--;
            }
        }
    }

    private void makeBackground() {
        for (int i = 0; i != WIDTH; i++) {
            for (int j = 0; j != HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    public TETile[][] getWorld() {
        return world;
    }
}
