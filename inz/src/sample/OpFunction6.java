package sample;

public class OpFunction6 implements OpFunction{
    // GRIEWANGK'A
    public double getValue(Coordinates coordinates) {
        double x = coordinates.getValues().get(0);
        double y = coordinates.getValues().get(1);
        //return -1 / 4000.0 * (x * x + y * y) + Math.cos(x) * Math.cos(y / Math.sqrt(2)) - 1;
        return 1 + x * x / 4000 + y * y / 4000 - Math.cos(x) * Math.cos(y * Math.sqrt(2) / 2);
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
        return "Griewanka";
    }

    @Override
    public int getRange() {
        return 300;
    }
}