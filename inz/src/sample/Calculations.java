package sample;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Calculations {
    private List<List<Particle>> particles;
    private List<Coordinates> bestGlobalLocations;
    private List<Double> bestGlobalValues;

    private double minRange, maxRange;
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

        minRange = pos.getMinRange();
        maxRange = pos.getMaxRange();
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

    public void addNextIteration(List<Particle> particles, Coordinates bestGlobalLocation, double bestGlobalValue){
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

    public Coordinates getBestGlobalLocation(int iteration){
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
            myWriter.write("Optymalizowana funkcja: " + functionName + " \r\n");
            myWriter.write("Zakres funkcji od: " + minRange + " do: " + maxRange + " \r\n");
            myWriter.write("Liczba cząsteczek: " + particlesNumber + " \r\n");
            myWriter.write("Liczba iteracji: " + iterationsNumber + " \r\n");
            myWriter.write("Współczynnik bezwładności: " + inertiaRate + " \r\n");
            myWriter.write("Współczynnik dążenia do najlepszego rozwiązania lokalnego: " + localBestAspirationRate + " \r\n");
            myWriter.write("Współczynnik dążenia do najlepszego rozwiązania globalnego: " + globalBestAspirationRate + " \r\n");
            myWriter.write("Współczynnik uczenia: " + learningRate + " \r\n\r\n");

            for(int i = 0; i < particles.size(); i++){
                myWriter.write("Numer iteracji: " + i + " \n");
                myWriter.write("Najlepsze rozwiązanie w iteracji: f( " + bestGlobalLocations.get(i).getValue(0) + " , "
                        + bestGlobalLocations.get(i).getValue(1) + " ) = " + bestGlobalValues.get(i) +" \r\n");

                for(int j = 0; j < particles.get(i).size(); j++){
                    Particle particle = particles.get(i).get(j);
                    myWriter.write("Cząsteczka " + j + " : f( " + particle.getLocation(0) + " , "
                    + particle.getLocation(1) + " ) = " + opFunction.getValue(particle.getLocation()) + " \r\n" );
                }

                myWriter.write("\r\n");
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
            myWriter.write("\"ZakresFunkcjiOd\": " +"\""+ minRange +"\"," +  "\n");
            myWriter.write("\"ZakresFunkcjiDo\": " +"\""+ maxRange +"\"," + "\n");
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
