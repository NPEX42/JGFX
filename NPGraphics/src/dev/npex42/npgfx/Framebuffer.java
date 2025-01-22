package dev.npex42.npgfx;

import static org.lwjgl.opengl.GL46.*;

public class Framebuffer {
    public static final int MAX_COLOR_ATTACHMENTS = 8;

    private int fbo, width, height;
    private int[] colorBufferIDs;

    public Framebuffer(int colorAttachmentCount, int width, int height) {
        this.width = width;
        this.height = height;
        colorBufferIDs = new int[colorAttachmentCount];
        fbo = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);
        for (int i = 0; i < colorAttachmentCount; i++) {
            colorBufferIDs[i] = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, colorBufferIDs[i]);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + i, GL_TEXTURE_2D, colorBufferIDs[i], 0);
        }

        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            System.out.print("Framebuffer Creation Failed: ");
            switch (glCheckFramebufferStatus(GL_FRAMEBUFFER)) {
                case GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT -> System.out.println("Incomplete Attachment(s)");
                case GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT -> System.out.println("Missing Attachment(s)");
                case GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE -> System.out.println("Incomplete Multi-Sample");
                case GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER -> System.out.println("Incomplete Draw-Buffer");
                case GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER -> System.out.println("Incomplete Read-Buffer");
            }
        }

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public int ColorAttachmentID(int index) {
        return colorBufferIDs[index];
    }

    public int ColorAttachmentCount() { return colorBufferIDs.length; }

    public void Bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);
        glViewport(0, 0, width, height);
        Renderer2D.SetCameraViewport(width, height);
    }

    public void Unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }
}
