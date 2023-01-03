package com.example.rugbe;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene
{
    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;

    public LevelEditorScene() {
        System.out.println("Level Editor Scene");
    }

    @Override
    public void update(float dt) {

        System.out.println(1f/dt + " FPS");

        if(!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE))
            changingScene = true;

        if(changingScene && timeToChangeScene > 0) {
            timeToChangeScene -= dt;
            Window.get().setR(Window.get().getR() - dt*5f);
            Window.get().setG(Window.get().getG() - dt*5f);
            Window.get().setB(Window.get().getB() - dt*5f);
        } else if(changingScene) {
            Window.changeScene(1);
        }
    }
}
