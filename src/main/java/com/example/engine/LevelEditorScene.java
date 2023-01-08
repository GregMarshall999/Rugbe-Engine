package com.example.engine;

import com.example.components.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene
{
    public LevelEditorScene() {

    }

    @Override
    public void init() {
        camera = new Camera(new Vector2f());

        int xOffset = 10;
        int yOffset = 10;

        float totalWidth = 600f - xOffset*2;
        float totalHeight = 300f - yOffset*2;
        float sizeX = totalWidth/100f;
        float sizeY = totalHeight/100f;

        for(int x = 0; x < 100; x++) {
            for(int y = 0; y < 100; y++) {
                float xPos = xOffset + x*sizeX;
                float yPos = yOffset + y*sizeY;

                GameObject go = new GameObject("Obj" + x + "" + y, new Transform(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY)));
                go.addComponent(new SpriteRenderer(new Vector4f(xPos/totalWidth, yPos/totalHeight, 1, 1)));
                addGameObjectToScene(go);
            }
        }
    }

    @Override
    public void update(float dt) {
        System.out.println("FPS: " + 1f/dt);

        gameObjects.forEach(gameObject -> gameObject.update(dt));
        renderer.render();
    }
}
