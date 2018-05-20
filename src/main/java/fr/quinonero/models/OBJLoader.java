package fr.quinonero.models;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class OBJLoader
{
    /**
     * Loads a model.
     * Requirements for model:
     * <ul>
     * <li><b>Wavefront</b> .obj format</li>
     * <li>Triangulated faces</li>
     * </ul>
     *
     * @param f - The path of the .obj file
     * @return a Model object of the given file
     */
    public static Model loadModel(File f)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(f));

            Model m = new Model();

            String line;
            while ((line = reader.readLine()) != null)
            {
                //Indicates a vertex
                if (line.startsWith("v "))
                {
                    float x = Float.valueOf(line.split(" ")[1]);
                    float y = Float.valueOf(line.split(" ")[2]);
                    float z = Float.valueOf(line.split(" ")[3]);
                    m.verts.add(new Vector3f(x, y, z));
                }
                //Indicates a face
                else if (line.startsWith("f "))
                {
                    //If face is triangulated
                    if(line.split(" ").length == 4)
                    {
                        Vector3f vertexIndices = new Vector3f(
                                Float.valueOf(line.split(" ")[1]),
                                Float.valueOf(line.split(" ")[2]),
                                Float.valueOf(line.split(" ")[3]));


                        Face mf = new Face();

                        //Instantiate all the arrays
                        mf.points = new Vector3f[3];

                        //// SETUP VERTICIES ////
                        Vector3f v1 = m.verts.get((int)vertexIndices.x - 1);
                        mf.points[0] = v1;
                        Vector3f v2 = m.verts.get((int)vertexIndices.y - 1);
                        mf.points[1] = v2;
                        Vector3f v3 = m.verts.get((int)vertexIndices.z - 1);
                        mf.points[2] = v3;

                        m.faces.add(mf);
                    }
                }
            }
            reader.close();

            //Tell model to set up AABB
            m.setUpAABB();

            //Remove the first element, because...
        //    m.faces.remove(0);

            return m;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}