package dev.npex42.npgfx;


import dev.npex42.npcore.ConsoleLogger;
import dev.npex42.npcore.Logger;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.*;

public class Texture {

    private static Logger L = new ConsoleLogger();

    private int ID, width, height, format, int_format, unit;

    public static Texture WHITE, MISSING;

    public static void Init() {
        //                                                    AABBGGRR
        WHITE = new Texture(1, 1, new int[] {0xFFFFFFFF});
    }

    public void Bind() {
        glActiveTexture(GL_TEXTURE0 + unit);
        glBindTexture(GL_TEXTURE_2D, ID);
    }

    public void Unbind() {
        glActiveTexture(GL_TEXTURE0 + unit);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int ID() { return ID; }
    public int Width() { return width; }
    public int Height() { return height; }
    public int Format() { return format; }

    public Texture(int width, int height, int[] pixels) {
        ID = glGenTextures();
        this.width = width;
        this.height = height;
        this.int_format = GL_RGBA;
        this.format = GL_RGBA;

        Bind();
        glPixelStorei(GL_UNPACK_SWAP_BYTES, GL_TRUE);
        glTexImage2D(
                GL_TEXTURE_2D, 0,
                GL_RGBA,
                width, height,
                0,
                GL_RGBA,
                GL_UNSIGNED_BYTE,
                pixels
        );
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glGenerateMipmap(GL_TEXTURE_2D);
        Unbind();
    }

    public Texture(int width, int height, ByteBuffer pixels) {
        ID = glGenTextures();
        this.width = width;
        this.height = height;
        this.int_format = GL_RGBA8;
        this.format = GL_RGBA;

        Bind();
        glTexImage2D(
                GL_TEXTURE_2D, 0,
                int_format,
                width, height,
                0,
                format,
                GL_UNSIGNED_BYTE,
                pixels
        );
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glGenerateMipmap(GL_TEXTURE_2D);
        Unbind();
    }

    public static Texture Load(String filepath) {
        int[] w = new int[1], h = new int[1], depth = new int[1];
        ByteBuffer pixels = STBImage.stbi_load(filepath, w, h, depth, 4);
        if (pixels == null) {
            throw new RuntimeException("Failed To Load Texture '"+filepath+"'");
        } else {
            L.Info("Loaded Texture '%s' (%dx%d)", filepath, w[0], h[0]);
        }



        return new Texture(w[0], h[0], pixels);
    }

    public void SetUnit(int unit) {
        this.unit = unit;
        glBindTextureUnit(unit, ID);
    }
}
