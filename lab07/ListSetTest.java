import org.junit.Test;
import static org.junit.Assert.*;

public class ListSetTest {

    @Test
    public void testBasics() {
        ListSet aSet = new ListSet();
        assertEquals(0, aSet.size());
        for (int i = -50; i < 50; i += 2) {
            aSet.add(i);
            assertTrue(aSet.contains(i));
        }
        assertEquals(50, aSet.size());
        for (int i = -50; i < 50; i += 2) {
            aSet.remove(i);
            assertFalse(aSet.contains(i));
        }
        assertTrue(aSet.isEmpty());
        assertEquals(0, aSet.size());
    }

    @Test
    public void testSizeEmpty() {
        ListSet aSet = new ListSet();
        assertEquals(0, aSet.size());
    }

    @Test
    public void testSize() {
        ListSet aSet = new ListSet();
        aSet.add(1);
        aSet.add(2);
        assertEquals(2, aSet.size());
    }

    @Test
    public void testAdd() {
        ListSet aSet = new ListSet();
        aSet.add(1);
        aSet.add(2);
        assertEquals(true, aSet.contains(2));
    }

    @Test
    public void testAddSame() {
        ListSet aSet = new ListSet();
        aSet.add(1);
        aSet.add(1);
        assertEquals(1, aSet.size());
    }

    @Test
    public void testRemove() {
        ListSet aSet = new ListSet();
        aSet.add(1);
        aSet.remove(1);
        assertTrue(aSet.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        ListSet aSet = new ListSet();
        assertEquals(true, aSet.isEmpty());
    }

    @Test
    public void testToIntArray() {
        ListSet aSet = new ListSet();
        for (int i = -2; i < 2; i++) {
            aSet.add(i);
        }

        int expected[] = {-2, -1, 0, 1};
        assertArrayEquals(expected, aSet.toIntArray());
    }

    @Test
    public void testToIntArray2() {
        ListSet aSet = new ListSet();
        aSet.add(-50);
        aSet.add(49);
        aSet.add(-50);
        aSet.add(49);

        int expected[] = {-50, 49};
        assertArrayEquals(expected, aSet.toIntArray());
    }

}
