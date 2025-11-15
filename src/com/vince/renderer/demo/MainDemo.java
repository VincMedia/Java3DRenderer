package com.vince.renderer.demo;

import com.vince.renderer.core.Window;
import com.vince.renderer.gl.*;
import com.vince.renderer.io.GLTFLoader;
import com.vince.renderer.math.*;
import com.vince.renderer.scene.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

public class MainDemo {

	//Main Method
    public static void main(String[] args) {
        new MainDemo().run();
    }

    
    //Run Method that Initilizes window and begins render loop
    void run() {
        // Create window
        Window window = new Window(1280, 720, "3D Engine Demo");
        window.create();

        // Enable depth test
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE); // enable face culling

        // Camera
        Camera cam = new Camera();

        // Scene
        Scene scene = new Scene();

        // Load model + shader + texture
        Model dragonModel = GLTFLoader.load("assets/models/dragon.obj");
        Shader litShader  = new Shader( //Lit Shader for Fancy
                "assets/shaders/lit.vert",
                "assets/shaders/lit.frag"
        );
        Texture albedo /*old variable name*/ = new Texture("assets/textures/dirt.png");

        // Prepare shader
        litShader.bind();
        litShader.setUniform1i("uAlbedo", 0);
        litShader.unbind();

        // Directional light (sun)
        DirectionalLight sun = new DirectionalLight(
                new Vec3f(-1, -1, -1),
                new Vec3f(1.0f, 0.95f, 0.9f),
                1.5f
        );

        // Add dragon as an object
        GameObject dragon = scene.createObject();
        dragon.transform.position = new Vec3f(0, -1, -3);
        dragon.transform.scale    = new Vec3f(0.5f, 0.5f, 0.5f);
        dragon.addComponent(new Renderable(dragonModel, litShader));

        // Timing
        double lastTime = glfwGetTime();

        // Lock mouse
        glfwSetInputMode(window.getHandle(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        // Main Loop
        while (!window.shouldClose()) {

            // Time Calculations: dt
            double now = glfwGetTime();
            float dt = (float)(now - lastTime);
            lastTime = now;

            // Inputs
            double[] mx = new double[1], my = new double[1];
            glfwGetCursorPos(window.getHandle(), mx, my);
            boolean mouseHeld = glfwGetMouseButton(window.getHandle(),
                                GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS;

            cam.updateOrientation(mx[0], my[0], mouseHeld);
            cam.move(window.getHandle(), dt);

            // Clear Screen
            glViewport(0, 0, 1280, 720);
            glClearColor(0.05f, 0.05f, 0.10f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Global Matrices
            Mat4f proj = Mat4f.perspective(
                    (float)Math.toRadians(70),
                    1280f / 720f,
                    0.1f,
                    100f
            );
            Mat4f view = cam.getViewMatrix();

            // Bind lighting and Camera Uniforms
            litShader.bind();
            litShader.setUniformMat4("uView", view);
            litShader.setUniformMat4("uProjection", proj);
            litShader.setUniform3f("uLightDir", sun.direction);
            litShader.setUniform3f("uLightColor", sun.color);
            litShader.setUniform1f("uLightIntensity", sun.intensity);
            litShader.setUniform3f("uCameraPos", cam.position);
            litShader.unbind();

            // Bind Textuer
            albedo.bind(0);

            // Update Objects
            scene.update(dt);

            // Render all objects
            scene.render();

            // Swap image/depth buffers, and poll GLFW events
            window.swapBuffers();
            window.pollEvents();
        }

        // On close
        albedo = null;
        dragonModel.destroy();
        window.destroy();
    }
}
