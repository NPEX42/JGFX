package dev.npex42.npgfx.ui;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImString;

public class TextEditor {
    ImString buffer = new ImString();

    public void SetContents(String value) {
        buffer.set(value, true);
    }

    public void Render(String title) {
        if (ImGui.begin(title)) {
            ImVec2 availRegion = ImGui.getContentRegionAvail();
            ImGui.inputTextMultiline("##ShaderCode", buffer, availRegion.x, availRegion.y, ImGuiInputTextFlags.CallbackEdit | ImGuiInputTextFlags.CallbackResize);

        }
        ImGui.end();
    }
}
