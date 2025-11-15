package com.vince.renderer.scene;

import com.vince.renderer.math.Vec3f;

public class DirectionalLight {
    public Vec3f direction;
    public Vec3f color;
    public float intensity;

    public DirectionalLight(Vec3f direction, Vec3f color, float intensity) {
        this.direction = direction.normalize();
        this.color = color;
        this.intensity = intensity;
    }
}
