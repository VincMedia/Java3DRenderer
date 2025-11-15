package com.vince.renderer.scene;

import com.vince.renderer.math.*;

public class Transform {
    public Vec3f position = new Vec3f(0,0,0);
    public Vec3f rotation = new Vec3f(0,0,0); // pitch, yaw, roll in degrees
    public Vec3f scale    = new Vec3f(1,1,1);

    public Mat4f getMatrix() {
        Mat4f t = Mat4f.translation(position.x, position.y, position.z);
        Mat4f rx = Mat4f.rotationX((float)Math.toRadians(rotation.x));
        Mat4f ry = Mat4f.rotationY((float)Math.toRadians(rotation.y));
        Mat4f rz = Mat4f.rotationZ((float)Math.toRadians(rotation.z));
        Mat4f s  = Mat4f.scale(scale.x, scale.y, scale.z);

        return t.mul(ry).mul(rx).mul(rz).mul(s);
    }
}
