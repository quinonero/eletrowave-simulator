package fr.quinonero.models;

import fr.quinonero.models.hitbox.Hitbox;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Model
{

    public List<Vector3f> verts = new ArrayList<Vector3f>();
    public List<Face> faces = new ArrayList<Face>();
    public Hitbox hitbox;


    public Model()
    {

    }


    public void setUpAABB()
    {
       this.hitbox = new Hitbox(this);
    }
}