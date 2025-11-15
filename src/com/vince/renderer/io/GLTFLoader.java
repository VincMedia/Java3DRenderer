package com.vince.renderer.io;

import com.vince.renderer.gl.Mesh;
import com.vince.renderer.gl.Model;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.nio.IntBuffer;

import static org.lwjgl.assimp.Assimp.*;

public class GLTFLoader {

    public static Model load(String path) {
        // Generate normals; flip UV vertically for OpenGL convention
        int flags = aiProcess_Triangulate
                | aiProcess_JoinIdenticalVertices
                | aiProcess_GenSmoothNormals
                | aiProcess_FlipUVs; // flips V (y) only

        AIScene scene = aiImportFile(path, flags);
        if (scene == null) throw new RuntimeException("Assimp error: " + aiGetErrorString());

        Model model = new Model();

        PointerBuffer meshes = scene.mMeshes();
        int meshCount = scene.mNumMeshes();
        for (int i = 0; i < meshCount; i++) {
            AIMesh mesh = AIMesh.create(meshes.get(i));

            int vcount = mesh.mNumVertices();

            AIVector3D.Buffer aPos = mesh.mVertices();
            AIVector3D.Buffer aNor = mesh.mNormals(); // should exist because of aiProcess_GenSmoothNormals
            AIVector3D.Buffer aUV0 = mesh.mTextureCoords(0); // may be null

            float[] interleaved = new float[vcount * 8];
            for (int v = 0; v < vcount; v++) {
                // pos
                AIVector3D p = aPos.get(v);
                interleaved[v*8 + 0] = p.x();
                interleaved[v*8 + 1] = p.y();
                interleaved[v*8 + 2] = p.z();
                // normal
                if (aNor != null) {
                    AIVector3D n = aNor.get(v);
                    interleaved[v*8 + 3] = n.x();
                    interleaved[v*8 + 4] = n.y();
                    interleaved[v*8 + 5] = n.z();
                } else {
                    interleaved[v*8 + 3] = 0;
                    interleaved[v*8 + 4] = 0;
                    interleaved[v*8 + 5] = 1;
                }
                // uv
                if (aUV0 != null) {
                    AIVector3D t = aUV0.get(v);
                    interleaved[v*8 + 6] = t.x();
                    interleaved[v*8 + 7] = t.y();
                } else {
                    interleaved[v*8 + 6] = 0f;
                    interleaved[v*8 + 7] = 0f;
                }
            }

            // indices from faces
            int fcount = mesh.mNumFaces();
            AIFace.Buffer faces = mesh.mFaces();
            int[] indices = new int[fcount * 3];
            for (int f = 0; f < fcount; f++) {
                AIFace face = faces.get(f);
                IntBuffer ib = face.mIndices();
                indices[f*3 + 0] = ib.get(0);
                indices[f*3 + 1] = ib.get(1);
                indices[f*3 + 2] = ib.get(2);
            }

            model.addMesh(new Mesh(interleaved, indices));
        }

        aiReleaseImport(scene);
        return model;
    }
}
