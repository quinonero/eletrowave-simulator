package fr.quinonero.opengl;

import fr.quinonero.gui.Controller;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;

public class OpenGLDraw {

    public static void drawFilledCircle(double x, double y, double radius) {
        int i;
        int triangleAmount = (int) (20 * Math.pow(radius, 2)); //# of triangles used to draw circle

        //GLfloat radius = 0.8f; //radius
        double twicePi = 2.0f * PI;

        glPushMatrix();
        glColor3d(Controller.INSTANCE.colorSrc.getValue().getRed(), Controller.INSTANCE.colorSrc.getValue().getGreen(), Controller.INSTANCE.colorSrc.getValue().getBlue());

        glBegin(GL_TRIANGLE_FAN);
        glVertex2d(x, y); // center of circle
        for (i = 0; i <= triangleAmount; i++) {
            glVertex2d(
                    x + (radius * cos(i * twicePi / triangleAmount)),
                    y + (radius * sin(i * twicePi / triangleAmount))
            );
        }
        glEnd();
        glPopMatrix();
    }

}
