package com.example.engine;

import com.example.components.RigidBody;
import com.example.components.Sprite;
import com.example.components.SpriteRenderer;
import com.example.components.SpriteSheet;
import com.example.util.AssetPool;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene
{
    private GameObject obj;
    private SpriteSheet sprites;
    SpriteRenderer objSprite;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();

        camera = new Camera(new Vector2f(-250, 0));
        if(levelLoaded) {
            activeGameObject = gameObjects.get(0);
            return;
        }

        sprites = AssetPool.getSpriteSheet("assets/images/spritesheet.png");

        obj = new GameObject("Object 1", new Transform(new Vector2f(200, 100), new Vector2f(256, 256)), 2);
        objSprite = new SpriteRenderer();
        objSprite.setColor(new Vector4f(1, 0, 0, 1));
        obj.addComponent(objSprite);
        obj.addComponent(new RigidBody());
        addGameObjectToScene(obj);
        activeGameObject = obj;

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), 3);
        SpriteRenderer obj2SR = new SpriteRenderer();
        Sprite obj2SP = new Sprite();
        obj2SP.setTexture(AssetPool.getTexture("assets/images/blendImage2.png"));
        obj2SR.setSprite(obj2SP);
        obj2.addComponent(obj2SR);
        addGameObjectToScene(obj2);
    }

    @Override
    public void update(float dt) {
        for(GameObject gameObject : gameObjects)
            gameObject.update(dt);
        renderer.render();
    }

    @Override
    public void imGui() {
        ImGui.begin("Test window");
        ImGui.text("Some random text");
        ImGui.end();
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpriteSheet("assets/images/spritesheet.png",
                new SpriteSheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16, 16, 26, 0));
        AssetPool.getTexture("assets/images/blendImage2.png");
    }
}
