package fr.quinonero.gui;

import fr.quinonero.models.OBJLoader;
import fr.quinonero.opengl.Line;
import fr.quinonero.thread.OpenGLThread;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public static Controller INSTANCE;

    @FXML
    public TextField srcFreqInput;

    @FXML
    public TextField distanceInput;

    @FXML
    public Button validate;

    @FXML
    public Button loadModel;

    @FXML
    public Button rayResetBtn;

    @FXML
    public Slider srcXPos;

    @FXML
    public Slider srcYPos;

    @FXML
    public Slider srcRadius;

    @FXML
    public Slider modelXPos;

    @FXML
    public Slider modelYPos;

    @FXML
    public Slider modelScale;

    @FXML
    public CheckBox renderHitbox;

    @FXML
    public CheckBox renderModel;

    @FXML
    public CheckBox renderSrc;

    @FXML
    public CheckBox renderRay;

    @FXML
    public CheckBox rayPropagation;

    @FXML
    public ColorPicker colorHitbox;

    @FXML
    public ColorPicker colorModel;

    @FXML
    public ColorPicker colorSrc;

    @FXML
    public ColorPicker colorRay;

    @FXML
    public ColorPicker colorBackground;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        INSTANCE = this;

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

        rayResetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OpenGLThread.lines.clear();
                for (float i = 0; i < 360; i++) {
                    OpenGLThread.lines.add(new Line(i, true, false));
                }
            }
        });


        srcXPos.setMax(FxmlReader.width);
        srcYPos.setMax(FxmlReader.height);
        srcXPos.setValue(FxmlReader.width / 2);
        srcYPos.setValue(FxmlReader.height / 2);
        srcRadius.setMin(1);
        srcRadius.setValue(10);

        modelXPos.setMax(FxmlReader.width);
        modelYPos.setMax(FxmlReader.height);
        modelXPos.setValue(FxmlReader.width / 2);
        modelYPos.setValue(FxmlReader.height / 2);
        modelScale.setMin(1);
        modelScale.setMax(10);
        modelScale.setValue(10);

        colorHitbox.setValue(Color.BLACK);
    }

}
