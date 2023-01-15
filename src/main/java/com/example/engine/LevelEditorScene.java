package com.example.engine;

import com.example.components.RigidBody;
import com.example.components.Sprite;
import com.example.components.SpriteRenderer;
import com.example.components.SpriteSheet;
import com.example.util.AssetPool;
import imgui.ImGui;
import imgui.ImVec2;
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
        sprites = AssetPool.getSpriteSheet("assets/images/spritesheets/decorationsAndBlocks.png");
        camera = new Camera(new Vector2f(-250, 0));
        if(levelLoaded) {
            activeGameObject = gameObjects.get(0);
            return;
        }

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
        MouseListener.getOrthoX();

        for(GameObject gameObject : gameObjects)
            gameObject.update(dt);
        renderer.render();
    }

    @Override
    public void imGui() {
        ImGui.begin("Test window");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for(int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprites(i);
            float spriteWidth = sprite.getWidth()*4;
            float spriteHeight = sprite.getHeight()*4;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if(ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[0].x, texCoords[0].y, texCoords[2].x, texCoords[2].y)) {
                System.out.println("Button " + i + "clicked");
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if(i + 1 < sprites.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpriteSheet("assets/images/spritesheets/decorationsAndBlocks.png",
                new SpriteSheet(AssetPool.getTexture("assets/images/spritesheets/decorationsAndBlocks.png"),
                        16, 16, 81, 0));
        AssetPool.getTexture("assets/images/blendImage2.png");
    }
}
