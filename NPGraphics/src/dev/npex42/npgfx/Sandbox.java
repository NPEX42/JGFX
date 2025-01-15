package dev.npex42.npgfx;

import dev.npex42.npgfx.ui.ImGuiExt;
import dev.npex42.npgfx.ui.ShaderView;
import imgui.ImGui;
import imgui.flag.ImGuiColorEditFlags;
import imgui.flag.ImGuiDir;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Sandbox extends Application2D {

    private Shader debug;

    private ShaderView shaderView = new ShaderView();

    private Vector2f pos = new Vector2f(0, 0);

    private float tpf;

    private int
            columns = 255,
            rows = 255,
            rectWidth = 1,
            rectHeight = 1,
            rectPadding = 1;

    private Color rect1Color = Color.ORANGE, rect2Color = Color.WHITE;

    private Texture uv;

    public static void main(String[] args) {
        Sandbox s = new Sandbox();
        s.Start(720, 480, "Sandbox");
    }

    @Override
    public boolean OnUserCreate() {
        uv = Texture.Load("assets/textures/uv_grid_opengl.jpg");
        return true;
    }

    @Override
    public boolean OnUserUpdate(float deltaTime) {
        tpf = deltaTime;
        Renderer2D.ResetStats();
        return true;
    }

    @Override
    public void OnUserDraw() {
        Clear(0.0f, 0.2f, 0.3f);
        DrawSprite(uv, 0, 0);
    }

    @Override
    public void OnUserDestroy() {
    }

    @Override
    public void OnUserUI() {
        ImGui.begin("Renderer Stats");
        ImGuiExt.TextFormatted("Frametime: %d ms", (int) (tpf * 1000));
        ImGuiExt.TextFormatted("Batches: %d", Renderer2D.BatchCount());
        ImGuiExt.TextFormatted("Vertices Rendered: %d", Renderer2D.VerticesDrawn());
        ImGuiExt.DragFloat2("Position", pos);

        ImGui.separator();
        float[] _rect1Color = ImGuiExt.ColorToFloats(rect1Color);
        float[] _rect2Color = ImGuiExt.ColorToFloats(rect2Color);

        ImGui.colorEdit3("Rect1 Color", _rect1Color);
        ImGui.colorEdit3("Rect2 Color", _rect2Color);

        rect1Color = ImGuiExt.FloatsToColor(_rect1Color);
        rect2Color = ImGuiExt.FloatsToColor(_rect2Color);
        ImGui.end();
    }
}
