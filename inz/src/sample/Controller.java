package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;

public class Controller implements Initializable {

    private POS pos;

    @FXML
    private TextField iterationsNumberInput;
    @FXML
    private TextField particlesNumberInput;
    @FXML
    private TextField rangeFromInput;
    @FXML
    private TextField rangeToInput;
    @FXML
    private TextField inertiaRateInput;
    @FXML
    private TextField localBestAspirationRateInput;
    @FXML
    private TextField globalBestAspirationRateInput;
    @FXML
    private TextField learningRateInput;
    @FXML
    private Slider iterationsSlider;
    @FXML
    private Text iterationsNumberTextfield;
    @FXML
    private Text bestParticleTextfield;
    @FXML
    private Text optimalValueTextfield;
    @FXML
    private Canvas canvas;
    @FXML
    private Text minVisText;
    @FXML
    private Text maxVisText;
    @FXML
    private Text maxVisText1;
    @FXML
    private Text halfVisText;
    @FXML
    private Text halfVisText1;
    @FXML
    private TextArea textIterationArea;

    public Controller(){
        pos = new POS();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*inicjalizacja inputow*/
        rangeFromInput.setText(valueOf(pos.getXmin()));
        rangeToInput.setText(valueOf(pos.getXmax()));
        particlesNumberInput.setText(valueOf(pos.getParticlesNumber()));
        iterationsNumberInput.setText(valueOf(pos.getIterationsNumber()));
        inertiaRateInput.setText(valueOf(pos.getInertiaRate()));
        localBestAspirationRateInput.setText(valueOf(pos.getLocalBestAspirationRate()));
        globalBestAspirationRateInput.setText(valueOf(pos.getGlobalBestAspirationRate()));
        learningRateInput.setText(valueOf(pos.getLearningRate()));

        /*ustawienie suwaka*/
        iterationsSlider.setMax(pos.getIterationsNumber());
        iterationsSlider.setMajorTickUnit(pos.getIterationsNumber()/5);
        iterationsSlider.setDisable(true);

        /*ustawienie najlepszego rozwiazania*/
        iterationsNumberTextfield.setText("-");
        bestParticleTextfield.setText("-");
        optimalValueTextfield.setText(pos.getOpFunction().getBestSolutionString());


        /*ustawienie wyrazen regularnych do inputow*/
        rangeFromInput.textProperty().addListener((observable, oldString, newString) -> {
            if (!newString.matches("-?\\d{0,5}([.,]\\d{0,4})?")) {
                rangeFromInput.setText(oldString);
            }
            if(!rangeFromInput.getText().isEmpty()) {
                if (rangeFromInput.getText().contains(",")) {
                    newString = newString.replace(",", ".");
                    rangeFromInput.setText(newString);
                }
            }
        });

        rangeToInput.textProperty().addListener((observable, oldString, newString) -> {
            if (!newString.matches("-?\\d{0,5}([.,]\\d{0,4})?")) {
                rangeToInput.setText(oldString);
            }
            if(!rangeToInput.getText().isEmpty()) {
                if (rangeToInput.getText().contains(",")) {
                    newString = newString.replace(",", ".");
                    rangeToInput.setText(newString);
                }
            }
        });

        particlesNumberInput.textProperty().addListener((observable, oldString, newString) -> {
            if (!newString.matches("\\d{0,5}")) {
                particlesNumberInput.setText(oldString);
            }
        });

        iterationsNumberInput.textProperty().addListener((observable, oldString, newString) -> {
            if (!newString.matches("\\d{0,5}")) {
                iterationsNumberInput.setText(oldString);
            }
        });

        inertiaRateInput.textProperty().addListener((observable, oldString, newString) -> {
            try{
                if (!newString.matches("\\d?([.,]\\d{0,6})?")) {
                    inertiaRateInput.setText(oldString);
                }
                if(!inertiaRateInput.getText().isEmpty()) {
                    if (inertiaRateInput.getText().contains(",")) {
                        newString = newString.replace(",", ".");
                        inertiaRateInput.setText(newString);
                    }
                    if (Double.parseDouble(inertiaRateInput.getText()) > 1) {
                        inertiaRateInput.setText("1.0");
                    }
                }
            }catch (Exception e){
                inertiaRateInput.setText("0.");
            }
        });

        localBestAspirationRateInput.textProperty().addListener((observable, oldString, newString) -> {
            try{
            if (!newString.matches("\\d?([.,]\\d{0,6})?")) {
                localBestAspirationRateInput.setText(oldString);
            }
            if(!localBestAspirationRateInput.getText().isEmpty()) {
                if (localBestAspirationRateInput.getText().contains(",")) {
                    newString = newString.replace(",", ".");
                    localBestAspirationRateInput.setText(newString);
                }
                if (Double.parseDouble(localBestAspirationRateInput.getText()) > 1) {
                    localBestAspirationRateInput.setText("1.0");
                }
            }
            }catch (Exception e){
                localBestAspirationRateInput.setText("0.");
            }
        });

        globalBestAspirationRateInput.textProperty().addListener((observable, oldString, newString) -> {
            try{
            if (!newString.matches("\\d?([.,]\\d{0,6})?")) {
                globalBestAspirationRateInput.setText(oldString);
            }
            if(!globalBestAspirationRateInput.getText().isEmpty()) {
                if (globalBestAspirationRateInput.getText().contains(",")) {
                    newString = newString.replace(",", ".");
                    globalBestAspirationRateInput.setText(newString);
                }
                if (Double.parseDouble(globalBestAspirationRateInput.getText()) > 1) {
                    globalBestAspirationRateInput.setText("1.0");
                }
            }
            } catch (Exception e){
                globalBestAspirationRateInput.setText("0.");
            }
        });

        learningRateInput.textProperty().addListener((observable, oldString, newString) -> {
            try{
            if (!newString.matches("\\d?([.,]\\d{0,6})?")) {
                learningRateInput.setText(oldString);
            }
            if(!learningRateInput.getText().isEmpty()) {
                if (learningRateInput.getText().contains(",")) {
                    newString = newString.replace(",", ".");
                    learningRateInput.setText(newString);
                }
                if (Double.parseDouble(learningRateInput.getText()) > 1) {
                    learningRateInput.setText("1.0");
                }
            }
            } catch (Exception e){
                learningRateInput.setText("0.");
            }
        });

        /*obsluga suwaka*/
        iterationsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            /*ustawienie numeru iteracji*/
            iterationsNumberTextfield.setText(String.valueOf(newValue.intValue()));

            int precision = 10000;
            /*ustawienie najlepszego globalnego rozwiazania dla iteracji*/
            StringBuilder stringBuilder= new StringBuilder("f(");
            Vector bestGlobalLocation = pos.getCalculations().getBestGlobalLocation(newValue.intValue());
            for (int i = 0; i < bestGlobalLocation.getValues().size(); i++){
                stringBuilder.append((double)Math.round(bestGlobalLocation.getValues().get(i)*precision)/precision);
                if(i != bestGlobalLocation.getValues().size()-1){
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append(") = ");
            stringBuilder.append((double)Math.round(pos.getCalculations().getBestGlobalValue(newValue.intValue())*precision)/precision);
            bestParticleTextfield.setText(stringBuilder.toString());

            /*wizualizacja*/
            GraphicsContext g = canvas.getGraphicsContext2D();
            int width = 600, height = 600;
            double x = pos.getXmax() - pos.getXmin();
            double xmin = pos.getXmin();
            double scale = width/x;
            List<Particle> particleList = pos.getCalculations().getParticles(newValue.intValue());

            g.setFill(Color.BLACK);
            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            for (int i = 0; i < pos.getParticlesNumber(); i++){
                g.fillOval((particleList.get(i).getLocation(0) - xmin) * scale, width - (particleList.get(i).getLocation(1) - xmin) * scale, 10, 10);
            }

            g.setFill(Color.RED);
            g.fillOval((bestGlobalLocation.getValue(0) - xmin) * scale, width - (bestGlobalLocation.getValue(1) - xmin) * scale, 10, 10);

            minVisText.setText(valueOf(pos.getXmin()));
            maxVisText.setText(valueOf(pos.getXmax()));
            maxVisText1.setText(valueOf(pos.getXmax()));
            halfVisText.setText(valueOf((pos.getXmax() + pos.getXmin())/2));
            halfVisText1.setText(valueOf((pos.getXmax() + pos.getXmin())/2));

            /*info o czasteczkach dla iteracji*/
            stringBuilder = new StringBuilder();
            for (int i = 0; i < pos.getParticlesNumber(); i++){
                stringBuilder.append(i + ") f(");
                Particle particle = pos.getCalculations().getParticles(newValue.intValue()).get(i);
                for (int j = 0; j < particle.getLocation().getValues().size(); j++){
                    stringBuilder.append((double)Math.round(particle.getLocation().getValues().get(j)*precision)/precision);
                    if(j != particle.getLocation().getValues().size()-1){
                        stringBuilder.append(", ");
                    }
                }
                stringBuilder.append(") = ");
                stringBuilder.append((double)Math.round(pos.getOpFunction().getValue(particle.getLocation())*precision)/precision);
                stringBuilder.append("\n");
                //stringBuilder.append(i + ") ").append(particleList.get(i).getLocation().getValues()).append("\n");
            }
            textIterationArea.setText(stringBuilder.toString());

        });

    }

    @FXML
    private void startCalculations()
    {
        /*ustawienie nowych parametrow*/

        /*zakres funkcji*/
        try{
            pos.setXmin(Double.parseDouble(rangeFromInput.getText()));
        }catch (Exception e){
            rangeFromInput.setText(valueOf(pos.getXmin()));
        }

        try{
            pos.setXmax(Double.parseDouble(rangeToInput.getText()));
        }catch (Exception e){
            rangeToInput.setText(valueOf(pos.getXmax()));
        }

        if(pos.getXmin() > pos.getXmax()){
            double tmp = pos.getXmin();
            pos.setXmin(pos.getXmax());
            pos.setXmax(tmp);
            rangeFromInput.setText(valueOf(pos.getXmin()));
            rangeToInput.setText(valueOf(pos.getXmax()));
        }

        /*liczba czasteczek*/
        try{
            pos.setParticlesNumber(Integer.parseInt(particlesNumberInput.getText()));
            if(Integer.parseInt(particlesNumberInput.getText()) == 0){
                particlesNumberInput.setText("1");
                pos.setParticlesNumber(1);
            }
        }catch (Exception e){
            particlesNumberInput.setText(valueOf(pos.getParticlesNumber()));
        }

        /*liczba iteracji*/
        try{
            pos.setIterationsNumber(Integer.parseInt(iterationsNumberInput.getText()));
            iterationsSlider.setMax(pos.getIterationsNumber());
            iterationsSlider.setMajorTickUnit(pos.getIterationsNumber()/5);
        }catch (Exception e){
            iterationsNumberInput.setText(valueOf(pos.getIterationsNumber()));
        }

        /*wspolczynniki*/
        try{
            pos.setInertiaRate(Double.parseDouble(inertiaRateInput.getText()));
        }catch (Exception e){
            inertiaRateInput.setText(valueOf(pos.getInertiaRate()));
        }

        try{
            pos.setLocalBestAspirationRate(Double.parseDouble(localBestAspirationRateInput.getText()));
        }catch (Exception e){
            localBestAspirationRateInput.setText(valueOf(pos.getLocalBestAspirationRate()));
        }

        try{
            pos.setGlobalBestAspirationRate(Double.parseDouble(globalBestAspirationRateInput.getText()));
        }catch (Exception e){
            globalBestAspirationRateInput.setText(valueOf(pos.getGlobalBestAspirationRate()));
        }

        try{
            pos.setLearningRate(Double.parseDouble(learningRateInput.getText()));
        }catch (Exception e){
            learningRateInput.setText(valueOf(pos.getLearningRate()));
        }

        pos.initialize();

       for (int i = 0; i < pos.getIterationsNumber(); i++){
            pos.runIteration();
           iterationsSlider.setValue(i);
        }

       iterationsSlider.setDisable(false);
       /*odswiezenie*/
       iterationsSlider.setValue(0);
       iterationsSlider.setValue(pos.getIterationsNumber());
    }

}
