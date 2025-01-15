package dev.npex42.npgfx;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {
    private static Window w;
    public static void SetActiveWindow(Window w) {
        Keyboard.w = w;
    }
    public static boolean IsKeyDown(int keycode) {
        return glfwGetKey(w.ID(), keycode) == GLFW_KEY_DOWN;
    }
}
