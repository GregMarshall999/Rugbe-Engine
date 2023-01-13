package com.example.engine;

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

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();

        camera = new Camera(new Vector2f(-250, 0));

        sprites = AssetPool.getSpriteSheet("assets/images/spritesheet.png");

        obj = new GameObject("Object 1", new Transform(new Vector2f(200, 100), new Vector2f(256, 256)), 2);
        obj.addComponent(new SpriteRenderer(new Vector4f(1, 0, 0, 1)));
        addGameObjectToScene(obj);
        activeGameObject = obj;

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), 1);
        obj2.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/blendImage2.png"))));
        addGameObjectToScene(obj2);
    }

    @Override
    public void update(float dt) {
        gameObjects.forEach(gameObject -> gameObject.update(dt));
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
    }
}
