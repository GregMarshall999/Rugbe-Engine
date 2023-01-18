package com.example.scenes;

import com.example.engine.Window;
import com.example.scenes.Scene;

public class LevelScene extends Scene
{
    public LevelScene() {
        System.out.println("Level Scene");
        Window.get().setR(1);
        Window.get().setG(1);
        Window.get().setB(1);
    }

    @Override
    public void update(float dt) {

    }
}
