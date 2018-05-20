package fr.quinonero.models;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Face
{

    public Vector3f[] points;

    /**
     * Not reccomended to use this
     * constructor
     */
    public Face(Vector3f... points)
    {
        this.points = points;
    }

    public Face()
    {

    }
}