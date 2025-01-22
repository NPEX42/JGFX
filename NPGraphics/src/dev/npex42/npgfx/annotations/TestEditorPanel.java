package dev.npex42.npgfx.annotations;


import dev.npex42.npgfx.Colour;
import dev.npex42.npgfx.Texture;

import java.awt.*;

public class TestEditorPanel {
    public @FloatRange(Max = 8.0f) float foo;
    public @ColorEdit3 Colour bar = new Colour(Color.WHITE);
    public @TextureSelector Texture baz;
}
