package dev.npex42.npgfx;

import imgui.ImDrawList;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class VectorList {
    private List<Vector2f> points = new ArrayList<>();
    public void DrawImGui(float ox, float oy, ImDrawList dl) {
        for (int i = 0; i < points.size() - 1; i++) {
            Vector2f start, end;
            start = points.get(i);
            end = points.get(i + 1);
            dl.addLine(
                    ox + start.x, oy + start.y,
                    ox + end.x, oy + end.y,
                        0xFFFFFFFF, 1
            );
        }
    }

    public void AddLine(float p1x, float p1y, float p2x, float p2y) {
        points.add(new Vector2f(p1x, p1y));
        points.add(new Vector2f(p2x, p2y));
    }
}
