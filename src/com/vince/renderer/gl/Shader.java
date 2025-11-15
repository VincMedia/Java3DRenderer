package com.vince.renderer.gl;

import static org.lwjgl.opengl.GL20.*;

import java.nio.file.*;
import static org.lwjgl.opengl.GL20.*;
import org.lwjgl.system.MemoryStack;
import java.nio.FloatBuffer;
import com.vince.renderer.math.Mat4f;
import com.vince.renderer.math.Vec3f;

public class Shader {
    private int programId;

    public Shader(String vertexPath, String fragmentPath) { //Constructor and GLFW Shader init
        int vertId = loadShader(vertexPath, GL_VERTEX_SHADER);
        int fragId = loadShader(fragmentPath, GL_FRAGMENT_SHADER);

        programId = glCreateProgram();
        glAttachShader(programId, vertId);
        glAttachShader(programId, fragId);
        glLinkProgram(programId);

        if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE)
            throw new RuntimeException("Program link failed: " + glGetProgramInfoLog(programId));
        //else System.out.println("Vertex at: "+vertexPath+",\n    and Fragment at: "+fragmentPath+",\n    have been successfully found");

        glDeleteShader(vertId);
        glDeleteShader(fragId);
    }

    private int loadShader(String path, int type) { // Loading specific shaders
        String source;
        try {
            source = Files.readString(Path.of(path));
        } catch (Exception e) {
            throw new RuntimeException("Could not read shader file: " + path);
        }

        int id = glCreateShader(type);
        glShaderSource(id, source);
        glCompileShader(id);
        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE)
            throw new RuntimeException("Shader compile failed (" + path + "): " + glGetShaderInfoLog(id));
        return id;
    }

    public void bind() { glUseProgram(programId); }
    public void unbind() { glUseProgram(0); }
    public void destroy() { glDeleteProgram(programId); }
    
    public void setUniformMat4(String name, Mat4f mat){ //Shader Uniform setting
        int loc = glGetUniformLocation(programId, name);
        if (loc != -1){
            try (MemoryStack stack = MemoryStack.stackPush()){
                FloatBuffer fb = stack.mallocFloat(16);
                fb.put(mat.m).flip();
                glUniformMatrix4fv(loc, false, fb);
            }
        }
    }
    
    public void setUniform1i(String name, int value){ //Shader Uniform setting
        int loc = glGetUniformLocation(programId, name);
        if (loc != -1) glUniform1i(loc, value);
    }

    public void setUniform1f(String name, float value){ //Shader Uniform setting
        int loc = glGetUniformLocation(programId, name);
        if (loc != -1) glUniform1f(loc, value);
    }

    public void setUniform3f(String name, Vec3f v){ //Shader Uniform setting
        int loc = glGetUniformLocation(programId, name);
        if (loc != -1) glUniform3f(loc, v.x, v.y, v.z);
    }

}
