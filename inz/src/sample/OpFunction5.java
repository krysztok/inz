package sample;

public class OpFunction5 implements OpFunction{
    // SCHAFFER'A
    public double getValue(Coordinates coordinates) {
        double x = coordinates.getValues().get(0);
        double y = coordinates.getValues().get(1);
        return 0.5 + (Math.pow(Math.sin(x * x - y * y), 2) - 0.5) / Math.pow(1 + 0.001 * (x * x + y * y), 2);

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
        return "Shaffera";
    }

    @Override
    public int getRange() {
        return 10;
    }
}
