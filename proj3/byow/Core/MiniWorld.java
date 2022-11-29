package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

import static byow.Core.Engine.HEIGHT;
import static byow.Core.Engine.WIDTH;

public class MiniWorld {
    static Random random;
    static Room roomSpace;
    static Room background;
    static final int roomSize = 16;
    static final int hall = 16;
    private TETile[][] world;
    private TETile[][] miniWorld;


    public MiniWorld() {
        random = new Random();
        Position original = new Position(0, 0);
        Position dia = new Position(WIDTH - 1, HEIGHT - 1);
        roomSpace =  new Room(original.move(new Position(1, 1)), dia.move(new Position(-1, -1)));
        background = new Room(original, dia);
        miniWorld = new TETile[WIDTH][HEIGHT];
        makeBackground();
        makeMiniRooms();
        makeWalls();
        makeKey();
        makeKey();
        makeKey();
    }

    private void makeKey() {
        Position position = Position.random();
        while (miniWorld[position.x][position.y] != Tileset.FLOOR) {
            position = Position.random();
        }
        miniWorld[position.x][position.y] = Tileset.KEY;
    }

    private void makeMiniRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        Room r = new Room();
        rooms.add(r);
        r.draw(miniWorld);
        int roomNum = 5;

        while (roomNum > 0) {
            int index = RandomUtils.uniform(random, rooms.size());
            Room current = rooms.get(index);
            Room next = current.emerge();
            if (next.isValid() && !next.isIntersect(rooms, current)) {
                rooms.add(next);
                next.draw(miniWorld);
                roomNum--;
            }
        }
    }

    private void makeWalls() {
        for (int i = 0; i != WIDTH; i++) {
            for (int j = 0; j != HEIGHT; j++) {
                Position pos = new Position(i, j);
                if (pos.isNothing(miniWorld) && pos.isNearFloor(miniWorld)) {
                    miniWorld[i][j] = Tileset.WALL;
                }
            }
        }
    }


    private void makeBackground() {
        for (int i = 0; i != WIDTH; i++) {
            for (int j = 0; j != HEIGHT; j++) {
                miniWorld[i][j] = Tileset.NOTHING;
            }
        }
    }

    public TETile[][] getMiniWorld() {
        return miniWorld;
    }
}
