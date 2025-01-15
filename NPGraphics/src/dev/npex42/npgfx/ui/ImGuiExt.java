package dev.npex42.npgfx.ui;

import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.awt.*;

public class ImGuiExt {
    public static void TextFormatted(String format, Object... args) {
        ImGui.text(String.format(format, args));
    }

    public static boolean DragFloat2(String id, Vector2f v) {
        if (v == null) return false;
        float[] values = new float[] {v.x, v.y};
        boolean isDirty = ImGui.dragFloat2(id, values);
        v.x = values[0];
        v.y = values[1];
        return isDirty;
    }

    public static boolean DragFloat3(String id, Vector3f v) {
        if (v == null) return false;
        float[] values = new float[] {v.x, v.y, v.z};
        boolean isDirty = ImGui.dragFloat3(id, values);
        v.x = values[0];
        v.y = values[1];
        v.z = values[2];
        return isDirty;
    }

    public static boolean DragFloat4(String id, Vector4f v) {
        if (v == null) return false;
        float[] values = new float[] {v.x, v.y, v.z, v.w};
        boolean isDirty = ImGui.dragFloat4(id, values);
        v.x = values[0];
        v.y = values[1];
        v.z = values[2];
        v.w = values[3];
        return isDirty;
    }

    public static float[] ColorToFloats(Color c) {
        float[] colour = new float[3];
        colour[0] = c.getRed() / 255.0f;
        colour[1] = c.getGreen() / 255.0f;
        colour[2] = c.getBlue() / 255.0f;
        return colour;
    }

    public static Color FloatsToColor(float[] colour) {
        return new Color(colour[0], colour[1], colour[2]);
    }
}
