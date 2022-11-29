import org.junit.Test;

import static org.junit.Assert.*;

public class ModNCounterTest {
    private int N;

    @Test
    public void increment() {
        ModNCounter c = new ModNCounter();
        c.increment();
        assertEquals(1, c.value());
        c.increment();
        assertEquals(2, c.value());
        if (c.value() == N) {
            assertEquals(0, c.value());
        }
    }

    @Test
    public void reset() {
        ModNCounter c = new ModNCounter();
        c.increment();
        c.reset();
        assertEquals(0, c.value());
    }


    @Test
    public void testConstructor() {
        ModNCounter c = new ModNCounter();
        assertEquals(0, c.value());
    }
}