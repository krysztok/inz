package sample;

public class Particle {
    //aktualne polozenie
    private Coordinates location;
    //aktualna predkosc
    private Coordinates velocity;
    //najlepsze lokalne rozwiazanie
    private Coordinates bestLocation;
    //wartosc najlepszego lokalnego rozwiazania
    private double bestValue;

    public Particle(Coordinates location, Coordinates velocity, double bestValue){
        this.location = location;
        this.bestLocation = location;
        this.velocity = velocity;
        this.bestValue = bestValue;
    }

    public Particle(Coordinates location, Coordinates velocity, Coordinates bestLocation, double bestValue){
        this.location = location;
        this.bestLocation = bestLocation;
        this.velocity = velocity;
        this.bestValue = bestValue;
    }

    public Particle(Coordinates location){
        this.location = location;
    }

    public Coordinates getBestLocation() {
        return bestLocation;
    }

    public double getBestLocation(int x){
        return bestLocation.getValue(x);
    }

    public Coordinates getLocation() {
        return location;
    }

    public double getLocation(int x) {
        return location.getValue(x);
    }

    public Coordinates getVelocity() {
        return velocity;
    }

    public double getVelocity(int x) {
        return velocity.getValue(x);
    }

    public void setBestLocation(Coordinates bestLocation) {
        this.bestLocation = bestLocation;
    }

    public void setLocation(Coordinates location) {
        this.location = location;
    }

    public void setVelocity(Coordinates velocity) {
        this.velocity = velocity;
    }

    public double getBestValue() {
        return bestValue;
    }

    public void setBestValue(double bestValue) {
        this.bestValue = bestValue;
    }

}
