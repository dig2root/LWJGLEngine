package engine;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.util.ArrayList;

public class DAELoader {

    public static ArrayList<Float> vertices = new ArrayList<Float>();
    public static ArrayList<Float> textureCoords = new ArrayList<Float>();
    public static ArrayList<Float> normals = new ArrayList<Float>();
    public static ArrayList<Float> colors = new ArrayList<Float>();

    public static void loadModelFile(String filename) {
        AIScene scene = Assimp.aiImportFile("models/" + filename, Assimp.aiProcess_Triangulate);
        PointerBuffer buffer = scene.mMeshes();

        for (int i = 0; i < buffer.limit(); i++) {
            AIMesh mesh = AIMesh.create(buffer.get(i));
            processMesh(mesh);
        }

        Assimp.aiReleaseImport(scene);
    }

    private static void processMesh(AIMesh mesh) {
        AIVector3D.Buffer vectors = mesh.mVertices();
        for (int i = 0; i < vectors.limit(); i++) {
            AIVector3D vector = vectors.get(i);
            vertices.add(vector.x());
            vertices.add(vector.y());
            vertices.add(vector.z());
        }

        AIVector3D.Buffer textCoordsVec = mesh.mTextureCoords(0);
        for (int i = 0; i < textCoordsVec.limit(); i++) {
            AIVector3D textCoordVec = textCoordsVec.get(i);
            textureCoords.add(textCoordVec.x());
            textureCoords.add(textCoordVec.y());
        }

        AIVector3D.Buffer normalsVec = mesh.mNormals();
        for (int i = 0; i < normalsVec.limit(); i++) {
            AIVector3D normalVec = normalsVec.get(i);
            normals.add(normalVec.x());
            normals.add(normalVec.y());
            normals.add(normalVec.z());
        }

        AIColor4D.Buffer vertexColors = mesh.mColors(0);
        for (int i = 0; i < vertexColors.limit(); i++) {
            AIColor4D vertexColor = vertexColors.get(i);
            colors.add(vertexColor.r());
            colors.add(vertexColor.g());
            colors.add(vertexColor.b());
            colors.add(vertexColor.a());
        }
    }
}
