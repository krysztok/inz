package sample;

public class Particle {
    //aktualne polozenie
    private Vector location;
    //aktualna predkosc
    private Vector velocity;
    //najlepsze lokalne rozwiazanie
    private Vector bestLocation;
    //wartosc najlepszego lokalnego rozwiazania
    private double bestValue;

    public Particle(Vector location, Vector velocity, double bestValue){
        this.location = location;
        this.bestLocation = location;
        this.velocity = velocity;
        this.bestValue = bestValue;
    }

    public Particle(Vector location, Vector velocity, Vector bestLocation, double bestValue){
        this.location = location;
        this.bestLocation = bestLocation;
        this.velocity = velocity;
        this.bestValue = bestValue;
    }

    public Particle(Vector location){
        this.location = location;
    }

    public Vector getBestLocation() {
        return bestLocation;
    }

    public double getBestLocation(int x){
        return bestLocation.getValue(x);
    }

    public Vector getLocation() {
        return location;
    }

    public double getLocation(int x) {
        return location.getValue(x);
    }

    public Vector getVelocity() {
        return velocity;
    }

    public double getVelocity(int x) {
        return velocity.getValue(x);
    }

    public void setBestLocation(Vector bestLocation) {
        this.bestLocation = bestLocation;
    }

    public void setLocation(Vector location) {
        this.location = location;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public double getBestValue() {
        return bestValue;
    }

    public void setBestValue(double bestValue) {
        this.bestValue = bestValue;
    }

}
