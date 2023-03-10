package main;

import engine.*;
import engine.entity.Entity;
import engine.entity.Model;
import engine.utils.Utils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class TestGameDAE implements ILogic {

    private static final float CAMERA_MOVE_SPEED = 0.05f;

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private Entity entity;
    private Camera camera;

    private Vector3f cameraInc;

    public TestGameDAE() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
        camera = new Camera(new Vector3f(0, 0, 2), new Vector3f(0, 0, 0));
        cameraInc = new Vector3f(0, 0, 0);
    }

    @Override
    public void init() throws Exception {
        renderer.init();

        // Chargement du modele 3D texture
        DAELoader.loadModelFile("house.dae");
        float[] vertices = Utils.arrayListOfFloatToFloatArray(DAELoader.vertices);
        float[] textureCoords = Utils.arrayListOfFloatToFloatArray(DAELoader.textureCoords);
        float[] normals = Utils.arrayListOfFloatToFloatArray(DAELoader.normals);
        float[] colors = Utils.arrayListOfFloatToFloatArray(DAELoader.colors);

        // Creation du model associe
        Model model = loader.loadModel(vertices, textureCoords, normals, colors);
        entity = new Entity(model, new Vector3f(0, 0,0), new Vector3f(0, 0, 0), 1);
    }

    @Override
    public void input() {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW.GLFW_KEY_SPACE)){
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)){
            cameraInc.z = -1;
        }
        if (window.isKeyPressed(GLFW.GLFW_KEY_A)){
            cameraInc.x = -1;
        }
        if (window.isKeyPressed(GLFW.GLFW_KEY_D)){
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW.GLFW_KEY_S)){
            cameraInc.y = -1;
        }
        if (window.isKeyPressed(GLFW.GLFW_KEY_W)){
            cameraInc.y = 1;
        }
    }

    @Override
    public void update(MouseManager mouseManager) {
        // Mise ?? jour de la cam??ra
        Vector2f rotVec = mouseManager.getDisplVec();
        camera.moveRotation(rotVec.x * mouseManager.MOUSE_SENSITIVITY, 0, rotVec.y * mouseManager.MOUSE_SENSITIVITY);
        camera.movePosition((float) (cameraInc.y * CAMERA_MOVE_SPEED * Math.sin(Math.toRadians(camera.getRotation().z))
                + cameraInc.x * CAMERA_MOVE_SPEED * Math.cos(Math.toRadians(camera.getRotation().z))),
                (float) (cameraInc.y * CAMERA_MOVE_SPEED * Math.cos(Math.toRadians(camera.getRotation().z))
                - cameraInc.x * CAMERA_MOVE_SPEED * Math.sin(Math.toRadians(camera.getRotation().z))),
                cameraInc.z * CAMERA_MOVE_SPEED);
    }

    @Override
    public void render() {
        if (window.isResize()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        window.setClearColour(0.2f, 0.94f, 1.0f, 0f);
        renderer.render(entity, camera);
    }

    @Override
    public void cleanUp() {
        renderer.cleanUp();
        loader.cleanUp();
    }
}
