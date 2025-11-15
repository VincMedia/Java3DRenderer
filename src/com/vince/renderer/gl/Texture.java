package com.vince.renderer.gl;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Texture {
    private int id;

    public Texture(String path) { // Texture based on image path
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        // Texture filtering/wrapping
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        ByteBuffer image;
        int width, height;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            STBImage.stbi_set_flip_vertically_on_load(true);
            image = STBImage.stbi_load(path, w, h, comp, 4);
            if (image == null) throw new RuntimeException("Failed to load texture: " + path);

            width = w.get();
            height = h.get();
        }

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0,
                     GL_RGBA, GL_UNSIGNED_BYTE, image);

        glGenerateMipmap(GL_TEXTURE_2D);
        STBImage.stbi_image_free(image);
    }

    public void bind(int unit) {
        glActiveTexture(GL_TEXTURE0 + unit);
        glBindTexture(GL_TEXTURE_2D, id);
    }
}
