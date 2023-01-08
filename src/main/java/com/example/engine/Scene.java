package com.example.engine;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene
{
    Camera camera;
    List<GameObject> gameObjects = new ArrayList<>();

    private boolean isRunning = false;

    public Scene() {

    }

    public void init() {

    }

    public void start() {
        gameObjects.forEach(GameObject::start);
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject gameObject) {
        gameObjects.add(gameObject);

        if(isRunning)
            gameObject.start();
    }

    public abstract void update(float dt);
}
