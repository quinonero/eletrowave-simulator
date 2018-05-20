package fr.quinonero.gui;

import fr.quinonero.thread.OpenGLThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public final class FxmlReader extends Application {

    public static FileChooser modelChooser = new FileChooser();
    public static Stage mainStage;
    public static long window;
    public static final int width = 1280;
    public static final int height = 720;
    public static File stlFile;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("interface.fxml"));
        stage.setResizable(false);
        stage.setScene(new Scene(loader.load()));
        mainStage = stage;
        modelChooser.setTitle("Open Resource File");
        modelChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Wavefront OBJ", "*.obj"));
        stage.show();
        new OpenGLThread().run();
    }


}
