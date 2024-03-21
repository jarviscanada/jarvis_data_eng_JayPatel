package ca.jrvs.apps.jdbc.sampleTest;

import ca.jrvs.apps.jdbc.sampleTest.NotSoSimpleCalculatorImpl;
import ca.jrvs.apps.jdbc.sampleTest.SimpleCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotSoSimpleCalculatorImplTest {

    @Mock
    SimpleCalculator mockSimpleCalc;

    NotSoSimpleCalculatorImpl notSoSimpleCalculator;




    @BeforeEach
    void init() {
        notSoSimpleCalculator = new NotSoSimpleCalculatorImpl(mockSimpleCalc);
    }

    @Test
    void power() {
        int expected = 4;
        int actual = notSoSimpleCalculator.power(2,2);
        assertEquals(expected,actual);
    }

    @Test
    void abs() {
        when(mockSimpleCalc.multiply(-2,-1)).thenReturn(2);

        int expected = 2;
        int actual = notSoSimpleCalculator.abs(-2);

        assertEquals(expected,actual);

    }

    @Test
    void sqrt() {
        int expected = 2;
        double actual = notSoSimpleCalculator.sqrt(4);
        assertEquals(expected,actual);
    }


    //SPY example
    @Test
    void isDevidedByOrNot()
    {
        NotSoSimpleCalculatorImpl spyNoOfNotSoSImple = spy(notSoSimpleCalculator);
        when(spyNoOfNotSoSImple.abs(4)).thenReturn(4);
        when(spyNoOfNotSoSImple.abs(2)).thenReturn(2);
        boolean expected = true;
    boolean actual = spyNoOfNotSoSImple.isDevidedByOrNot(4,2);
    assertEquals(expected,actual);
    }
}