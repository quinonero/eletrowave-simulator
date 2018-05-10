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
     * <li>Normals included</li>
     * <li>One or no image files</li>
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
                //Indicates a vertex normal
                else if (line.startsWith("vn "))
                {
                    float x = Float.valueOf(line.split(" ")[1]);
                    float y = Float.valueOf(line.split(" ")[2]);
                    float z = Float.valueOf(line.split(" ")[3]);
                    m.norms.add(new Vector3f(x, y, z));
                }
                //Indicates a texture coordinate
                else if (line.startsWith("vt "))
                {
                    float x = Float.valueOf(line.split(" ")[1]);
                    float y = Float.valueOf(line.split(" ")[2]);
                    m.textureCoords.add(new Vector2f(x, y));
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


                        Vector3f normalIndices = new Vector3f(
                                Float.valueOf(line.split(" ")[1]),
                                Float.valueOf(line.split(" ")[2]),
                                Float.valueOf(line.split(" ")[3]));

                        Face mf = new Face();

                        //Instantiate all the arrays
                        mf.normals = new Vector3f[3];
                        mf.points = new Vector3f[3];

                        if(m.norms.size() != 0) {
                            //// SETUP NORMALS ////
                            Vector3f n1 = m.norms.get((int) normalIndices.x - 1);
                            mf.normals[0] = n1;
                            Vector3f n2 = m.norms.get((int) normalIndices.y - 1);
                            mf.normals[1] = n2;
                            Vector3f n3 = m.norms.get((int) normalIndices.z - 1);
                            mf.normals[2] = n3;
                        }

                        //// SETUP VERTICIES ////
                        Vector3f v1 = m.verts.get((int)vertexIndices.x - 1);
                        mf.points[0] = v1;
                        Vector3f v2 = m.verts.get((int)vertexIndices.y - 1);
                        mf.points[1] = v2;
                        Vector3f v3 = m.verts.get((int)vertexIndices.z - 1);
                        mf.points[2] = v3;


                        //Tell face to set up AABB
                        mf.setUpAABB();

                        m.faces.add(mf);
                    }
                }
            }
            reader.close();

            //Tell model to set up AABB
            m.setUpAABB();

            //Remove the first element, because...
            m.faces.remove(0);

            return m;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static FloatBuffer reserveData(int size) {
        return BufferUtils.createFloatBuffer(size);
    }

    private static float[] asFloats(Vector3f v) {
        return new float[]{v.x, v.y, v.z};
    }
    private static float[] asFloats(Vector2f v) {
        return new float[]{v.x, v.y};
    }

    /**
     * Returns An Object[][]
     *
     * <ul>
     * 	<li>return[0][0] - vboVertexHandle:int</li>
     * 	<li>return[0][1] - vboNormalHandle:int</li>
     * 	<li>return[0][2] - vboTextureHandle:int</li>
     *  <li>return[0][3] - vboColorHandle:int</li>
     *  <li>return[0][4] - vboTexIDHandle:int</li>
     * 	<li>return[1][0] - vertices:FloatBuffer</li>
     * 	<li>return[1][1] - normals:FloatBuffer</li>
     * 	<li>return[1][2] - textureCoordinates:FloatBuffer</li>
     *  <li>return[1][3] - colors:FloatBuffer</li>
     *  <li>return[1][4] - textureID:FloatBuffer</li>
     * </ul>
     */
    public static Object[][] createVBO(Model model)
    {
        int vboVertexHandle = glGenBuffers();
        int vboNormalHandle = glGenBuffers();
        int vboColorHandle = glGenBuffers();
        int vboTexHandle = glGenBuffers();
        int vboTexIDHandle = glGenBuffers();

        FloatBuffer vertices = reserveData(model.faces.size() * 9);
        FloatBuffer normals = reserveData(model.faces.size() * 9);
        FloatBuffer colors = reserveData(model.faces.size() * 9);
        FloatBuffer textCoords = reserveData(model.faces.size() * 6);
        IntBuffer textID = BufferUtils.createIntBuffer(model.faces.size() * 3);//reserveData(model.faces.size() * 3);

        for (Face face : model.faces)
        {
            vertices.put(asFloats(face.points[0]));
            vertices.put(asFloats(face.points[1]));
            vertices.put(asFloats(face.points[2]));

            normals.put(asFloats(face.normals[0]));
            normals.put(asFloats(face.normals[1]));
            normals.put(asFloats(face.normals[2]));

            if(face.textureCoords!=null)
            {
                textCoords.put(asFloats(face.textureCoords[0]));
                textCoords.put(asFloats(face.textureCoords[1]));
                textCoords.put(asFloats(face.textureCoords[2]));
            }
        }
        vertices.flip();
        normals.flip();
        colors.flip();
        textCoords.flip();
        textID.flip();

        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glVertexPointer(3, GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
        glBufferData(GL_ARRAY_BUFFER, normals, GL_STATIC_DRAW);
        glNormalPointer(GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle);
        glBufferData(GL_ARRAY_BUFFER, colors, GL_STATIC_DRAW);
        glColorPointer(3, GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, vboTexHandle);
        glBufferData(GL_ARRAY_BUFFER, textCoords, GL_STATIC_DRAW);
        glTexCoordPointer(2, GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, vboTexIDHandle);
        glBufferData(GL_ARRAY_BUFFER, textID, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return new Object[][]{
                {vboVertexHandle, vertices},
                {vboNormalHandle, normals},
                {vboTexHandle, textCoords},
                {vboColorHandle, colors},
                {vboTexIDHandle, textID}
        };
    }
}