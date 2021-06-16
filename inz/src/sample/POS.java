package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class POS {
    private double inertiaRate; //wsp. bezwladnosci 0-1
    private double localBestAspirationRate; //wsp. dazenia do najlepszego lokalnego rozwiazania 0-1
    private double globalBestAspirationRate; //wsp. dazenia do najlepszego globalnego rozwiazania 0-1
    private Vector bestGlobalLocation; //najlepsze globalne rozwiazanie
    private double bestGlobalvalue;
    private int particlesNumber; //ilosc czasteczek
    private int iterationsNumber; //ilosc iteracji
    private List<Particle> particles; //lista czasteczek
    private double xmin, xmax;
    private double learningRate; //wsp. wplywu predkosci na czasteczke
    private OpFunction opFunction;
    private Calculations calculations; //wyniki wszystkich iteracji

    public POS() {
        inertiaRate = 0.1;
        localBestAspirationRate = 0.3;
        globalBestAspirationRate = 0.6;
        particlesNumber = 50;
        iterationsNumber = 50;
        particles = new ArrayList<>();
        calculations = new Calculations(this);
        xmin = -300;
        xmax = 300;
        learningRate = 0.5;
        opFunction = new OpFunction6();
    }


    public void initialize() {
        particles = new ArrayList<>();
        bestGlobalLocation = null;
        calculations = new Calculations(this);

        Random generator = new Random();
        for (int i = 0; i < particlesNumber; i++) {
            /*polozenie poczatkowe*/
            double x = xmin + (xmax - xmin) * generator.nextDouble();
            double y = xmin + (xmax - xmin) * generator.nextDouble();

            List<Double> values = new ArrayList<Double>();
            values.add(x);
            values.add(y);

            Vector location = new Vector(values);

            /*najlepsze rozwiazanie globalne*/
            double opFunctionValue = opFunction.getValue(location);
            if (bestGlobalLocation == null) {
                bestGlobalLocation = location;
                bestGlobalvalue = opFunctionValue;
            } else {

                if (opFunction.isBetter(bestGlobalvalue, opFunctionValue)) {
                    bestGlobalLocation = location;
                    bestGlobalvalue = opFunctionValue;
                }
            }

            /*predkosc poczatkowa*/
            /*z przedzialu [-|max-min|, |max-min|]*/
            double vxmax = Math.abs(xmax - xmin);
            double vxmin = -vxmax;

            x = vxmax + (vxmax - vxmin) * generator.nextDouble();
            y = vxmax + (vxmax - vxmin) * generator.nextDouble();

            values = new ArrayList<Double>();
            values.add(x);
            values.add(y);

            Vector velocity = new Vector(values);

            /*utworzenie czasteczki*/
            particles.add(new Particle(location, velocity, opFunctionValue));
        }

        /*zapisanie stanu początkowego*/
        calculations.addNextIteration(particles, bestGlobalLocation, bestGlobalvalue);

        /*System.out.println(bestGlobalLocation.getValues());
        System.out.println(bestGlobalvalue);*/
    }

    public void runAlgorithm() {

    }

    public void runIteration() {
        Random generator = new Random();

        for (int i = 0; i < particlesNumber; i++) {
            /*nowa predkosc dla czasteczki*/
            double rl = generator.nextDouble();
            double rg = generator.nextDouble();
            Particle particle = particles.get(i);
            double x = particle.getVelocity(0) * inertiaRate + localBestAspirationRate * rl * (particle.getBestLocation(0) - particle.getLocation(0)) + globalBestAspirationRate * rg * (bestGlobalLocation.getValue(0) - particle.getLocation(0));
            double y = particle.getVelocity(1) * inertiaRate + localBestAspirationRate * rl * (particle.getBestLocation(1) - particle.getLocation(1)) + globalBestAspirationRate * rg * (bestGlobalLocation.getValue(1) - particle.getLocation(1));

            List<Double> velocity = new ArrayList<Double>();
            velocity.add(x);
            velocity.add(y);
            particle.setVelocity(new Vector(velocity));

            /*nowe polozenie czasteczki*/
            Vector location = particle.getLocation();
            x = location.getValue(0) + learningRate * velocity.get(0);
            if(x > xmax) x = xmax;
            if(x < xmin) x = xmin;
            y = location.getValue(1) + learningRate * velocity.get(1);
            if(y > xmax) y = xmax;
            if(y < xmin) y = xmin;

            List<Double> newLocation = new ArrayList<Double>();
            newLocation.add(x);
            newLocation.add(y);
            particle.setLocation(new Vector(newLocation));

            /*najlepsze rozwiazanie lokalne*/
            double test = particle.getBestValue();
            double opFunctionValue = opFunction.getValue(location);
            if (opFunction.isBetter(particle.getBestValue(), opFunctionValue)) {
                particle.setBestLocation(particle.getLocation());
                particle.setBestValue(opFunctionValue);

                /*najlepsze rozwiazanie globalne*/
                if (opFunction.isBetter(bestGlobalvalue, opFunctionValue)) {
                    bestGlobalLocation=particle.getLocation();
                    bestGlobalvalue=opFunctionValue;
                }
            }

            //System.out.println(location.getValues() +" " + test + " " + newLocation +" " +particle.getBestValue());

        }

        /*zapisanie iteracji*/
        calculations.addNextIteration(particles, bestGlobalLocation, bestGlobalvalue);

        //System.out.println(bestGlobalLocation.getValues());
        //System.out.println(bestGlobalvalue);
    }

    public void setBestGlobalLocation(Vector bestGlobalLocation) {
        this.bestGlobalLocation = bestGlobalLocation;
    }

    public void setGlobalBestAspirationRate(double globalBestAspirationRate) {
        this.globalBestAspirationRate = globalBestAspirationRate;
    }

    public void setInertiaRate(double inertiaRate) {
        this.inertiaRate = inertiaRate;
    }

    public void setIterationsNumber(int iterationsNumber) {
        this.iterationsNumber = iterationsNumber;
    }

    public void setLocalBestAspirationRate(double localBestAspirationRate) {
        this.localBestAspirationRate = localBestAspirationRate;
    }

    public void setParticles(List<Particle> particules) {
        this.particles = particules;
    }

    public void setParticlesNumber(int particulesAmount) {
        this.particlesNumber = particulesAmount;
    }

    public double getGlobalBestAspirationRate() {
        return globalBestAspirationRate;
    }

    public double getInertiaRate() {
        return inertiaRate;
    }

    public double getLocalBestAspirationRate() {
        return localBestAspirationRate;
    }

    public int getIterationsNumber() {
        return iterationsNumber;
    }

    public int getParticlesNumber() {
        return particlesNumber;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public Vector getBestGlobalLocation() {
        return bestGlobalLocation;
    }

    public double getXmin() {
        return xmin;
    }

    public double getXmax() {
        return xmax;
    }

    public void setXmax(double xmax) {
        this.xmax = xmax;
    }

    public void setXmin(double xmin) {
        this.xmin = xmin;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public OpFunction getOpFunction() {
        return opFunction;
    }

    public void setOpFunction(OpFunction opFunction) {
        this.opFunction = opFunction;
    }

    public Calculations getCalculations() {
        return calculations;
    }
}


