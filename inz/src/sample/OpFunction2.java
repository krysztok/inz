package sample;

public class OpFunction2 implements OpFunction{
    // ACKLEY'A
    public double getValue(Vector vector){
        double x = vector.getValues().get(0);
        double y = vector.getValues().get(1);
        return -20 * Math.exp(-Math.sqrt((x*x +y*y)/2)/5) - Math.exp((Math.cos(Math.PI*2*x) + Math.cos(Math.PI*2*y))/2) + 20 + Math.exp(1);
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