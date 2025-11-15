package com.vince.renderer.demo;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class MainDemo {
    private long window;

    //Main Method
    public static void main(String[] args) {
        new MainDemo().run();
    }

    //Simple Run
    public void run() {
    	//Display LWJGL Version in console
        System.out.println("Starting LWJGL " + Version.getVersion() + "...");
        init();
        loop();

        glfwDestroyWindow(window);
        glfwTerminate();
    }

    //GLFW Window init
    private void init() {
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(1280, 720, "3D Renderer", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create GLFW window"); //Display Error Message

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); // vsync
        glfwShowWindow(window); //Show Window

        GL.createCapabilities();
    }

    //Main Draw Loop
    private void loop() {
        glClearColor(0.05f, 0.05f, 0.07f, 1.0f); //Setting Background Color

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //Clear Color and Depth buffer
            glfwSwapBuffers(window); //Swap Color and Depth Buffers
            glfwPollEvents(); //Check for GLFW Events: Keyboard, Mouse, etc.
        }
    }
}
