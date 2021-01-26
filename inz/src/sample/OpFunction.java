package sample;

public class OpFunction {
// BOHACHEVSKY’EGO
    public double getValue(Vector vector){
        double x = vector.getValues().get(0);
        double y = vector.getValues().get(1);
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
}
