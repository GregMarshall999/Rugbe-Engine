package com.example.engine;

import java.util.ArrayList;
import java.util.List;

public class GameObject
{
    private String name;
    private List<Component> components;
    private Transform transform;

    public GameObject(String name) {
        this.name = name;
        components = new ArrayList<>();
        transform = new Transform();
    }

    public GameObject(String name, Transform transform) {
        this.name = name;
        components = new ArrayList<>();
        this.transform = transform;
    }

    public Transform getTransform() {
        return transform;
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
                components.remove(c);
                return;
            }
        }
    }

    public void addComponent(Component c) {
        components.add(c);
        c.setGameObject(this);
    }

    public void update(float dt) {
        components.forEach(component -> component.update(dt));
    }

    public void start() {
        components.forEach(Component::start);
    }
}
