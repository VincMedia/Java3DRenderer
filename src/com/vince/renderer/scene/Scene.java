package com.vince.renderer.scene;

import java.util.*;

public class Scene {
    private List<GameObject> objects = new ArrayList<>();

    public GameObject createObject() {
        GameObject obj = new GameObject();
        objects.add(obj);
        return obj;
    }

    public void update(float dt) {
        for (GameObject o : objects) o.update(dt);
    }

    public void render() {
        for (GameObject o : objects) o.render();
    }
}
