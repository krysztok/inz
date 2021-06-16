package sample;

public class OpFunction6 implements OpFunction{
    // GRIEWANGK'A
    public double getValue(Vector vector) {
        double x = vector.getValues().get(0);
        double y = vector.getValues().get(1);
        return -1 / 4000.0 * (x * x + y * y) + Math.cos(x) * Math.cos(y / Math.sqrt(2)) - 1;
    }

    public boolean isBetter(double oldValue, double newValue) {
        if (newValue > oldValue) {
            return true;
        }
        return false;
    }

    public String getBestSolutionString() {
        return ("f(0, 0) = 0");
    }
    public String getName(){
        return "Griewangka";
    }

    @Override
    public int getRange() {
        return 300;
    }
}