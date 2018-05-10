package fr.quinonero.models;

import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    public static void renderModel(Model m){
        for(Face f : m.faces){
            glPushMatrix();
            glTranslatef(100,100,0);
            glScalef(5,5,0);
            glBegin(GL_TRIANGLE_FAN);
            for(Vector3f v : f.points)
            glVertex3f(v.x, v.y, v.z); // center of circle
            glEnd();
            glPopMatrix();
        }
    }
}
