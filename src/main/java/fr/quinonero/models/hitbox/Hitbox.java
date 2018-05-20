package fr.quinonero.models.hitbox;

import fr.quinonero.gui.Controller;
import fr.quinonero.models.Face;
import fr.quinonero.models.Model;
import fr.quinonero.utils.VectorPair;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hitbox {

    public List<VectorPair<Vector3f, Vector3f>> points = new ArrayList<>();
    public List<VectorPair<Vector3f, Vector3f>> initialPoints = new ArrayList<>();
    private HashMap<VectorPair<Vector3f, Vector3f>, Integer> entryCounter = new HashMap<>();

    public Hitbox(Model m) {
        /*

        face : 1 / 2 / 3

        1 - 2
        2 - 3
        3 - 1

         */

        for (Face f : m.faces) {
            this.initialPoints.add(new VectorPair<>(f.points[0], f.points[1]));
            this.initialPoints.add(new VectorPair<>(f.points[1], f.points[2]));
            this.initialPoints.add(new VectorPair<>(f.points[2], f.points[0]));

            this.entryCounter.put(new VectorPair<>(f.points[0], f.points[1]), 0);
            this.entryCounter.put(new VectorPair<>(f.points[1], f.points[2]), 0);
            this.entryCounter.put(new VectorPair<>(f.points[2], f.points[0]), 0);

        }


        for (VectorPair<Vector3f, Vector3f> p : this.initialPoints) {
            this.entryCounter.put(p, this.entryCounter.get(p) + 1);
        }


        System.out.println(this.entryCounter);

        for(Map.Entry<VectorPair<Vector3f, Vector3f>, Integer> entry : this.entryCounter.entrySet()) {

            if(entry.getValue() >= 2)
                this.initialPoints.remove(entry.getKey());
        }

        this.points.addAll(this.initialPoints);

        System.out.println(this.initialPoints.size() + " " + this.points.size());
    }

    public boolean intersecting2D(float x, float y) {

        for (VectorPair<Vector3f, Vector3f> p : points) {

            if (p.getValue().x > x && p.getKey().x < x && p.getValue().y > y && p.getKey().y < y)
                return true;

        }
        return false;

    }

    public void updateAABB(double x, double y) {
        this.points.clear();

        for (VectorPair<Vector3f, Vector3f> p : initialPoints) {

            Vector3f min = new Vector3f();
            Vector3f max = new Vector3f();

            min.x = p.getValue().x;
            min.y = p.getValue().y;
            min.z = p.getValue().z;
            max.x = p.getKey().x;
            max.y = p.getKey().y;
            max.z = p.getKey().z;

            min.x *= Controller.INSTANCE.modelScale.getValue();
            min.y *= Controller.INSTANCE.modelScale.getValue();
            max.x *= Controller.INSTANCE.modelScale.getValue();
            max.y *= Controller.INSTANCE.modelScale.getValue();

            min.x += x;
            min.y += y;
            max.x += x;
            max.y += y;

            // System.out.println(min + " " + max);

            this.points.add(new VectorPair<>(min, max));
        }
    }
}