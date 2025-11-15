package com.vince.renderer.scene;

import com.vince.renderer.gl.*;
import com.vince.renderer.math.*;

public class Renderable extends Component {

    private Model model;
    private Shader shader;

    public Renderable(Model m, Shader s) {
        this.model = m;
        this.shader = s;
    }

    @Override
    public void render() {
        shader.bind();

        shader.setUniformMat4("uModel", gameObject.transform.getMatrix());

        model.draw();
        shader.unbind();
    }
}
