package fr.quinonero.thread;

import fr.quinonero.gui.FxmlReader;
import fr.quinonero.models.Model;
import fr.quinonero.models.OBJLoader;
import fr.quinonero.models.Renderer;
import fr.quinonero.opengl.Line;
import fr.quinonero.opengl.OpenGLDraw;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OpenGLThread implements Runnable {

    public static float srcXPos = 0, srcYPos = 0;
    public static float srcRadius = 10;

    private List<Line> lines = new ArrayList<Line>();

    public static Model structModel = null;

    public OpenGLThread() {
        this.init();
        //this.setupQuad();
    }

    @Override
    public void run() {

        while (!Display.isCloseRequested()) {

            // render OpenGL here
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);


            OpenGLDraw.drawFilledCircle(srcXPos, srcYPos, srcRadius);

            for (Line l : lines) {
                l.update();
                     l.render();
            }

            if (structModel != null)
                Renderer.renderModel(structModel);

            // OpenGLDraw.drawCircleCenterLines(srcXPos, srcYPos, srcRadius);
        /*    GL30.glBindVertexArray(vao);
            GL20.glEnableVertexAttribArray(0);

            // Draw the vertices
            GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, vertex.length / 2);

            // Put everything back to default (deselect)
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);*/


            Display.update();
        }

        Display.destroy();
    }

    public void init() {
        try {
            Display.setDisplayMode(new DisplayMode(FxmlReader.width, FxmlReader.height));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 640, 480, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        for (float i = 0; i <= 360; i++) {
            lines.add(new Line(i, true, false));
        }

    }

}
