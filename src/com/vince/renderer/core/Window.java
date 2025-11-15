package com.vince.renderer.core;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private long handle; // ID Key
    private int width, height; //Size
    private String title; //Title for Window

    public Window(int width, int height, String title) { // Constructor on size and title
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create() { // creating and initializing GLFW components
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);

        handle = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (handle == MemoryUtil.NULL)
            throw new RuntimeException("Failed to create GLFW window");

        glfwMakeContextCurrent(handle);
        glfwSwapInterval(1);
        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);
        glViewport(0, 0, width, height);
        glClearColor(0.5f, 0.05f, 0.07f, 1.0f); // Navy-ish color
    }

    public boolean shouldClose() { return glfwWindowShouldClose(handle); }
    public void swapBuffers() { glfwSwapBuffers(handle); }
    public void pollEvents() { glfwPollEvents(); }
    public void destroy() { glfwDestroyWindow(handle); glfwTerminate(); }
    public long getHandle() { return handle; }
}
