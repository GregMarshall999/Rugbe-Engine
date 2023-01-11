package com.example.engine;

import com.example.components.Sprite;
import com.example.components.SpriteRenderer;
import com.example.components.SpriteSheet;
import com.example.util.AssetPool;
import org.joml.Vector2f;

public class LevelEditorScene extends Scene
{
    private GameObject obj;
    private SpriteSheet sprites;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();

        camera = new Camera(new Vector2f(-250, 0));

        sprites = AssetPool.getSpriteSheet("assets/images/spritesheet.png");

        obj = new GameObject("Object 1", new Transform(new Vector2f(200, 100), new Vector2f(256, 256)), 2);
        obj.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/blendImage1.png"))));
        addGameObjectToScene(obj);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), 1);
        obj2.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/blendImage2.png"))));
        addGameObjectToScene(obj2);
    }

    @Override
    public void update(float dt) {
        gameObjects.forEach(gameObject -> gameObject.update(dt));
        renderer.render();
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpriteSheet("assets/images/spritesheet.png",
                new SpriteSheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16, 16, 26, 0));
    }
}
