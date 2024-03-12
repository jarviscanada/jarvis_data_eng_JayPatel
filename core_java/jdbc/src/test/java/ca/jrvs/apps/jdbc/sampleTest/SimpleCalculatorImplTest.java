package ca.jrvs.apps.jdbc.sampleTest;

import ca.jrvs.apps.jdbc.sampleTest.SimpleCalculator;
import ca.jrvs.apps.jdbc.sampleTest.SimpleCalculatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCalculatorImplTest {

    SimpleCalculator calculator;

    @BeforeEach
    void init() {
        calculator = new SimpleCalculatorImpl();
    }

    @Test
    void add() {
        int expacted = 2;
        int actual = calculator.add(1,1);
        assertEquals(expacted,actual);
    }

    @Test
    void subtract() {
        int expacted = 2;
        int actual = calculator.subtract(4,2);
        assertEquals(expacted,actual);
    }

    @Test
    void multiply() {
        int expacted = 4;
        int actual = calculator.add(2,2);
        assertEquals(expacted,actual);
    }

    @Test
    void divide() {
        double expacted = 2;
        double actual = calculator.divide(4,2);
        assertEquals(expacted,actual);
    }
}