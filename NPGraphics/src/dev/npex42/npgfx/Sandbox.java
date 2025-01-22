package dev.npex42.npgfx;

import dev.npex42.npgfx.annotations.TestEditorPanel;
import dev.npex42.npgfx.ui.ImGuiExt;
import dev.npex42.npgfx.ui.ObjectView;
import dev.npex42.npgfx.ui.ShaderView;
import dev.npex42.npgfx.ui.TextEditor;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiModFlags;
import imgui.internal.flag.ImGuiTextFlags;
import imgui.type.ImString;
import org.joml.Vector2f;

public class Sandbox extends Application2D {

    private Shader debug;

    private ShaderView shaderView = new ShaderView();

    private Vector2f pos = new Vector2f(0, 0);

    private float tpf;

    private Framebuffer framebuffer;

    private int
            columns = 255,
            rows = 255,
            rectWidth = 1,
            rectHeight = 1,
            rectPadding = 1;

    ImString msg = new ImString("Yeet");

    private VectorList vl = new VectorList();

    private Colour testColor = new Colour();
    private int[] activeColorBuffer = new int[] { 0 };

    TestEditorPanel testEditorPanel = new TestEditorPanel();
    ObjectView objectView = new ObjectView(testEditorPanel, TestEditorPanel.class);


    TextEditor shaderEdit = new TextEditor();


    private Texture uv;

    public static void main(String[] args) {
        Sandbox s = new Sandbox();
        s.Start(720, 480, "Sandbox");
    }

    @Override
    public boolean OnUserCreate() {
        uv = Texture.Load("assets/textures/uv_grid_opengl.jpg");
        debug = Shader.Load("assets/renderer2d");
        shaderView.SetShader(debug);
        AddUIPanel("shader info", shaderView);
        AddUIPanel("ObjectView", objectView);

        framebuffer = new Framebuffer(2, 720, 480);

        vl.AddLine(0, 100, 100, 0);
        vl.AddLine(0, 100, 100, 0);

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
        framebuffer.Bind();
        Clear(0.1f, 0.2f, 0.3f);
        Fill(testEditorPanel.baz);
        DrawQuad(0, 0, 200, 200);
        Renderer2D.Flush();
        framebuffer.Unbind();
    }

    @Override
    public void OnUserDestroy() {
        ImGui.saveIniSettingsToDisk("imgui.ini");
    }

    @Override
    public void OnUserUI() {
        ImGui.dockSpaceOverViewport();
        ImGui.begin("Renderer Stats");
        ImGuiExt.TextFormatted("Frametime: %d ms", (int) (tpf * 1000));
        ImGuiExt.TextFormatted("Batches: %d", Renderer2D.BatchCount());
        ImGuiExt.TextFormatted("Vertices Rendered: %d", Renderer2D.VerticesDrawn());
        ImGuiExt.DragFloat2("Position", pos);

        ImGui.separator();

        ImGuiExt.ColorEdit3("testColor", testColor);

        ImGui.sliderInt("Active Color Buffer", activeColorBuffer, 0, framebuffer.ColorAttachmentCount() - 1);

        ImGui.end();

        ImGui.begin("viewport");
        ImVec2 winOrigin = ImGui.getCursorScreenPos();
        ImGui.image(framebuffer.ColorAttachmentID(activeColorBuffer[0]), new ImVec2(720, 480), new ImVec2(0, 1), new ImVec2(1, 0));
        ImVec2 CursorPos = ImGui.getMousePos();

        ImGuiExt.TextFormatted("Cursor Screen Pos: (%.2f,%.2f)", CursorPos.x, CursorPos.y);

        ImDrawList drawList = ImGui.getWindowDrawList();
        drawList.addRect(winOrigin.x + 0, winOrigin.y, winOrigin.x + 100, winOrigin.y + 100, 0xFF00FFFF);

        ImGui.inputTextMultiline("##msg", msg, ImGuiInputTextFlags.CallbackEdit | ImGuiInputTextFlags.CallbackResize);
        ImGui.end();

        ImGui.showDemoWindow();

        shaderEdit.Render("Shader Editor");
    }
}
