package dev.npex42.npgfx.ui;

import dev.npex42.npgfx.Shader;
import dev.npex42.npgfx.UniformInfo;
import imgui.ImGui;

import java.util.Map;

public class ShaderView implements UIPanel {
    private Shader shader;
    @Override
    public void Update() {
        if (shader == null) return;
        ImGui.begin("Shader Uniforms");
        for (Map.Entry<String, UniformInfo> info : shader.Uniforms()) {
            ImGui.text(String.format("%s - %s", info.getValue().name(), info.getValue().type()));
        }
        ImGui.end();
    }

    public void SetShader(Shader s) { shader = s; }
}
