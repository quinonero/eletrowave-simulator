package fr.quinonero.gui;

import fr.quinonero.thread.OpenGLThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


public final class FxmlReader extends Application {

    public static FileChooser modelChooser = new FileChooser();
    public static Stage mainStage;
    public static long window;
    public static ByteBuffer buffer;
    public static final int width = 784;
    public static final int height = 623;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("interface.fxml"));
        stage.setResizable(false);
        stage.setScene(new Scene(loader.load()));
        mainStage = stage;
        modelChooser.setTitle("Open Resource File");
        modelChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("STL Files", "*.stl"));
        stage.show();
        initGL();
    }


    private void initGL() {

        if (!glfwInit()) {
            System.out.println("GLFW initialization failed.");
            System.exit(1);
        }
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        window = glfwCreateWindow(width, height, "GLFW Window", MemoryUtil.NULL, MemoryUtil.NULL);
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        GL.createCapabilities();
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 12, 12, 0, 1, -1);
        glfwShowWindow(window);

        new OpenGLThread().run();

        GL11.glReadBuffer(GL11.GL_FRONT);

        int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
        buffer = BufferUtils.createByteBuffer(width * height * bpp);
    }

}
