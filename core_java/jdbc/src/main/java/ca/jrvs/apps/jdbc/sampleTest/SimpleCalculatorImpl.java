package ca.jrvs.apps.jdbc.sampleTest;

public class SimpleCalculatorImpl implements SimpleCalculator {


    public int add(int x, int y) {
        return x+y;
    }

    public int subtract(int x, int y) {
        return x-y;
    }

    public int multiply(int x, int y) {
        return x*y;
    }

    public double divide(int x, int y) {
        return x/y;
    }

    public static void main(String[] args) {

    }
}
