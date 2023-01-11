package com.example.renderer;

import com.example.components.SpriteRenderer;
import com.example.engine.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer
{
    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatch> batches;

    public Renderer() {
        batches = new ArrayList<>();
    }

    public void add(GameObject obj) {
        SpriteRenderer spr = obj.getComponents(SpriteRenderer.class);
        if(spr != null)
            add(spr);
    }

    private void add(SpriteRenderer sprite) {
        boolean added = false;

        for(RenderBatch batch : batches)
            if(batch.hasRoom() && batch.getZIndex() == sprite.getGameObject().getZIndex()) {
                Texture tex = sprite.getTexture();
                if(tex == null || (batch.hasTexture(tex) || batch.hasTextureRoom())) {
                    batch.addSprite(sprite);
                    added = true;
                    break;
                }
            }

        if(!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, sprite.getGameObject().getZIndex());
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(sprite);
            Collections.sort(batches);
        }
    }

    public void render() {
        batches.forEach(RenderBatch::render);
    }
}
