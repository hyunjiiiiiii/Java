import org.junit.Test;

import static org.junit.Assert.*;

public class MeasurementTest {
    private int feet;
    private int inches;
    private Measurement m2;
    private int multipleAmount;
    @Test
    public void test1() {
        // TODO: stub for first test
        assertEquals(0, feet);
        assertEquals(0, inches);
    }

    // TODO: Add additional JUnit tests for Measurement.java here.
    @Test
    public void Measurement1() {
        assertEquals(feet, feet);
        assertEquals(0, inches);
    }

    @Test
    public void Measurement2() {
        assertEquals(feet, feet);
        assertEquals(inches, inches);
    }

    @Test
    public void getFeet() {
        Measurement m = new Measurement();
        m.getFeet();
        assertEquals(0, feet);
    }

    @Test
    public void getInches() {
        Measurement m = new Measurement();
        m.getInches();
        assertEquals(0, inches);
    }

    @Test
    public void plus() {
        Measurement m = new Measurement();
        Measurement m2 = new Measurement();
        m.plus(m2);
        assertEquals(m.getFeet() + m2.getFeet(), m.getFeet());
        assertEquals(m.getInches() + m2.getInches(), m.getInches());
    }

    @Test
    public void minus() {
        Measurement m = new Measurement();
        m2 = new Measurement(feet, inches);
        m.minus(m2);
        assertEquals(m.getFeet() - m2.getFeet(), m.getFeet());
        assertEquals(m.getInches() - m2.getInches(), m.getInches());
    }

    @Test
    public void multiple() {
        Measurement m = new Measurement();
        m.multiple(multipleAmount);
        assertEquals(m.getFeet() * multipleAmount, m.getFeet());
        assertEquals(m.getInches() * multipleAmount, m.getInches());
    }


}