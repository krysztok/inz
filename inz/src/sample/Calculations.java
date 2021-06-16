package sample;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Calculations {
    private List<List<Particle>> particles;
    private List<Vector> bestGlobalLocations;
    private List<Double> bestGlobalValues;

    private double xmin, xmax;
    private int particlesNumber;
    private int iterationsNumber;
    private double inertiaRate;
    private double localBestAspirationRate;
    private double globalBestAspirationRate;
    private double learningRate;
    private String functionName;
    private OpFunction opFunction;

    public Calculations(POS pos){
        particles = new ArrayList<>();
        bestGlobalLocations = new ArrayList<>();
        bestGlobalValues = new ArrayList<>();

        xmin = pos.getXmin();
        xmax = pos.getXmax();
        particlesNumber = pos.getParticlesNumber();
        iterationsNumber = pos.getIterationsNumber();
        inertiaRate = pos.getInertiaRate();
        localBestAspirationRate = pos.getLocalBestAspirationRate();
        globalBestAspirationRate = pos.getGlobalBestAspirationRate();
        learningRate = pos.getLearningRate();

        if(pos.getOpFunction()!=null){
            functionName = pos.getOpFunction().getName();
            opFunction = pos.getOpFunction();
        }

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

    public void saveToTxt(){
        if(particles.size()<1){
            return;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();
        String filename = dtf.format(now) + ".txt";

        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write("Optymalizowana funkcja: " + functionName + "\n");
            myWriter.write("Zakres funkcji od: " + xmin + " do: " + xmax + "\n");
            myWriter.write("Liczba cząsteczek: " + particlesNumber + "\n");
            myWriter.write("Liczba iteracji: " + iterationsNumber + "\n");
            myWriter.write("Współczynnik bezwładności: " + inertiaRate + "\n");
            myWriter.write("Współczynnik dążenia do najlepszego rozwiązania lokalnego: " + localBestAspirationRate + "\n");
            myWriter.write("Współczynnik dążenia do najlepszego rozwiązania globalnego: " + globalBestAspirationRate + "\n");
            myWriter.write("Współczynnik uczenia: " + learningRate + "\n\n");

            for(int i = 0; i < particles.size(); i++){
                myWriter.write("Numer iteracji: " + i + "\n");
                myWriter.write("Najlepsze rozwiązanie w iteracji: f( " + bestGlobalLocations.get(i).getValue(0) + " , "
                        + bestGlobalLocations.get(i).getValue(1) + " ) = " + bestGlobalValues.get(i) +" \n");

                for(int j = 0; j < particles.get(i).size(); j++){
                    Particle particle = particles.get(i).get(j);
                    myWriter.write("Cząsteczka " + j + " : f( " + particle.getLocation(0) + " , "
                    + particle.getLocation(1) + " ) = " + opFunction.getValue(particle.getLocation()) + "\n" );
                }

                myWriter.write("\n");
            }

            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void saveToJson(){
        if(particles.size()<1){
            return;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();
        String filename = dtf.format(now) + ".json";

        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write("{\n");
            myWriter.write("\"OptymalizowanaFunkcja\": " +"\""+ functionName+"\"," + "\n");
            myWriter.write("\"ZakresFunkcjiOd\": " +"\""+ xmin+"\"," +  "\n");
            myWriter.write("\"ZakresFunkcjiDo\": " +"\""+ xmax+"\"," + "\n");
            myWriter.write("\"LiczbaCzasteczek\": " +"\""+ particlesNumber+"\"," + "\n");
            myWriter.write("\"LiczbaIteracji\": " +"\""+ iterationsNumber+"\"," + "\n");
            myWriter.write("\"WspolczynnikBezwladnosci\": " +"\""+ inertiaRate+"\"," + "\n");
            myWriter.write("\"WspolczynnikDazeniaDoNajlepszegoRozwiazaniaLokalnego\": " +"\""+ localBestAspirationRate+"\"," + "\n");
            myWriter.write("\"WspolczynnikDazeniaDoNajlepszegoRozwiazaniaGlobalnego\": " +"\""+ globalBestAspirationRate+"\"," + "\n");
            myWriter.write("\"WspolczynnikUczenia\": " +"\""+ learningRate+"\"," + "\n");
            myWriter.write("\"Iteracje\": [\n");

            for(int i = 0; i < particles.size(); i++){
                myWriter.write("{\n");
                myWriter.write("\"NumerIteracji\": " +"\""+ i + "\",\n");
                myWriter.write("\"NajlepszeRozwiązanie\": {\n");
                myWriter.write("\"x\": " +"\""+ bestGlobalLocations.get(i).getValue(0) + "\",\n");
                myWriter.write("\"y\": " +"\""+ bestGlobalLocations.get(i).getValue(1) + "\",\n");
                myWriter.write("\"Wartosc\": " +"\""+ bestGlobalValues.get(i)  + "\"\n},\n");
                myWriter.write("\"Czasteczki\": [\n");

                for(int j = 0; j < particles.get(i).size(); j++){
                    Particle particle = particles.get(i).get(j);
                    myWriter.write("{\n");
                    myWriter.write("\"Numer\": " +"\""+ j + "\",\n");
                    myWriter.write("\"x\": " +"\""+ particle.getLocation(0) + "\",\n");
                    myWriter.write("\"y\": " +"\""+ particle.getLocation(1) + "\",\n");
                    myWriter.write("\"Wartosc\": " +"\""+ opFunction.getValue(particle.getLocation())  + "\"\n}");

                    if(j+1 !=particles.get(i).size()){
                        myWriter.write(",\n");
                    } else {
                        myWriter.write("\n");
                    }
                }
                myWriter.write("]");

                if(i+1 !=particles.size()){
                    myWriter.write("},\n");
                } else {
                    myWriter.write("}\n");
                }

            }

            myWriter.write("]");
            myWriter.write("}");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
