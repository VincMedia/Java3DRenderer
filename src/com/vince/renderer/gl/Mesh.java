package com.vince.renderer.gl;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private final int vao;
    private final int vbo;
    private final int ebo; // 0 if none
    private final int indexCount;
    private final int vertexCount;

    
    public Mesh(float[] interleaved, int[] indices) { // Constructor
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        ebo = (indices != null && indices.length > 0) ? glGenBuffers() : 0;

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, interleaved, GL_STATIC_DRAW);

        final int stride = 8 * Float.BYTES;
        // position
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0L);
        // normal
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, stride, 3L * Float.BYTES);
        // uv
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, stride, 6L * Float.BYTES);

        if (ebo != 0) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
            indexCount = indices.length;
            vertexCount = 0;
        } else {
            indexCount = 0;
            vertexCount = interleaved.length / 8;
        }

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void draw() { // Draw mesh to screen
        glBindVertexArray(vao);
        if (ebo != 0) {
            glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0L);
        } else {
            glDrawArrays(GL_TRIANGLES, 0, vertexCount);
        }
        glBindVertexArray(0);
    }

    public void destroy() { // Destory mesh
        if (ebo != 0) glDeleteBuffers(ebo);
        glDeleteBuffers(vbo);
        glDeleteVertexArrays(vao);
    }
}
