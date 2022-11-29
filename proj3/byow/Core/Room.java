package byow.Core;


import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;


import java.util.List;

import static byow.Core.NewWorld.*;


public class Room {
    private Position p;
    private Position q;

    public Room(Position p, Position q) {
        Position.regular(p, q);
        this.p = p;
        this.q = q;
    }

    public Room() {
        Position p0 = Position.random();
        Position q0 = getRandomRoomQ(p0, Direction.getRandomDirection());
        Room temp = new Room(p0, q0);
        while (!temp.isValid()) {
            q0 = getRandomRoomQ(p0, Direction.getRandomDirection());
            temp = new Room(p0, q0);
        }
        this.p = p0;
        this.q = q0;
    }

    public Room emerge() {
        Room newRoom = emergeHelper();
        while (!newRoom.isValid()) {
            newRoom = emergeHelper();
        }
        return newRoom;
    }

    private Room emergeHelper() {
        if (isHall()) {
            PositionWithDirection pwd0 = Position.randomLength(this);
            Position p0 = pwd0.p;
            Direction d0 = pwd0.direction;
            return new Room(p0, getRandomRoomQ(p0, d0));
        } else {
            PositionWithDirection pwd0 = Position.randomPerimeter(this);
            Position p0 = pwd0.p;
            Direction d0 = pwd0.direction;
            return new Room(p0, getRandomHallQ(p0, d0));
        }
    }

    private Position getRandomRoomQ(Position p0, Direction d0) {
        Position q1, p1;
        int diff1 = RandomUtils.uniform(random, 4, roomSize / 2);
        int diff2 = RandomUtils.uniform(random, 4, roomSize);
        int diff3 = RandomUtils.uniform(random, 4, roomSize);
        boolean direct = RandomUtils.bernoulli(random);
        switch (d0) {
            case WEST:
                if (direct) {
                    p1 = p0.move(new Position(0, diff1));
                    q1 = p1.move(new Position(-diff2, -diff3));
                    break;
                } else {
                    p1 = p0.move(new Position(0, -diff1));
                    q1 = p1.move(new Position(-diff2, diff3));
                    break;
                }
            case EAST:
                if (direct) {
                    p1 = p0.move(new Position(0, diff1));
                    q1 = p1.move(new Position(diff2, -diff3));
                    break;
                } else {
                    p1 = p0.move(new Position(0, -diff1));
                    q1 = p1.move(new Position(diff2, diff3));
                    break;
                }
            case NORTH:
                if (direct) {
                    p1 = p0.move(new Position(diff1, 0));
                    q1 = p1.move(new Position(-diff2, diff3));
                    break;
                } else {
                    p1 = p0.move(new Position(-diff1, 0));
                    q1 = p1.move(new Position(diff2, diff3));
                    break;
                }
            case SOUTH:
                if (direct) {
                    p1 = p0.move(new Position(diff1, 0));
                    q1 = p1.move(new Position(-diff2, -diff3));
                    break;
                } else {
                    p1 = p0.move(new Position(-diff1, 0));
                    q1 = p1.move(new Position(diff2, -diff3));
                    break;
                }
            default:
                throw new IllegalStateException("Unexpected value: " + d0);
        }
        return q1;
    }



    private Position getRandomHallQ(Position p0, Direction d0) {
        int diff = RandomUtils.uniform(random, 4, hall);
        switch (d0) {
            case EAST:  return new Position(p0.x + diff, p0.y);
            case WEST:  return new Position(p0.x - diff, p0.y);
            case NORTH: return new Position(p0.x, p0.y + diff);
            case SOUTH: return new Position(p0.x, p0.y - diff);
            default:
                throw new IllegalStateException("Unexpected value: " + d0);
        }
    }


    public void draw(TETile[][] world) {
        for (int x=p.x; x<=q.x; x++) {
            for (int y=p.y; y<=q.y; y++) {
                world[x][y] = Tileset.FLOOR;
            }
        }
    }

    public boolean isHall() {
        return (q.x == p.x && q.y != p.y) || (q.y == p.y && q.x != p.x);
    }

    public boolean isIntersect(Room o) {
        return p.x - 2 <= o.getQ().x && o.getP().x - 2 <= q.x && p.y - 2 <= o.getQ().y && o.getP().y - 2 <= q.y;
    }

    public boolean isIntersect(List<Room> rooms, Room curRoom) {
        for (Room room: rooms) {
            if (room != curRoom && this.isIntersect(room)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValid() {
        return p.isInside(roomSpace) && q.isInside(roomSpace);
    }

    public Position getP() {
        return this.p;
    }
    public Position getQ() {
        return this.q;
    }
    public int getWidth() {
        return q.x - p.x + 1;
    }
    public int getHeight() {
        return q.y - p.y + 1;
    }

}