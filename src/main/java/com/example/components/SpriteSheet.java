package com.example.components;

import com.example.renderer.Texture;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class SpriteSheet
{
    private final List<Sprite> sprites;

    public SpriteSheet(Texture texture, int spriteWidth, int spriteHeight, int numSprites, int spacing) {
        sprites = new ArrayList<>();

        int currentX = 0;
        int currentY = texture.getHeight() - spriteHeight;
        for(int i = 0; i < numSprites; i++) {
            float topY = (currentY + spriteHeight)/(float)texture.getHeight();
            float rightX = (currentX + spriteWidth)/(float)texture.getWidth();
            float leftX = currentX/(float)texture.getWidth();
            float bottomY = currentY/(float)texture.getHeight();

            Vector2f[] texCoords = {
                    new Vector2f(rightX, topY),
                    new Vector2f(rightX, bottomY),
                    new Vector2f(leftX, bottomY),
                    new Vector2f(leftX, topY),
            };
            Sprite sprite = new Sprite();
            sprite.setTexCoords(texCoords);
            sprite.setTexture(texture);
            this.sprites.add(sprite);

            currentX += spriteWidth + spacing;
            if(currentX >= texture.getWidth()) {
                currentX = 0;
                currentY -= spriteHeight + spacing;
            }
        }
    }

    public Sprite getSprites(int index) {
        return sprites.get(index);
    }
}
