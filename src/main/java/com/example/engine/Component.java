package com.example.engine;

public abstract class Component
{
    protected GameObject gameObject;

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void start() {

    }

    public abstract void update(float dt);
}
