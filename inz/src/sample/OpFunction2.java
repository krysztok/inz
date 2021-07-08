package sample;

public class OpFunction2 implements OpFunction{
    // ACKLEY'A
    public double getValue(Coordinates coordinates){
        double x = coordinates.getValues().get(0);
        double y = coordinates.getValues().get(1);
        return -20 * Math.exp(-0.2 * Math.sqrt(0.5 * (x*x +y*y))) - Math.exp(0.5 * (Math.cos(2*Math.PI*x) + Math.cos(2*Math.PI*y))) + 20 + Math.exp(1);
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
        return "Ackleya";
    }

    @Override
    public int getRange() {
        return 25;
    }
}