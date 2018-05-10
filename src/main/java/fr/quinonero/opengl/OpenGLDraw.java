package fr.quinonero.opengl;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;

public class OpenGLDraw {

    public static void drawFilledCircle(float x, float y, float radius) {
        int i;
        int triangleAmount = (int) (20 * Math.pow(radius, 2)); //# of triangles used to draw circle

        //GLfloat radius = 0.8f; //radius
        double twicePi = 2.0f * PI;

        glPushMatrix();

        glBegin(GL_TRIANGLE_FAN);
        glVertex2f(x, y); // center of circle
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
