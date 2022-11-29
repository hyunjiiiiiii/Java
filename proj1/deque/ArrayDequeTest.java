
package deque;

import org.junit.Before;
import org.junit.Test;

//import java.util.ArrayDeque;


import static org.junit.Assert.*;

/* Performs some basic array deque tests. */
public class ArrayDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     /**
     * You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * ArrayDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    public static Deque<Integer> ad = new ArrayDeque<Integer>();

    @Test
    public void testAddFirst() {
        ad.addFirst(0);
        ad.addFirst(1);
        ad.addFirst(2);
        ad.printDeque();
        assertEquals(3, ad.size());
        ad = new ArrayDeque<Integer>();
    }
    @Test
    public void testAddFirstWithResize() {
        for (int i = 0; i < 10; i++) {
            ad.addFirst(i);
        }
        ad.printDeque();
        int result1 = ad.size();
        int result2 = ad.get(0);
        assertTrue(result1 > 9);
        assertEquals(0, result2);

        ad = new ArrayDeque<Integer>();
    }
    @Test
    public void addLastTest() {
        ad.addLast(0);
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.printDeque();
        int result = ad.get(4);
        assertEquals(3, result);
        ad = new ArrayDeque<Integer>();
    }
    @Test
    public void addLastFirstTest() {
        ad.addFirst(2);
        ad.addFirst(1);
        ad.addFirst(0);
        ad.addLast(3);
        ad.addLast(4);
        ad.printDeque();
        int result = ad.get(2);
        assertEquals(4, result);
        ad = new ArrayDeque<Integer>();
    }
    @Test
    public void addLastFirstWithResizeTest() {
        for (int i = 0; i < 10; i++) {
            ad.addLast(i);
        }
        ad.printDeque();
        int result1 = ad.size();
        int result2 = ad.get(0);
        assertTrue(result1 > 9);

        ad = new ArrayDeque<Integer>();
    }
    @Test
    public void removeFirstTest() {
        ad.addFirst(0);
        ad.addFirst(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.printDeque();
        ad.removeFirst();
        ad.printDeque();
        ad.removeFirst();
        ad.printDeque();
        assertNull(ad.get(0));
        ad = new ArrayDeque<Integer>();
    }
    @Test
    public void removeEmptyTest() {
        ad.removeFirst();
        ad.removeLast();
        assertTrue(ad.isEmpty());

        ad = new ArrayDeque<Integer>();
    }

    @Test
    public void removeLastTest() {
        ad.addFirst(0);
        ad.addLast(1);
        ad.addLast(2);
        ad.printDeque();
        ad.removeLast();
        ad.printDeque();
        ad.removeLast();
        ad.printDeque();
        assertNull(ad.get(1));
        ad = new ArrayDeque<Integer>();
    }
    @Test
    public void getTest() {
        ad.addFirst(0);
        ad.addLast(1);
        ad.addLast(2);
        ad.printDeque();
        int result = ad.get(0);
        assertEquals(0, result);
        assertNull(ad.get(4));
        ad = new ArrayDeque<Integer>();
    }
    @Test
    public void sizeTest() {
        ad.addFirst(0);
        ad.addFirst(1);
        ad.addFirst(2);
        assertEquals(3, ad.size());
        ad = new ArrayDeque<Integer>();
    }
    @Test
    public void equals() {
        Deque<Integer> o = new ArrayDeque<>();
        o.addFirst(0);
        o.addFirst(1);
        ad.addFirst(0);
        ad.addFirst(1);
        assertTrue(ad.equals(o));
        ad = new ArrayDeque<Integer>();
    }
}