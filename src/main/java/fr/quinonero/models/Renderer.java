package fr.quinonero.models;

import fr.quinonero.gui.Controller;
import fr.quinonero.utils.VectorPair;
import javafx.util.Pair;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    public static void renderModel(Model m) {

      //  m.hitbox.updateAABB(Controller.INSTANCE.modelXPos.getValue(), Controller.INSTANCE.modelYPos.getValue());

        for (Face f : m.faces) {

            glPushMatrix();
            glColor3d(Controller.INSTANCE.colorModel.getValue().getRed(), Controller.INSTANCE.colorModel.getValue().getGreen(), Controller.INSTANCE.colorModel.getValue().getBlue());
            glTranslated(Controller.INSTANCE.modelXPos.getValue(), Controller.INSTANCE.modelYPos.getValue(), 0);
            glScaled(Controller.INSTANCE.modelScale.getValue(), Controller.INSTANCE.modelScale.getValue(), 0);
            glBegin(GL_TRIANGLE_FAN);
            for (Vector3f v : f.points)
                glVertex3f(v.x, v.y, v.z);
            glEnd();
            glPopMatrix();

        }
        if (Controller.INSTANCE.renderHitbox.isSelected())
            renderHitbox(m);
    }

    public static void renderHitbox(Model m) {
        glPushMatrix();
        glColor3d(Controller.INSTANCE.colorHitbox.getValue().getRed(), Controller.INSTANCE.colorHitbox.getValue().getGreen(), Controller.INSTANCE.colorHitbox.getValue().getBlue());
        glTranslated(700, 250, 0);
        glScaled(10,10, 0);
        glBegin(GL_LINES);
        for (VectorPair<Vector3f, Vector3f> p : m.hitbox.points) {
            glVertex3f(p.getKey().x, p.getKey().y, p.getKey().z);
            glVertex3f(p.getValue().x, p.getValue().y, p.getValue().z);
        }
        glEnd();
        glPopMatrix();
    }
}
