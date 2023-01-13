package com.example.components;

import com.example.engine.Component;
import com.example.engine.Transform;
import com.example.renderer.Texture;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component
{
    private Vector4f color;
    private Sprite sprite;

    private Transform lastTransform;
    private boolean isDirty;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
        sprite = new Sprite(null);
        isDirty = true;
    }

    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
        color = new Vector4f(1, 1, 1, 1);
        isDirty = true;
    }

    @Override
    public void start() {
        lastTransform = gameObject.getTransform().copy();
    }

    @Override
    public void update(float dt) {
        if(!lastTransform.equals(gameObject.getTransform())) {
            gameObject.getTransform().copy(lastTransform);
            isDirty = true;
        }
    }

    @Override
    public void imGui() {
        float[] imColor = {color.x, color.y, color.z, color.w};
        if (ImGui.colorPicker4("Color Picker: ", imColor)) {
            color.set(imColor[0], imColor[1], imColor[2], imColor[3]);
            isDirty = true;
        }
    }

    public Vector4f getColor() {
        return color;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        isDirty = true;
    }

    public void setColor(Vector4f color) {
        if(!this.color.equals(color)) {
            this.color.set(color);
            isDirty = true;
        }
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setClean() {
        isDirty = false;
    }
}
