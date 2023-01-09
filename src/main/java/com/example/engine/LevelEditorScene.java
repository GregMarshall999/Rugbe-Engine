package com.example.engine;

import com.example.components.SpriteRenderer;
import com.example.util.AssetPool;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

public class LevelEditorScene extends Scene
{
    public LevelEditorScene() {

    }

    @Override
    public void init() {
        camera = new Camera(new Vector2f(-250, 0));

        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/testImage.png")));
        addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/testImage2.png")));
        addGameObjectToScene(obj2);

        loadResources();
    }

    @Override
    public void update(float dt) {
        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT))
            camera.translate(new Vector2f(100f*dt, 0f));
        else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT))
            camera.translate(new Vector2f(-100f*dt, 0f));

        if (KeyListener.isKeyPressed(GLFW_KEY_UP))
            camera.translate(new Vector2f(0f, 100f*dt));
        else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN))
            camera.translate(new Vector2f(0f, -100f*dt));

        gameObjects.forEach(gameObject -> gameObject.update(dt));
        renderer.render();
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
    }
}
