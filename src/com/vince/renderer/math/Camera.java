package com.vince.renderer.math;

import static org.lwjgl.glfw.GLFW.*;

public class Camera { //Hell
    public Vec3f position = new Vec3f(0, 0, 3);
    private float yaw = -90.0f;
    private float pitch = 0.0f;
    private float speed = 2.0f;
    private float sensitivity = 0.2f;

    private Vec3f front = new Vec3f(0, 0, -1);
    private Vec3f up = new Vec3f(0, 1, 0);
    private Vec3f right = new Vec3f(1, 0, 0);

    private double lastMouseX, lastMouseY;
    private boolean firstMouse = true;

    public void updateOrientation(double mouseX, double mouseY, boolean mouseHeld) {
        if (!mouseHeld) { firstMouse = true; return; }
        if (firstMouse) {
            lastMouseX = mouseX; lastMouseY = mouseY; firstMouse = false;
        }
        float xoffset = (float)(mouseX - lastMouseX);
        float yoffset = (float)(lastMouseY - mouseY);
        lastMouseX = mouseX; lastMouseY = mouseY;

        xoffset *= sensitivity;
        yoffset *= sensitivity;

        yaw -= xoffset;
        pitch += yoffset;

        if (pitch > 89.0f) pitch = 89.0f;
        if (pitch < -89.0f) pitch = -89.0f;

        updateVectors();
    }

    private void updateVectors() {
        float cy = (float)Math.cos(Math.toRadians(yaw));
        float sy = (float)Math.sin(Math.toRadians(yaw));
        float cp = (float)Math.cos(Math.toRadians(pitch));
        float sp = (float)Math.sin(Math.toRadians(pitch));

        front = new Vec3f(cy * cp, sp, sy * cp * -1).normalize();
        right = Vec3f.cross(front, new Vec3f(0,1,0)).normalize();
        up = Vec3f.cross(right, front).normalize();
    }

    public void move(long window, float delta) {
        Vec3f move = new Vec3f();
        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) move = move.add(front);
        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) move = move.sub(front);
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) move = move.sub(right);
        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) move = move.add(right);
        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) move = move.add(up);
        if (glfwGetKey(window, GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS) move = move.sub(up);

        if (move.x != 0 || move.y != 0 || move.z != 0)
            position = position.add(move.normalize().scale(speed * delta));
    }

    public Mat4f getViewMatrix() {
        Vec3f center = position.add(front);
        return lookAt(position, center, up);
    }

    private Mat4f lookAt(Vec3f eye, Vec3f center, Vec3f up) {
        Vec3f f = center.sub(eye).normalize();
        Vec3f s = Vec3f.cross(f, up).normalize();
        Vec3f u = Vec3f.cross(s, f);

        Mat4f r = new Mat4f();
        r.m[0]=s.x; r.m[4]=s.y; r.m[8]=s.z;  r.m[12]=-Vec3f.dot(s, eye);
        r.m[1]=u.x; r.m[5]=u.y; r.m[9]=u.z;  r.m[13]=-Vec3f.dot(u, eye);
        r.m[2]=-f.x; r.m[6]=-f.y; r.m[10]=-f.z; r.m[14]=Vec3f.dot(f, eye);
        r.m[3]=0; r.m[7]=0; r.m[11]=0; r.m[15]=1;
        return r;
    }
}
