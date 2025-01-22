package dev.npex42.npgfx;

import org.joml.Vector4f;

import java.awt.*;

public class Colour {
    private Color _color;

    public float RedF() { return _color.getRed() / 255.0f; }
    public float GreenF() { return _color.getGreen() / 255.0f; }
    public float BlueF() { return _color.getBlue() / 255.0f; }
    public float AlphaF() { return _color.getBlue() / 255.0f; }

    public void SetFromVec4(Vector4f v) {
        _color = new Color(v.x, v.y, v.z, v.w);
    }

    public Vector4f GetAsVec4() {
        return new Vector4f(RedF(), GreenF(), BlueF(), AlphaF());
    }

    public Color AsNativeColor() {
        return _color;
    }

    public Colour(Color c) { _color = c;}

    public Colour() {
        _color = Color.WHITE;
    }
}
