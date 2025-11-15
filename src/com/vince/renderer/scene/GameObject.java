package com.vince.renderer.scene;

import java.util.*;

public class GameObject {
    public Transform transform = new Transform();
    private List<Component> components = new ArrayList<>();

    public <T extends Component> T addComponent(T component) {
        component.setGameObject(this);
        components.add(component);
        component.start();
        return component;
    }

    public void update(float dt) {
        for (Component c : components) c.update(dt);
    }

    public void render() {
        for (Component c : components) c.render();
    }
}
