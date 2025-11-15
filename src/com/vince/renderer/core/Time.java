package com.vince.renderer.core;

import static org.lwjgl.glfw.GLFW.*;

public class Time {
    private static double lastTime = glfwGetTime();
    private static double deltaTime;

    public static void update() { // Update time calculations
        double now = glfwGetTime();
        deltaTime = now - lastTime;
        lastTime = now;
    }

    public static float delta() { return (float) deltaTime; }
    public static float fps() { return (float) (1.0 / deltaTime); }
}
