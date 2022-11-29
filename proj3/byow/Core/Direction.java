package byow.Core;

import static byow.Core.NewWorld.random;

public enum Direction {
    EAST, WEST, NORTH, SOUTH;

    public static Direction getRandomDirection() {
        int i = RandomUtils.uniform(random, 4);
        switch (i) {
            case 0: return EAST;
            case 1: return WEST;
            case 2: return NORTH;
            case 3: return SOUTH;
            default:
                throw new IllegalStateException();
        }
    }
}