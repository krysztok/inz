package sample;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
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
    private Canvas canvasbackground;
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
    @FXML
    private ChoiceBox functionChooser;
    @FXML
    private CheckBox animationCheckBox;
    @FXML
    private Button countButton;

    public Controller() {
        pos = new POS();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*incijalizacja wyboru funkcji*/
        functionChooser.getItems().add("Bohachevsky'ego");
        functionChooser.getItems().add("Ackleya");
        functionChooser.getItems().add("Matyasa");
        functionChooser.getItems().add("Rastrigina");
        functionChooser.getItems().add("Shaffera");
        functionChooser.getItems().add("Griewanka");
        functionChooser.setValue("Griewanka");

        functionChooser.setOnAction((event) -> {
            int selectedIndex = functionChooser.getSelectionModel().getSelectedIndex();


            switch (selectedIndex) {
                case 0:
                    pos.setOpFunction(new OpFunction1());
                    break;
                case 1:
                    pos.setOpFunction(new OpFunction2());
                    break;
                case 2:
                    pos.setOpFunction(new OpFunction3());
                    break;
                case 3:
                    pos.setOpFunction(new OpFunction4());
                    break;
                case 4:
                    pos.setOpFunction(new OpFunction5());
                    break;
                case 5:
                    pos.setOpFunction(new OpFunction6());
                    break;
            }
            /*ustawienie zakresu*/
            rangeFromInput.setText(String.valueOf(0 - pos.getOpFunction().getRange()));
            rangeToInput.setText(String.valueOf(pos.getOpFunction().getRange()));
            pos.setMaxRange(pos.getOpFunction().getRange());
            pos.setMinRange(0 - pos.getOpFunction().getRange());

            /*reset suwaka*/
            setSliderDefault();
            /*ustawienie najlepszego rozwiazania*/
            setBestResultsDefault();
            /*wyczyszczenie informacji o cząsteczkach*/
            textIterationArea.clear();

            /*ustawienie tła i usunięcie wizulizacji*/
            GraphicsContext g = canvas.getGraphicsContext2D();
            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            setCanvasBackground();
        });

        /*inicjalizacja tła*/
        setCanvasBackground();

        /*inicjalizacja inputow*/
        rangeFromInput.setText(valueOf(pos.getMinRange()));
        rangeToInput.setText(valueOf(pos.getMaxRange()));
        particlesNumberInput.setText(valueOf(pos.getParticlesNumber()));
        iterationsNumberInput.setText(valueOf(pos.getIterationsNumber()));
        inertiaRateInput.setText(valueOf(pos.getInertiaRate()));
        localBestAspirationRateInput.setText(valueOf(pos.getLocalBestAspirationRate()));
        globalBestAspirationRateInput.setText(valueOf(pos.getGlobalBestAspirationRate()));
        learningRateInput.setText(valueOf(pos.getLearningRate()));

        /*ustawienie suwaka*/
        setSliderDefault();

        /*ustawienie najlepszego rozwiazania*/
        setBestResultsDefault();


        /*ustawienie wyrazen regularnych do inputow*/
        rangeFromInput.textProperty().addListener((observable, oldString, newString) -> {
            if (!newString.matches("-?\\d{0,5}([.,]\\d{0,4})?")) {
                rangeFromInput.setText(oldString);
            }
            if (!rangeFromInput.getText().isEmpty()) {
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
            if (!rangeToInput.getText().isEmpty()) {
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
            try {
                if (!newString.matches("\\d?([.,]\\d{0,6})?")) {
                    inertiaRateInput.setText(oldString);
                }
                if (!inertiaRateInput.getText().isEmpty()) {
                    if (inertiaRateInput.getText().contains(",")) {
                        newString = newString.replace(",", ".");
                        inertiaRateInput.setText(newString);
                    }
                    if (Double.parseDouble(inertiaRateInput.getText()) > 5) {
                        inertiaRateInput.setText("5.0");
                    }
                }
            } catch (Exception e) {
                inertiaRateInput.setText("0.");
            }
        });

        localBestAspirationRateInput.textProperty().addListener((observable, oldString, newString) -> {
            try {
                if (!newString.matches("\\d?([.,]\\d{0,6})?")) {
                    localBestAspirationRateInput.setText(oldString);
                }
                if (!localBestAspirationRateInput.getText().isEmpty()) {
                    if (localBestAspirationRateInput.getText().contains(",")) {
                        newString = newString.replace(",", ".");
                        localBestAspirationRateInput.setText(newString);
                    }
                    if (Double.parseDouble(localBestAspirationRateInput.getText()) > 5) {
                        localBestAspirationRateInput.setText("5.0");
                    }
                }
            } catch (Exception e) {
                localBestAspirationRateInput.setText("0.");
            }
        });

        globalBestAspirationRateInput.textProperty().addListener((observable, oldString, newString) -> {
            try {
                if (!newString.matches("\\d?([.,]\\d{0,6})?")) {
                    globalBestAspirationRateInput.setText(oldString);
                }
                if (!globalBestAspirationRateInput.getText().isEmpty()) {
                    if (globalBestAspirationRateInput.getText().contains(",")) {
                        newString = newString.replace(",", ".");
                        globalBestAspirationRateInput.setText(newString);
                    }
                    if (Double.parseDouble(globalBestAspirationRateInput.getText()) > 5) {
                        globalBestAspirationRateInput.setText("5.0");
                    }
                }
            } catch (Exception e) {
                globalBestAspirationRateInput.setText("0.");
            }
        });

        learningRateInput.textProperty().addListener((observable, oldString, newString) -> {
            try {
                if (!newString.matches("\\d?([.,]\\d{0,6})?")) {
                    learningRateInput.setText(oldString);
                }
                if (!learningRateInput.getText().isEmpty()) {
                    if (learningRateInput.getText().contains(",")) {
                        newString = newString.replace(",", ".");
                        learningRateInput.setText(newString);
                    }
                    if (Double.parseDouble(learningRateInput.getText()) > 1) {
                        learningRateInput.setText("1.0");
                    }
                }
            } catch (Exception e) {
                learningRateInput.setText("0.");
            }
        });

        /*obsluga suwaka*/
        iterationsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            /*ustawienie numeru iteracji*/
            iterationsNumberTextfield.setText(String.valueOf(newValue.intValue()));

            int precision = 10000;
            /*ustawienie najlepszego globalnego rozwiazania dla iteracji*/
            StringBuilder stringBuilder = new StringBuilder("f(");
            Coordinates bestGlobalLocation = pos.getCalculations().getBestGlobalLocation(newValue.intValue());
            for (int i = 0; i < bestGlobalLocation.getValues().size(); i++) {
                stringBuilder.append((double) Math.round(bestGlobalLocation.getValues().get(i) * precision) / precision);
                if (i != bestGlobalLocation.getValues().size() - 1) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append(") = ");
            stringBuilder.append((double) Math.round(pos.getCalculations().getBestGlobalValue(newValue.intValue()) * precision) / precision);
            bestParticleTextfield.setText(stringBuilder.toString());

            /*wizualizacja*/
            GraphicsContext g = canvas.getGraphicsContext2D();
            int width = 600, height = 600;
            double x = pos.getMaxRange() - pos.getMinRange();
            double minRange = pos.getMinRange();
            double scale = width / x;
            List<Particle> particleList = pos.getCalculations().getParticles(newValue.intValue());

            g.setFill(Color.BLACK);
            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            for (int i = 0; i < pos.getParticlesNumber(); i++) {
                g.fillOval((particleList.get(i).getLocation(0) - minRange) * scale, width - (particleList.get(i).getLocation(1) - minRange) * scale, 10, 10);
            }

            g.setFill(Color.RED);
            g.fillOval((bestGlobalLocation.getValue(0) - minRange) * scale, width - (bestGlobalLocation.getValue(1) - minRange) * scale, 10, 10);

            minVisText.setText(valueOf(pos.getMinRange()));
            maxVisText.setText(valueOf(pos.getMaxRange()));
            maxVisText1.setText(valueOf(pos.getMaxRange()));
            halfVisText.setText(valueOf((pos.getMaxRange() + pos.getMinRange()) / 2));
            halfVisText1.setText(valueOf((pos.getMaxRange() + pos.getMinRange()) / 2));

            /*info o czasteczkach dla iteracji*/
            stringBuilder = new StringBuilder();
            for (int i = 0; i < pos.getParticlesNumber(); i++) {
                stringBuilder.append(i + ") f(");
                Particle particle = pos.getCalculations().getParticles(newValue.intValue()).get(i);
                for (int j = 0; j < particle.getLocation().getValues().size(); j++) {
                    stringBuilder.append((double) Math.round(particle.getLocation().getValues().get(j) * precision) / precision);
                    if (j != particle.getLocation().getValues().size() - 1) {
                        stringBuilder.append(", ");
                    }
                }
                stringBuilder.append(") = ");
                stringBuilder.append((double) Math.round(pos.getOpFunction().getValue(particle.getLocation()) * precision) / precision);
                stringBuilder.append("\n");
                //stringBuilder.append(i + ") ").append(particleList.get(i).getLocation().getValues()).append("\n");
            }
            textIterationArea.setText(stringBuilder.toString());

        });

    }

    private void setSliderDefault() {
        iterationsSlider.setMax(pos.getIterationsNumber());
        iterationsSlider.setMajorTickUnit(pos.getIterationsNumber() / 5);
        iterationsSlider.setValue(0);
        iterationsSlider.setDisable(true);
    }

    private void setBestResultsDefault() {
        iterationsNumberTextfield.setText("-");
        bestParticleTextfield.setText("-");
        optimalValueTextfield.setText(pos.getOpFunction().getBestSolutionString());
    }

    @FXML
    private void saveToTxt() {
        pos.getCalculations().saveToTxt();
    }

    @FXML
    private void saveToJson() {
        pos.getCalculations().saveToJson();
    }

    @FXML
    private void startCalculations() {
        /*ustawienie nowych parametrow*/

        /*zakres funkcji*/
        try {
            pos.setMinRange(Double.parseDouble(rangeFromInput.getText()));
        } catch (Exception e) {
            rangeFromInput.setText(valueOf(pos.getMinRange()));
        }

        try {
            pos.setMaxRange(Double.parseDouble(rangeToInput.getText()));
        } catch (Exception e) {
            rangeToInput.setText(valueOf(pos.getMaxRange()));
        }

        if (pos.getMinRange() > pos.getMaxRange()) {
            double tmp = pos.getMinRange();
            pos.setMinRange(pos.getMaxRange());
            pos.setMaxRange(tmp);
            rangeFromInput.setText(valueOf(pos.getMinRange()));
            rangeToInput.setText(valueOf(pos.getMaxRange()));
        }

        /*liczba czasteczek*/
        try {
            pos.setParticlesNumber(Integer.parseInt(particlesNumberInput.getText()));
            if (Integer.parseInt(particlesNumberInput.getText()) == 0) {
                particlesNumberInput.setText("1");
                pos.setParticlesNumber(1);
            }
        } catch (Exception e) {
            particlesNumberInput.setText(valueOf(pos.getParticlesNumber()));
        }

        /*liczba iteracji*/
        try {
            pos.setIterationsNumber(Integer.parseInt(iterationsNumberInput.getText()));
            iterationsSlider.setMax(pos.getIterationsNumber());
            iterationsSlider.setMajorTickUnit(pos.getIterationsNumber() / 5);
        } catch (Exception e) {
            iterationsNumberInput.setText(valueOf(pos.getIterationsNumber()));
        }

        /*wspolczynniki*/
        try {
            pos.setInertiaRate(Double.parseDouble(inertiaRateInput.getText()));
        } catch (Exception e) {
            inertiaRateInput.setText(valueOf(pos.getInertiaRate()));
        }

        try {
            pos.setLocalBestAspirationRate(Double.parseDouble(localBestAspirationRateInput.getText()));
        } catch (Exception e) {
            localBestAspirationRateInput.setText(valueOf(pos.getLocalBestAspirationRate()));
        }

        try {
            pos.setGlobalBestAspirationRate(Double.parseDouble(globalBestAspirationRateInput.getText()));
        } catch (Exception e) {
            globalBestAspirationRateInput.setText(valueOf(pos.getGlobalBestAspirationRate()));
        }

        try {
            pos.setLearningRate(Double.parseDouble(learningRateInput.getText()));
        } catch (Exception e) {
            learningRateInput.setText(valueOf(pos.getLearningRate()));
        }

        /*dodanie tla*/
        setCanvasBackground();

        /*liczenie czasu start*/
        //long start = System.currentTimeMillis();

        /*algorytm*/
        pos.initialize();
        for (int i = 0; i < pos.getIterationsNumber(); i++) {
            pos.runIteration();
        }

        /*czas wypisanie*/
        //long finish = System.currentTimeMillis();
        //System.out.println(finish-start);

        /*Animacja*/
        if (animationCheckBox.isSelected()) {
            int delay = 100;
            /*wylaczenie mozliwosci wyboru funkcji i startu obliczen*/
            countButton.setDisable(true);
            functionChooser.setDisable(true);
            PauseTransition enablePauseTransition = new PauseTransition(Duration.millis(delay *(pos.getIterationsNumber() + 1)));
            enablePauseTransition.setOnFinished(event -> {
                countButton.setDisable(false);
                functionChooser.setDisable(false);
            });
            enablePauseTransition.play();

            for (int i = 0; i < pos.getIterationsNumber() + 1; i++) {
                PauseTransition pauseTransition = new PauseTransition(Duration.millis(delay * i));
                int finalI = i;
                pauseTransition.setOnFinished(event -> iterationsSlider.setValue(finalI));
                pauseTransition.play();
            }
        }else{
            /*odswiezenie*/
            iterationsSlider.setValue(0);
            iterationsSlider.setValue(pos.getIterationsNumber());
        }

        iterationsSlider.setDisable(false);

    }

    private void setCanvasBackground() {
        int width = 600, height = 600;

        double minRange = pos.getMinRange();
        double scale = width / (pos.getMaxRange() - pos.getMinRange());

        //pos.getOpFunction().getValue();
        GraphicsContext g = canvasbackground.getGraphicsContext2D();
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        PixelWriter pixelWriter = g.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                List<Double> point = new ArrayList<Double>();
                double x = i / scale + minRange;
                double y = minRange + (j / scale);
                point.add(x);
                point.add(y);
                double value = pos.getOpFunction().getValue(new Coordinates(point));


                /*przesuniencie tla wykresu poniewaz chyba mnozenie przez skale psuje polozenie*/
                int translation = 5;
                int xa = i + translation;
                int ya = height - (j - translation);

                double opacity = 0.6;
                if(value < -16){
                    pixelWriter.setColor(xa, ya, Color.color(0, 0, 0.5, opacity + opacity * value / -16 > 1 ? 1 : (opacity + opacity * value / -16 < 0 ? opacity : opacity + opacity * value / -16)));
                }
                else if(value < -8){
                    pixelWriter.setColor(xa, ya, Color.color(0, 0, 0.8, opacity + opacity * value / -8 > 1 ? 1 : (opacity + opacity * value / -8 < 0 ? opacity : opacity + opacity * value / -8)));
                }
                else if(value < -4){
                    pixelWriter.setColor(xa, ya, Color.color(0.11, 0.56, 1, opacity + opacity * value / -4 > 1 ? 1 : (opacity + opacity * value / -4 < 0 ? opacity : opacity + opacity * value / -4)));
                }
                else if(value < 0){
                    pixelWriter.setColor(xa, ya, Color.color(0.53, 0.8, 0.98, opacity + opacity * value / -4 > 1 ? 1 : (opacity + opacity * value / -4 < 0 ? opacity : opacity + opacity * value / -4)));
                }
                else if (value < 4) {
                    pixelWriter.setColor(xa, ya, Color.color(0.4, 0.8, 0.4, opacity + opacity * value / 4 > 1 ? 1 : (opacity + opacity * value / 4 < 0 ? opacity : opacity + opacity * value / 4)));
                } else if (value < 8) {
                    pixelWriter.setColor(xa, ya, Color.color(0.4, 0.9, 0.4, opacity + opacity * value / 8 > 1 ? 1 : (opacity + opacity * value / 8 < 0 ? opacity : opacity + opacity * value / 8)));
                } else if (value < 16) {
                    pixelWriter.setColor(xa, ya, Color.color(0.4, 1, 0.4, opacity + opacity * value / 16 > 1 ? 1 : (opacity + opacity * value / 16 < 0 ? opacity : opacity + opacity * value / 16)));
                } else if (value < 32) {
                    pixelWriter.setColor(xa, ya, Color.color(1, 1, 0.45, opacity + opacity * value / 32 > 1 ? 1 : (opacity + opacity * value / 32 < 0 ? opacity : opacity + opacity * value / 32)));
                } else if (value < 64) {
                    pixelWriter.setColor(xa, ya, Color.color(1, 1, 0.3, opacity + opacity * value / 64 > 1 ? 1 : (opacity + opacity * value / 64 < 0 ? opacity : opacity + opacity * value / 64)));
                } else if (value < 128) {
                    pixelWriter.setColor(xa, ya, Color.color(1, 1, 0.1, opacity + opacity * value / 128 > 1 ? 1 : (opacity + opacity * value / 128 < 0 ? opacity : opacity + opacity * value / 128)));
                } else if (value < 256) {
                    pixelWriter.setColor(xa, ya, Color.color(1, 0.52, 0, opacity + opacity * value / 256 > 1 ? 1 : (opacity + opacity * value / 256 < 0 ? opacity : opacity + opacity * value / 256)));
                } else if (value < 512) {
                    pixelWriter.setColor(xa, ya, Color.color(1, 0.4, 0, opacity + opacity * value / 512 > 1 ? 1 : (opacity + opacity * value / 512 < 0 ? opacity : opacity + opacity * value / 512)));
                } else if (value < 1024) {
                    pixelWriter.setColor(xa, ya, Color.color(1, 0.2, 0.2, opacity + opacity * value / 1024 > 1 ? 1 : (opacity + opacity * value / 1024 < 0 ? opacity : opacity + opacity * value / 1024)));
                } else if (value < 2048) {
                    pixelWriter.setColor(xa, ya, Color.color(1, 0.1, 0.1, opacity + opacity * value / 2048 > 1 ? 1 : (opacity + opacity * value / 2048 < 0 ? opacity : opacity + opacity * value / 2048)));
                } else if (value < 4096) {
                    pixelWriter.setColor(xa, ya, Color.color(1, 0, 0, opacity + opacity * value / 4096 > 1 ? 1 : (opacity + opacity * value / 4096 < 0 ? opacity : opacity + opacity * value / 4096)));
                } else {
                    pixelWriter.setColor(xa, ya, Color.color(1, 0, 0, 1));
                }


            }
        }

    }

}
