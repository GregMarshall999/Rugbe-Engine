package com.example.util;

import com.example.components.SpriteSheet;
import com.example.renderer.Shader;
import com.example.renderer.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool
{
    private final static Map<String, Shader> shaders = new HashMap<>();
    private final static Map<String, SpriteSheet> spriteSheets = new HashMap<>();
    private final static Map<String, Texture> textures = new HashMap<>();

    public static Shader getShader(String resourceName) {
        File file = new File(resourceName);

        if(shaders.containsKey(file.getAbsolutePath()))
            return shaders.get(file.getAbsolutePath());
        else {
            Shader shader = new Shader(resourceName);
            shader.compile();
            shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }
    }

    public static SpriteSheet getSpriteSheet(String resourceName) {
        File file = new File(resourceName);
        assert spriteSheets.containsKey(file.getAbsolutePath()) : "Error: Tried to access sprite sheet '" + resourceName + "', nonexistent in asset pool.";

        return spriteSheets.getOrDefault(file.getAbsolutePath(), null);
    }

    public static Texture getTexture(String resourceName) {
        File file = new File(resourceName);

        if(textures.containsKey(file.getAbsolutePath()))
            return textures.get(file.getAbsolutePath());
        else {
            Texture texture = new Texture();
            texture.init(resourceName);
            textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    public static void addSpriteSheet(String resourceName, SpriteSheet spriteSheet) {
        File file = new File(resourceName);
        if(!spriteSheets.containsKey(file.getAbsolutePath()))
            spriteSheets.put(file.getAbsolutePath(), spriteSheet);
    }
}
