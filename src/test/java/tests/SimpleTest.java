package tests;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class SimpleTest {

    @Test
    public void compareTest() {
        BigDecimal ten = new BigDecimal(10);
        int compareTo = ten.compareTo(BigDecimal.ZERO);
        System.out.println(compareTo);
        assertTrue(compareTo > 0);
    }
}
