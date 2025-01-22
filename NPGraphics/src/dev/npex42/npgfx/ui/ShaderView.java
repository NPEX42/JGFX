package dev.npex42.npgfx.ui;

import dev.npex42.npgfx.Shader;
import dev.npex42.npgfx.UniformInfo;
import imgui.ImGui;

import java.util.HashMap;
import java.util.Map;

public class ShaderView implements UIPanel {
    private Shader shader;

    private Map<String, Object> values = new HashMap<>();

    @Override
    public void Update() {
        if (shader == null) return;
        for (Map.Entry<String, UniformInfo> infoKV : shader.Uniforms()) {
            UniformInfo info = infoKV.getValue();

        }
    }

    public void SetShader(Shader s) { shader = s; }
}
