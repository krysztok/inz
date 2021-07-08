package sample;

import java.util.List;

public class Coordinates {
    private List<Double> values;

    public Coordinates(List<Double> values){
        this.values = values;
    }

    public List<Double> getValues() {
        return values;
    }

    public double getValue(int x){
        return values.get(x);
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }
}
