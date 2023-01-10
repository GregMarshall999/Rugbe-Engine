package com.example.engine;

import com.example.components.SpriteRenderer;
import com.example.components.SpriteSheet;
import com.example.util.AssetPool;
import org.joml.Vector2f;

public class LevelEditorScene extends Scene
{
    private int spriteIndex = 0;
    private float spriteFlipTime = .2f;
    private float spriteFlipTimeLeft = 0f;

    private GameObject obj;
    private SpriteSheet sprites;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();

        camera = new Camera(new Vector2f(-250, 0));

        sprites = AssetPool.getSpriteSheet("assets/images/spritesheet.png");

        obj = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj.addComponent(new SpriteRenderer(sprites.getSprites(0)));
        addGameObjectToScene(obj);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(sprites.getSprites(15)));
        addGameObjectToScene(obj2);
    }

    @Override
    public void update(float dt) {
        spriteFlipTimeLeft -= dt;
        if(spriteFlipTimeLeft <=0) {
            spriteFlipTimeLeft = spriteFlipTime;
            spriteIndex++;
            if(spriteIndex > 4)
                spriteIndex = 0;

            obj.getComponents(SpriteRenderer.class).setSprite(sprites.getSprites(spriteIndex));
        }

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
