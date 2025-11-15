package com.vince.renderer.scene;

public abstract class Component {
    protected GameObject gameObject;

    public void setGameObject(GameObject obj) { this.gameObject = obj; }

    public void start() {}
    public void update(float dt) {}
    public void render() {}
}
