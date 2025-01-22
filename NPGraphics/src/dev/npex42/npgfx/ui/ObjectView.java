package dev.npex42.npgfx.ui;

import dev.npex42.npgfx.Colour;
import dev.npex42.npgfx.annotations.ColorEdit3;
import dev.npex42.npgfx.annotations.FloatRange;
import imgui.ImGui;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ObjectView implements UIPanel {
    private Class<?> clazz;
    private Object instance;
    private Field[] fields;

    public ObjectView(Object instance, Class<?> clazz) {
        this.instance = instance;
        this.clazz = clazz;
        this.fields = clazz.getFields();
    }

    @Override
    public void Update() {
        try {
            for (Field f : fields) {
                for (Annotation a : f.getAnnotations()) {
                    switch (a) {
                        case FloatRange fr -> {
                            float[] value = new float[]{f.getFloat(instance)};
                            ImGui.sliderFloat(f.getName(), value, fr.Min(), fr.Max());
                            f.setFloat(instance, value[0]);
                        }

                        case ColorEdit3 ce3 -> {
                            Colour c = (Colour) f.get(instance);
                            ImGuiExt.ColorEdit3(f.getName(), c);
                            f.set(instance, c);
                        }

                        default -> {
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }
}
