package ca.jrvs.apps.jdbc.sampleTest;



public class NotSoSimpleCalculatorImpl implements NotSoSimpleCalculator{

    SimpleCalculator cal;

    public NotSoSimpleCalculatorImpl(SimpleCalculator calc) {
        this.cal = calc;
    }
    @Override

    public int power(int x, int y) {
        return (int) Math.pow(x,y);
    }

    @Override
    public int abs(int x) {
        return x <= 0 ? cal.multiply(x, -1) : x;
    }

    @Override
    public double sqrt(int x) {
        return Math.sqrt(x);
    }

    @Override
    public boolean isDevidedByOrNot(int x, int y) {
        int absX= abs(x);
        int absY=abs(y);

        return absX % absY == 0;
    }
}
