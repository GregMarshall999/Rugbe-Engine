package com.example.scenes;

import com.example.components.Component;
import com.example.components.ComponentDeserializer;
import com.example.engine.Camera;
import com.example.engine.GameObject;
import com.example.engine.GameObjectDeserializer;
import com.example.renderer.Renderer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImGui;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene
{
    protected Camera camera;
    protected Renderer renderer = new Renderer();
    protected List<GameObject> gameObjects = new ArrayList<>();
    protected GameObject activeGameObject;
    protected boolean levelLoaded = false;

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

    public void saveExit() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();

        try {
            FileWriter writer = new FileWriter("level.txt");
            writer.write(gson.toJson(gameObjects));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();

        String inFile = "";
        try {
            inFile = new String(Files.readAllBytes(Paths.get("level.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!inFile.equals("")) {
            int maxGoId = -1;
            int maxCompId = -1;
            GameObject[] objs = gson.fromJson(inFile, GameObject[].class);
            for(GameObject obj : objs) {
                addGameObjectToScene(obj);

                for(Component c : obj.getAllComponents()) {
                    if(c.getUid() > maxCompId) {
                        maxCompId = c.getUid();
                    }
                }
                if(obj.getUid() > maxGoId) {
                    maxGoId = obj.getUid();
                }
            }
            maxCompId++;
            maxGoId++;
            GameObject.init(maxGoId);
            Component.init(maxCompId);
            levelLoaded = true;
        }
    }
}
