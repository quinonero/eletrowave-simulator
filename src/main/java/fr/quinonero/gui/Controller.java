package fr.quinonero.gui;

import fr.quinonero.utils.ByteBufferInputStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.lwjgl.opengl.GL11;

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
    public AnchorPane openGLRenderFrame;

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
                FxmlReader.modelChooser.showOpenDialog(FxmlReader.mainStage);
            }
        });


        Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            int i = 0;
            @Override
            public void handle(ActionEvent event) {
                if(openGLRenderFrame != null){
                    GL11.glReadPixels(0, 0, FxmlReader.width, FxmlReader.height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, FxmlReader.buffer );
                    ImageView abc = new ImageView(new Image(new ByteBufferInputStream(FxmlReader.buffer)));
                    abc.setFitWidth(openGLRenderFrame.getWidth());
                    abc.setFitHeight(openGLRenderFrame.getHeight());
                    System.out.println(FxmlReader.buffer.get());
                    openGLRenderFrame.getChildren().clear();
                    openGLRenderFrame.getChildren().add(abc);

                    System.out.println("tot");
                    i++;
                }
            }
        }));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();



    }
}
