package fr.quinonero.thread;

import fr.quinonero.gui.Controller;
import fr.quinonero.gui.FxmlReader;
import javafx.fxml.FXML;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class OpenGLThread implements Runnable {
    @Override
    public void run() {
        while (!glfwWindowShouldClose (FxmlReader.window))
        {
            glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glBegin (GL_TRIANGLES);
            glColor3f (1, 0, 0.7f);
            glVertex3f (6, 4, 0); // Vertex one
            glColor3f (1, 0, 0.7f);
            glVertex3f (4, 8, 0); // Vertex two
            glColor3f (1, 0, 0.7f);
            glVertex3f (8, 8, 0); // Vertex three
            glEnd();

            glRotatef(0.1f,0,0,1f);

            glfwSwapBuffers (FxmlReader.window);
            glfwPollEvents();
        }
        glfwDestroyWindow(FxmlReader.window);
        glfwTerminate();
    }
}
