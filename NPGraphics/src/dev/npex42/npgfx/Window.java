package dev.npex42.npgfx;

import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private long windowID = 0;

    public static Window Create(int width, int height, String title) {
        Window w = new Window();
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 5);
        w.windowID = glfwCreateWindow(width, height, title, 0, 0);
        w.MakeCurrent();
        return w;
    }

    public boolean ShouldClose() {
        return glfwWindowShouldClose(windowID);
    }

    public void Swap() {
        glfwSwapBuffers(windowID);
    }

    public void PollEvents() {
        glfwPollEvents();
    }

    public void WaitEvents() {
        glfwWaitEvents();
    }

    public void WaitEvents(double timeout) {
        glfwWaitEventsTimeout(timeout);
    }

    public void Destroy() {
        glfwDestroyWindow(windowID);
    }

    public void Close() {
        glfwSetWindowShouldClose(windowID, true);
    }

    public long ID() {return windowID;}

    public void MakeCurrent() {
        glfwMakeContextCurrent(windowID);
    }

    public int Width() {
        int[] w = new int[1], h = new int[1];
        glfwGetFramebufferSize(windowID, w, h);
        return w[0];
    }

    public int Height() {
        int[] w = new int[1], h = new int[1];
        glfwGetFramebufferSize(windowID, w, h);
        return h[0];
    }
}
