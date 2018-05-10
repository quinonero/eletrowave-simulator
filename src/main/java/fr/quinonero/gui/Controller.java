package fr.quinonero.gui;

import fr.quinonero.models.OBJLoader;
import fr.quinonero.thread.OpenGLThread;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public TextField srcFreqInput;

    @FXML
    public TextField distanceInput;

    @FXML
    public Button validate;

    @FXML
    public Button loadModel;

    @FXML
    public Slider srcXPos;

    @FXML
    public Slider srcYPos;

    @FXML
    public Slider srcRadius;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        srcFreqInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)") && !newValue.equals("")) {
                    srcFreqInput.setText(oldValue);
                }
            }
        });

        distanceInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)") && !newValue.equals("")) {
                distanceInput.setText(oldValue);
            }
        });

        loadModel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OpenGLThread.structModel = OBJLoader.loadModel(FxmlReader.modelChooser.showOpenDialog(FxmlReader.mainStage));
            }
        });


        srcXPos.setMax(FxmlReader.width);
        srcYPos.setMax(FxmlReader.height);
        srcXPos.setValue(FxmlReader.width / 2);
        srcYPos.setValue(FxmlReader.height / 2);
        srcRadius.setMin(1);
        srcRadius.setValue(10);
        OpenGLThread.srcXPos = (float)srcXPos.getValue();
        OpenGLThread.srcYPos = (float)srcYPos.getValue();

        srcXPos.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                OpenGLThread.srcXPos = newValue.floatValue();
            }
        });

        srcYPos.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                OpenGLThread.srcYPos = newValue.floatValue();
            }
        });

        srcRadius.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                OpenGLThread.srcRadius = newValue.floatValue();
            }
        });

    }
}
