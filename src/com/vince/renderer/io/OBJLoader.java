package com.vince.renderer.io;

import com.vince.renderer.gl.Mesh;
import com.vince.renderer.gl.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class OBJLoader { // Simple OBJ loader

    private static class Vec3 { float x,y,z; Vec3(float x,float y,float z){this.x=x;this.y=y;this.z=z;} }
    private static class Vec2 { float x,y;   Vec2(float x,float y){this.x=x;this.y=y;} }

    public static Model load(String path) {
        List<Vec3> positions = new ArrayList<>();
        List<Vec2> uvs       = new ArrayList<>();
        List<Vec3> normals   = new ArrayList<>();

        // mapping "vIndex/vtIndex/vnIndex" -> new unified vertex index
        Map<String, Integer> vertexMap = new HashMap<>();
        List<Float> interleaved = new ArrayList<>();
        List<Integer> indices   = new ArrayList<>();

        try {
            for (String line : Files.readAllLines(Path.of(path))) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                if (line.startsWith("v ")) {
                    String[] p = line.split("\\s+");
                    positions.add(new Vec3(
                            Float.parseFloat(p[1]),
                            Float.parseFloat(p[2]),
                            Float.parseFloat(p[3])));
                } else if (line.startsWith("vt ")) {
                    String[] p = line.split("\\s+");
                    // OBJ v is typically (u, v) with v downward; we’ll not flip here
                    uvs.add(new Vec2(
                            Float.parseFloat(p[1]),
                            Float.parseFloat(p[2])));
                } else if (line.startsWith("vn ")) {
                    String[] p = line.split("\\s+");
                    normals.add(new Vec3(
                            Float.parseFloat(p[1]),
                            Float.parseFloat(p[2]),
                            Float.parseFloat(p[3])));
                } else if (line.startsWith("f ")) {
                    // assume triangles; fan triangulation not handled here
                    String[] parts = line.split("\\s+");
                    if (parts.length != 4)
                        throw new RuntimeException("Non-triangle face encountered in: " + path);

                    for (int i = 1; i <= 3; i++) {
                        String key = parts[i]; // like "v/vt/vn" or "v//vn" or "v/vt"
                        Integer unifiedIndex = vertexMap.get(key);
                        if (unifiedIndex == null) {
                            String[] idx = key.split("/");
                            int vi = Integer.parseInt(idx[0]) - 1;
                            Integer ti = (idx.length > 1 && !idx[1].isEmpty()) ? Integer.parseInt(idx[1]) - 1 : null;
                            Integer ni = (idx.length > 2) ? Integer.parseInt(idx[2]) - 1 : null;

                            Vec3 pos = positions.get(vi);
                            Vec3 nor = (ni != null && ni >= 0 && ni < normals.size()) ? normals.get(ni) : new Vec3(0,0,1);
                            Vec2 uv  = (ti != null && ti >= 0 && ti < uvs.size()) ? uvs.get(ti) : new Vec2(0,0);

                            // interleaved: pos(3), normal(3), uv(2)
                            interleaved.add(pos.x); interleaved.add(pos.y); interleaved.add(pos.z);
                            interleaved.add(nor.x); interleaved.add(nor.y); interleaved.add(nor.z);
                            interleaved.add(uv.x);  interleaved.add(uv.y);

                            unifiedIndex = (interleaved.size()/8) - 1;
                            vertexMap.put(key, unifiedIndex);
                        }
                        indices.add(unifiedIndex);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read OBJ: " + path, e);
        }

        float[] vtx = new float[interleaved.size()];
        for (int i = 0; i < interleaved.size(); i++) vtx[i] = interleaved.get(i);
        int[] idxArr = indices.stream().mapToInt(Integer::intValue).toArray();

        Mesh mesh = new Mesh(vtx, idxArr);
        Model model = new Model();
        model.addMesh(mesh);
        return model;
    }
}
