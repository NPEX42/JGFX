package dev.npex42.npgfx;

import dev.npex42.npgfx.ui.UIPanel;
import imgui.ImGui;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Application2D {
    public abstract boolean OnUserCreate();
    public abstract boolean OnUserUpdate(float deltaTime);
    public abstract void    OnUserDraw();
    public abstract void    OnUserDestroy();

    public void OnUserUI() {}

    private Window window;
    private Color activeFill = Color.BLACK;
    private Map<String, UIPanel> panels = new HashMap<>();
    private Texture activeTexture;



    public void Start(int width, int height, String title) {
        window = Window.Create(width, height, title);
        GLFW.glfwSwapInterval(1);
        window.MakeCurrent();

        OGL.Initialize();
        Renderer2D.Init();
        Texture.Init();
        activeTexture = Texture.WHITE;
        Keyboard.SetActiveWindow(window);

        ImGui.createContext();
        ImGuiImplGlfw implGlfw = new ImGuiImplGlfw();
        implGlfw.init(window.ID(), true);
        ImGuiImplGl3 implGl3 = new ImGuiImplGl3();
        implGl3.init();

        if (!OnUserCreate()) {
            window.Destroy();
            return;
        }
        double start, end;
        start = GLFW.glfwGetTime();
        end = start;
        while(!window.ShouldClose()) {

            window.PollEvents();
            Renderer2D.SetCameraViewport(window.Width(), window.Height());
            if (!OnUserUpdate((float) (end - start))) {
                break;
            }
            start = GLFW.glfwGetTime();
            OnUserDraw();
            Renderer2D.Flush();
            implGlfw.newFrame();
            implGl3.newFrame();
            ImGui.newFrame();
            OnUserUI();
            for (String key : panels.keySet()) {
                panels.get(key).Update();
            }
            ImGui.endFrame();
            ImGui.render();
            implGl3.renderDrawData(ImGui.getDrawData());
            end = GLFW.glfwGetTime();

            window.Swap();

        }

        OnUserDestroy();
        window.Destroy();
    }

    protected void Fill(Color c) {
        if (!activeFill.equals(c)) {

            if (!activeTexture.equals(Texture.WHITE)) {
                Renderer2D.Flush();
            }

            Renderer2D.SetTint(c);
            Renderer2D.SetTexture(Texture.WHITE);
            Texture.WHITE.SetUnit(0);
            Texture.WHITE.Bind();
        }
    }

    protected void Fill(Texture tex) {
        if (!tex.equals(activeTexture)) {
            Renderer2D.Flush();
            Renderer2D.SetTint(Color.WHITE);
            Renderer2D.SetTexture(tex);
            tex.SetUnit(0);
            tex.Bind();
        }
    }

    protected void Clear(float r, float g, float b) {
        Renderer2D.Clear(r, g, b);
    }

    protected void DrawQuad(float x, float y, float w, float h) {
        Renderer2D.PushQuad(x, y, w, h);
    }

    protected void DrawSprite(Texture spr, float x, float y, float w, float h) {
        Fill(spr);
        DrawQuad(x, y, w, h);
    }

    protected void DrawSprite(Texture spr, float x, float y) {
        Fill(spr);
        DrawQuad(x, y, spr.Width(), spr.Height());
    }

    protected Shader LoadShader(String filepath) {
        return Shader.Load(filepath);
    }

    protected void AddUIPanel(String title, UIPanel panel) {
        panels.put(title, panel);
    }
}
