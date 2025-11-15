package com.vince.renderer.gl;

import java.util.*;

public class Model { // Simple model class based on mesh
    private final List<Mesh> meshes = new ArrayList<>();
    public void addMesh(Mesh m){ meshes.add(m); }
    public void draw(){ for (Mesh m: meshes) m.draw(); }
    public void destroy(){ for (Mesh m: meshes) m.destroy(); meshes.clear(); }
}
