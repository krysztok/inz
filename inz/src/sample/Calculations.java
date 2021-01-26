package sample;

import java.util.ArrayList;
import java.util.List;

public class Calculations {
    private List<List<Particle>> particles;
    private List<Vector> bestGlobalLocations;
    private List<Double> bestGlobalValues;

    public Calculations(){
        particles = new ArrayList<>();
        bestGlobalLocations = new ArrayList<>();
        bestGlobalValues = new ArrayList<>();
    }

    public void addNextIteration(List<Particle> particles, Vector bestGlobalLocation, double bestGlobalValue){
        List<Particle> particlesCopy = new ArrayList<>();

        for(int i = 0; i < particles.size(); i++){
            Particle particle = particles.get(i);
            Particle particleCopy = new Particle(particle.getLocation(), particle.getVelocity(), particle.getBestLocation(), particle.getBestValue());
            particlesCopy.add(particleCopy);
        }

        this.particles.add(particlesCopy);
        this.bestGlobalLocations.add(bestGlobalLocation);
        this.bestGlobalValues.add(bestGlobalValue);
    }

    public List<Particle> getParticles(int iteration){
        if(iteration < particles.size()){
            return particles.get(iteration);
        }
        return null;
    }

    public Vector getBestGlobalLocation(int iteration){
        if(iteration < bestGlobalLocations.size()){
            return bestGlobalLocations.get(iteration);
        }
        return null;
    }

    public double getBestGlobalValue(int iteration){
        if(iteration < bestGlobalValues.size()){
            return bestGlobalValues.get(iteration);
        }
        return Double.NaN;
    }
}
