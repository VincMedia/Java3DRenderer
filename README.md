# Java3DRenderer
A lightweight Java 3D renderer built from scratch using LWJGL.

# Overview
This 3D Engine is a fully custom, real-time 3D renderer written in Java using LWJGL 3, designed to showcase low-level graphics programming skills, engine architecture, and mathematical understanding of 3D transformations and rendering pipelines.

This project demonstrates proficiency in:

- Manual matrix & camera math
- Graphics pipelines & shaders
- Working with OpenGL through LWJGL
- Building engine systems (scene, objects, components)
- Model loading (OBJ + glTF via Assimp)
- Lighting & texture mapping
- Real-time performance with large meshes (100k+ triangles)

Built entirely in Eclipse, with all dependencies manually linked—no Gradle or Maven—to show deep familiarity with the graphics stack

# Features
Core Engine Architecture
- GameObject system (Unity-style)
- Transform system (position, rotation, scale)
- Scene management
- Component-based architecture (Renderable, future components)

Camera & Movement
- Fully manual implementation
- Rotation via mouse look
- WASD + Space/Ctrl movement
- Proper view matrix (lookAt)

# Math Library

Custom-written classes:
- Vec3f
- Mat4f
- Perspective matrix
- Translation, rotation (X/Y/Z)
- Scaling
- Matrix multiplication
- LookAt matrix
(No external math libraries used.)

# Model Loading

OBJ Loader (custom)
- Supports positions, normals, UVs
- Builds indexed, interleaved vertex buffers

glTF 2.0 Loader (via LWJGL Assimp)
- Positions, normals, UVs
- Correct index buffer
- Smooth normals
- Proper winding

Capable of rendering high-poly models (e.g., 100,000+ triangles).

Lighting & Shading
- Directional light (sunlight)
- World-space normals
- Lambert diffuse shadin
- Per-pixel lighting
- Diffuse/albedo texture sampling
- Texture loader using stb_image

Graphics Pipeline
- Indexed VBO + VAO + EBO
- Interleaved attributes:
- Position (3)
- Normal (3)
- UV (2)
- Depth testing
- Backface culling (optional)
- Clean shader uniform management.

# Project Structure

  src/
    com/vince/renderer/
      core/       (Window, Time, Input)
      math/       (Vec3f, Mat4f, Camera)
      gl/         (Mesh, Model, Shader, Texture)
      io/         (OBJLoader, GLTFLoader)
      scene/      (GameObject, Component, Scene, Renderable)
      demo/       (MainDemo.java)

  assets/
    models/       (OBJ + glTF models)
    textures/     (Diffuse maps)
    shaders/      (Vertex + Fragment shaders)
  
  libs/           (LWJGL JARs + natives)

# How to Build & Run

Requirements
- Java 8+
- Eclipse IDE (Reccomended but not required)
- LWJGL 3 (manual JAR + native file linking already configured)

Running the demo
- Clone this repository
- Open in Eclipse
- Ensure working directory is set to project root
- Run:
    com.vince.renderer.demo.MainDemo

Controls:
- W/A/S/D – Move
- SPACE / CTRL – Up / down
- Mouse (LMB held) – Look around
- ESC – (Use OS close button for now)

# Why This Project Exists

This engine was built as part of a personal portfolio to demonstrate:
- Ability to implement a graphics renderer from the ground up
- Understanding of matrix math & camera systems
- Ability to build engine-style architecture in Java
- Skill with LWJGL's low-level APIs and Assimp model import
- Comfort with large codebases and system design

The project intentionally avoids high-level graphics libraries to highlight complete control over the rendering pipeline.

# Contact

If you'd like to collaborate, ask questions, or view more work:

- GitHub: github.com/VincMedia
- LinkedIn: [Vincent Kimmel](https://www.linkedin.com/in/vincent-kimmel-016824285/)
- Email: vincentkimmel1324@gmail.com

# Thank you for your time!
  
