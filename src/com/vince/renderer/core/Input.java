package com.vince.renderer.core;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private static long window; //Focused Window

    public static void setWindow(long handle) { window = handle; } //Setting window with ID key

    public static boolean isKeyDown(int key) { // Polling if key is down
        return glfwGetKey(window, key) == GLFW_PRESS;
    }

    public static boolean isMouseDown(int button) { //Polling if mouse is down
        return glfwGetMouseButton(window, button) == GLFW_PRESS;
    }

    public static double getMouseX() { //Polling Mouse x position
        double[] x = new double[1], y = new double[1];
        glfwGetCursorPos(window, x, y);
        return x[0];
    }

    public static double getMouseY() { //Polling Mouse y position
        double[] x = new double[1], y = new double[1];
        glfwGetCursorPos(window, x, y);
        return y[0];
    }
}
