package com.example.components;

import com.example.engine.GameObject;
import com.example.engine.MouseListener;
import com.example.engine.Window;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class MouseControls extends Component
{
    GameObject holdingObject = null;

    public void pickupObject(GameObject gameObject) {
        holdingObject = gameObject;
        Window.getScene().addGameObjectToScene(gameObject);
    }

    public void place() {
        holdingObject = null;
    }

    @Override
    public void update(float dt) {
        if(holdingObject != null) {
            holdingObject.getTransform().getPosition().x = MouseListener.getOrthoX();
            holdingObject.getTransform().getPosition().y = MouseListener.getOrthoY();

            if(MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                place();
            }
        }
    }
}
