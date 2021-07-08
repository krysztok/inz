package sample;

public class OpFunction4 implements OpFunction{
    // RASTRIGIN'A
    public double getValue(Coordinates coordinates) {
        double x = coordinates.getValues().get(0);
        double y = coordinates.getValues().get(1);
        return 10 * 2 + (x * x - 10 * Math.cos(2 * Math.PI * x)) + (y * y - 10 * Math.cos(2 * Math.PI * y));
    }

    public boolean isBetter(double oldValue, double newValue) {
        if (newValue < oldValue) {
            return true;
        }
        return false;
    }

    public String getBestSolutionString() {
        return ("f(0, 0) = 0");
    }
    public String getName(){
        return "Rastrigina";
    }

    @Override
    public int getRange() {
        return 5;
    }
}