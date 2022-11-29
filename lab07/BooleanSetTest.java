import org.junit.Test;
import static org.junit.Assert.*;

public class BooleanSetTest {

    @Test
    public void testBasics() {
        BooleanSet aSet = new BooleanSet(100);
        assertEquals(0, aSet.size());
        for (int i = 0; i < 100; i += 2) {
            aSet.add(i);
            assertTrue(aSet.contains(i));
        }
        assertEquals(50, aSet.size());

        for (int i = 0; i < 100; i += 2) {
            aSet.remove(i);
            assertFalse(aSet.contains(i));
        }
        assertTrue(aSet.isEmpty());
        assertEquals(0, aSet.size());
    }

    @Test
    public void testToIntArray() {
        BooleanSet aSet = new BooleanSet(5);
        for (int i = 0; i < 5; i += 2) {
            // 0, 2, 4
            aSet.add(i);
            assertTrue(aSet.contains(i));
        }

        int expected[] = {0, 2, 4};
        assertArrayEquals(expected, aSet.toIntArray());
    }

    @Test
    public void testToIntArrayEmpty() {
        BooleanSet aSet = new BooleanSet(5);

        int expected[] = {};
        assertArrayEquals(expected, aSet.toIntArray());
    }
}
