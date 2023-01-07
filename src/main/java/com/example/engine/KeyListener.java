package com.example.engine;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener
{
    private static KeyListener instance;

    private final boolean[] keyPressed = new boolean[350];

    private KeyListener() {}

    public static KeyListener get() {
        if(instance == null)
            instance = new KeyListener();

        return instance;
    }

    public static void keyCallBack(long window, int key, int scanCode, int action, int mods) {
        switch (action) {
            case GLFW_PRESS -> get().keyPressed[key] = true;
            case GLFW_RELEASE -> get().keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        return get().keyPressed[keyCode];
    }
}
