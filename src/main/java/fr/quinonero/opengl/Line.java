package fr.quinonero.opengl;

import fr.quinonero.gui.Controller;
import fr.quinonero.thread.OpenGLThread;
import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class Line {

    public Vector2f delAbsRelCoord;
    public Line src;
    public float length = 2;
    public float deg, impactX, impactY;
    public boolean isSource;
    public boolean isDephased;
    public boolean gotImpact = false;

    public Line(float deg, boolean isSource, boolean isDephased) {
        this.deg = deg;
        this.isSource = isSource;
        this.isDephased = isDephased;
        this.delAbsRelCoord = new Vector2f((float) Controller.INSTANCE.srcXPos.getValue(), (float) Controller.INSTANCE.srcYPos.getValue());
    }

    public Line(int deg, boolean isSource, boolean isDephased, Line src) {
        this.deg = deg;
        this.isSource = isSource;
        this.isDephased = isDephased;
        this.src = src;
        this.delAbsRelCoord = new Vector2f(this.src.delAbsRelCoord.x + this.src.impactX, this.src.delAbsRelCoord.y + this.src.impactY);
    }

    public void update() {

        if (this.isSource) {
            if (this.deg == 0) {
                this.impactX = length;
                this.impactY = 0;
            } else if (this.deg == 90) {
                this.impactX = 0;
                this.impactY = -length;
            } else if (this.deg == 180) {
                this.impactX = -length;
                this.impactY = 0;
            } else if (this.deg == 270) {
                this.impactX = 0;
                this.impactY = length;
            } else {
                this.impactX = (float) (length * Math.cos(Math.toRadians(this.deg)));
                this.impactY = (float) (length * Math.sin(Math.toRadians(this.deg)));
            }
        } else {
            this.impactX = (float) (length * Math.cos(Math.toRadians(this.deg)) + this.src.impactX);
            this.impactY = (float) (length * Math.sin(Math.toRadians(this.deg)) + this.src.impactY);
        }


        //      System.out.println(this.impactX + " " + this.impactY);

        if (!gotImpact) {
            checkCollision();
            this.length += 0.01;
        }
    }

    public void render() {
        glPushMatrix();
        if (this.isSource) {
            glTranslated(Controller.INSTANCE.srcXPos.getValue(), Controller.INSTANCE.srcYPos.getValue(), 0);
        } else {
            glTranslated(this.impactX + this.delAbsRelCoord.x, impactY + this.delAbsRelCoord.y, 0);
        }
        glColor3d(Controller.INSTANCE.colorRay.getValue().getRed(), Controller.INSTANCE.colorRay.getValue().getGreen(), Controller.INSTANCE.colorRay.getValue().getBlue());
        if (this.isDephased)
            glColor3d(0, 255, 0);

        switch ((int) this.deg) {
            case 0:
                glColor3d(255, 255, 255);
                break;
            case 90:
                glColor3d(255, 0, 0);
                break;
            case 180:
                glColor3d(0, 255, 0);
                break;
            case 270:
                glColor3d(0, 0, 255);
                break;
        }


        //  glRotated(this.deg, 0, 0, 1);
        glBegin(GL_LINES);
        glVertex3d(0, 0, 0);
        glVertex3d(this.impactX, this.impactY, 0);
        glEnd();
        glPopMatrix();

    }


    private void checkCollision() {

        if (OpenGLThread.structModel != null && OpenGLThread.structModel.faces.size() > 0)

            if (OpenGLThread.structModel.hitbox.intersecting2D(this.impactX + this.delAbsRelCoord.x, impactY + this.delAbsRelCoord.y)) {
                System.out.println("INTERSET");
                this.gotImpact = true;
                OpenGLThread.loopLines.add(new Line(1, false, !this.isDephased, this));
            }
    }
}
