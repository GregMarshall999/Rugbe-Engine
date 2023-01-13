package com.example.engine;

import com.example.renderer.Renderer;
import imgui.ImGui;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene
{
    Camera camera;
    Renderer renderer = new Renderer();
    List<GameObject> gameObjects = new ArrayList<>();
    GameObject activeGameObject;

    private boolean isRunning = false;

    public Scene() {

    }

    public void init() {

    }

    public void start() {
        for(GameObject gameObject : gameObjects) {
            gameObject.start();
            renderer.add(gameObject);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject gameObject) {
        gameObjects.add(gameObject);

        if(isRunning) {
            gameObject.start();
            renderer.add(gameObject);
        }
    }

    public abstract void update(float dt);

    public Camera camera() {
        return camera;
    }

    public void sceneImGui() {
        if(activeGameObject != null) {
            ImGui.begin("Inspector");
            activeGameObject.imGui();
            ImGui.end();
        }

        imGui();
    }

    public void imGui() {

    }
}
