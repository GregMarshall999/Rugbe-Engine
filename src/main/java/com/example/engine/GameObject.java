package com.example.engine;

import com.example.components.Component;

import java.util.ArrayList;
import java.util.List;

public class GameObject
{
    private static int ID_COUNTER = 0;
    private int uid = -1;

    private String name;
    private List<Component> components;
    private Transform transform;
    private int zIndex;

    public GameObject(String name, Transform transform, int zIndex) {
        this.name = name;
        components = new ArrayList<>();
        this.transform = transform;
        this.zIndex = zIndex;
        uid = ID_COUNTER++;
    }

    public Transform getTransform() {
        return transform;
    }

    public int getZIndex() {
        return zIndex;
    }

    public <T extends Component> T getComponents(Class<T> componentClass) {
        for (Component c : components)
            if(componentClass.isAssignableFrom(c.getClass()))
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting Component.";
                }

        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for(int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c) {
        c.generateId();
        components.add(c);
        c.setGameObject(this);
    }

    public void update(float dt) {
        components.forEach(component -> component.update(dt));
    }

    public void start() {
        components.forEach(Component::start);
    }

    public void imGui() {
        for(Component c : components)
            c.imGui();
    }

    public static void init(int maxId) {
        ID_COUNTER = maxId;
    }

    public int getUid() {
        return uid;
    }

    public List<Component> getAllComponents() {
        return components;
    }
}
