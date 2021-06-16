package sample;

public class OpFunction3 implements OpFunction{
    // MATYAS'A
    public double getValue(Vector vector){
        double x = vector.getValues().get(0);
        double y = vector.getValues().get(1);
        return -(0.26 * (x * x + y * y) - 0.48 * x * y);
    }

    public boolean isBetter(double oldValue, double newValue){
        if(newValue > oldValue){
            return true;
        }
        return false;
    }

    public String getBestSolutionString(){
        return("f(0, 0) = 0");
    }
    public String getName(){
        return "Matyasa";
    }

    @Override
    public int getRange() {
        return 25;
    }
}
