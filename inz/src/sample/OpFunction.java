package sample;

public interface OpFunction {
// BOHACHEVSKY’EGO
    public double getValue(Coordinates coordinates);

    public boolean isBetter(double oldValue, double newValue);

    public String getBestSolutionString();

    public String getName();

    public int getRange();
}
