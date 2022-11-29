package deque;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Performs some basic linked list deque tests.
 */
public class LinkedListDequeTest {

    /**
     * You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * LinkedListDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. Please do not import java.util.Deque here!
     */

    public static Deque<Integer> lld = new LinkedListDeque<Integer>();
    public static Deque<Character> lld_char = new LinkedListDeque<Character>();

    public static LinkedListDeque<Integer> lld_lld = new LinkedListDeque<Integer>();

    @Test
    /** Adds a few things to the list, checks that isEmpty() is correct.
     * This is one simple test to remind you how junit tests work. You
     * should write more tests of your own.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

//        System.out.println("Make sure to uncomment the lines below (and delete this print statement)." +
//                " The code you submit to the AG shouldn't have any print statements!");

        assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());
        lld.addFirst(0);

        assertFalse("lld1 should now contain 1 item", lld.isEmpty());

        lld = new LinkedListDeque<Integer>(); //Assigns lld equal to a new, clean LinkedListDeque!


    }

    @Test
    public void sizeTest() {
        lld.addFirst(0);
        lld.addFirst(1);
        lld.addFirst(2);
        assertEquals(3, lld.size());
        lld = new LinkedListDeque<Integer>();
    }


    @Test
    public void addFirstTest() {
        lld.addFirst(0);
        lld.addFirst(1);
        int result = lld.get(0);
        assertEquals(1, result);
        lld = new LinkedListDeque<Integer>();
    }


    @Test
    public void addLastTest() {
        lld.addLast(0);
        lld.addLast(1);
        int result = lld.get(lld.size() - 1);
        assertEquals(1, result);
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void removeFirstTest() {
        lld.removeFirst();
        lld.printDeque();
        lld = new LinkedListDeque<Integer>();
        lld.addFirst(0);
        lld.removeFirst();
        lld.printDeque();
        lld = new LinkedListDeque<Integer>();
        lld.addFirst(0);
        lld.addLast(1);
        assertTrue(lld.removeFirst() == 0);
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void removeLastTest() {
        assertNull(lld.removeLast());
        lld.printDeque();
        assertTrue(lld.size() == 0);
        lld = new LinkedListDeque<Integer>();
        lld.addFirst(0);
        lld.removeLast();
        lld.printDeque();
        assertTrue(lld.size() == 0);
        lld = new LinkedListDeque<Integer>();
        lld.addFirst(0);
        lld.addLast(1);
        assertTrue(lld.removeLast() == 1);
        assertNull(lld.get(1));
        lld = new LinkedListDeque<Integer>();
    }
    @Test
    public void removeLastTest2() {
        // null <-> [1] <-> [0] <-> null
        lld.addFirst(0);
        lld.addFirst(1);

        // Check how it looks before remove
        System.out.print("Before remove: ");
        lld.printDeque();

        // Check what remove returns (should be 1
        System.out.print("removed ");
        System.out.println(lld.removeLast());

        System.out.print("After remove: ");
        lld.printDeque();

        assertNull(lld.get(1));
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void getTest() {
        lld.addFirst(0);
        lld.addLast(1);
        lld.addLast(2);
        int result = lld.get(1);
        assertEquals(1, result);
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void getRecursiveTest() {
        lld_lld.addFirst(0);
        lld_lld.addLast(1);
        lld_lld.addLast(2);
        int result = lld_lld.getRecursive(2);
        assertEquals(2, result);
        lld = new LinkedListDeque<Integer>();
    }

    /**
     * Adds an item, removes an item, and ensures that dll is empty afterwards.
     */
    @Test
    public void addRemoveTest() {
        lld.addFirst(0);
        lld.removeFirst();
        assertTrue(lld.isEmpty());
        lld = new LinkedListDeque<Integer>();
    }

    /**
     * Make sure that removing from an empty LinkedListDeque does nothing
     */
    @Test
    public void removeEmptyTest() {
        lld.removeFirst();
        assertTrue(lld.isEmpty());
        lld = new LinkedListDeque<Integer>();
    }

    /**
     * Make sure your LinkedListDeque also works on non-Integer types
     */
    @Test
    public void multipleParamsTest() {
        lld_char.addFirst('A');
        char result = lld_char.get(0);
        assertEquals('A', result);
        lld = new LinkedListDeque<Integer>();
    }

    /**
     * Make sure that removing from an empty LinkedListDeque returns null
     */
    @Test
    public void emptyNullReturn() {
        assertNull(lld.removeLast());
        lld = new LinkedListDeque<Integer>();
    }

    /**
     * TODO: Write tests to ensure that your implementation works for really large
     * numbers of elements, and test any other methods you haven't yet tested!
     */
    @Test
    public void largeLinkedListTest() {
        for (int i = 0; i < 1000; i++) {
            lld.addLast(i);
        }
        assertEquals(1000, lld.size());
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void equals() {
        Deque<Integer> o = new LinkedListDeque<Integer>();
        o.addFirst(0);
        o.addLast(2);
        o.addLast(4);
        o.addLast(6);
        lld.addFirst(0);
        lld.addLast(2);
        lld.addLast(4);
        lld.addLast(6);
        assertTrue(lld.equals(o));
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void combinedTest() {
        lld.addFirst(0);
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addFirst(3);
        lld.printDeque();
        assertTrue(lld.removeFirst() == 3);
        lld.printDeque();
        lld.removeFirst();
        lld.printDeque();
        int result = lld.get(1);
        assertEquals(0, result);
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void combinedTest1() {
        lld.addLast(0);
        lld.printDeque();
        lld.addFirst(3);
        lld.printDeque();
        lld.removeLast();
        lld.printDeque();
        lld.removeFirst();
        lld.printDeque();
        lld.addFirst(8);
        lld.printDeque();
        lld.addFirst(9);
        lld.printDeque();
        lld.removeLast();
        lld = new LinkedListDeque<Integer>();
    }

    @Test
    public void combinedTest2() {
        lld.addFirst(0);
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addFirst(3);
        lld.printDeque();
        assertTrue(lld.removeLast() == 0);
        lld.printDeque();
        int result = lld.get(1);
        assertEquals(2, result);
        assertTrue(lld.removeFirst() == 3);
        lld.printDeque();
        assertTrue(lld.removeFirst() == 2);
        lld.printDeque();
        assertTrue(lld.removeLast() == 1);
        assertTrue(lld.isEmpty());
        lld.addFirst(0);
        lld.printDeque();
        assert(lld.removeLast() == 0);
        lld.printDeque();

        lld = new LinkedListDeque<Integer>();
    }

}