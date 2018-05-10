package fr.quinonero.opengl;

import fr.quinonero.thread.OpenGLThread;

import static org.lwjgl.opengl.GL11.*;

public class Line {

    public Line src;
    public int length = 1;
    public double deg, impactX, impactY;
    public boolean isSource;
    public boolean isDephased;
    public boolean gotImpact = false;

    public Line(float deg, boolean isSource, boolean isDephased) {
        this.deg = deg;
        this.isSource = isSource;
        this.isDephased = isDephased;
    }

    public Line(int deg, boolean isSource, boolean isDephased, Line src) {
        this.deg = deg;
        this.isSource = isSource;
        this.isDephased = isDephased;
        this.src = src;
    }

    public void update() {

        this.impactX = length * Math.cos(this.deg);
        this.impactY = length * Math.sin(this.deg);


        checkCollision();
        if (!gotImpact)
            this.length++;
    }

    public void render() {
        glPushMatrix();
        if (this.isSource) {
            glTranslated(OpenGLThread.srcXPos, OpenGLThread.srcYPos, 0);
        } else {
            glTranslated(this.src.impactX, this.src.impactY, 0);
        }
        glRotated(this.deg, 0, 0, 1);
        glBegin(GL_LINES);
        glVertex3d(10, 10, 0);
        glVertex3d(this.length, this.length, 0);
        glEnd();
        glPopMatrix();
    }


    private void checkCollision() {

    }
}
