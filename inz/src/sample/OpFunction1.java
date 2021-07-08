package sample;

public class OpFunction1 implements OpFunction{
    // BOHACHEVSKY’EGO
    public double getValue(Coordinates coordinates){
        double x = coordinates.getValues().get(0);
        double y = coordinates.getValues().get(1);
        return x*x+2*y*y-0.3*Math.cos(Math.PI*3*x)-0.4*Math.cos(Math.PI*4*y)+0.7;
    }

    public boolean isBetter(double oldValue, double newValue){
        if(newValue < oldValue){
            return true;
        }
        return false;
    }

    public String getBestSolutionString(){
        return("f(0, 0) = 0");
    }

    public String getName(){
        return "Bohachevsky'ego";
    }

    @Override
    public int getRange() {
        return 25;
    }
}