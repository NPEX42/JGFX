package dev.npex42.npgfx;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.awt.*;

import static org.lwjgl.opengl.GL46.*;

public final class Renderer2D {

    public static final int
            MAX_QUADS = 2048,
            MAX_VERTS = MAX_QUADS * 6;
    private static int posVBO, colorVBO, uvVBO, vao, vertexCount = 0, colourCount = 0;

    private static float[] colours = new float[MAX_VERTS * 3];

    private static float[] activeColour = new float[3];

    private static float[] positions = new float[MAX_VERTS * 2];
    private static float[] uvs = new float[MAX_VERTS * 2];
    private Renderer2D() {}
    private static float width, height;

    private static Shader activeShader;
    private static Texture activeTexture, WHITE;

    private static Matrix4f
            modelMat = new Matrix4f(),
            projectionMat = new Matrix4f();

    private static int batchCount, verticesDrawn;


    public static void Init() {
        vao = glGenVertexArrays();
        posVBO = glGenBuffers();
        colorVBO = glGenBuffers();
        uvVBO = glGenBuffers();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, posVBO);
        glBufferData(GL_ARRAY_BUFFER, positions, GL_STREAM_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, colorVBO);
        glBufferData(GL_ARRAY_BUFFER, colours, GL_STREAM_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, uvVBO);
        glBufferData(GL_ARRAY_BUFFER, uvs, GL_STREAM_DRAW);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);


        activeShader = Shader.Load("assets/renderer2d");


    }

    public static void PushVertex(float x, float y, float u, float v) {

        if (vertexCount >= MAX_VERTS) {
            Flush();
        }

        Vector4f vec = new Vector4f(x, y, 0, 1);
        vec.mul(modelMat);
        vec.mul(projectionMat);

        positions[vertexCount * 2 + 0] = vec.x;
        positions[vertexCount * 2 + 1] = vec.y;

        uvs[vertexCount * 2 + 0] = u;
        uvs[vertexCount * 2 + 1] = v;

        vertexCount += 1;
    }

    public static void PushVertex(Vector2f pos, Vector2f uv) {
        PushVertex(pos.x, pos.y, uv.x, uv.y);
    }

    public static void PushQuad(Vector2f pos, Vector2f size) {
        PushQuad(pos.x, pos.y, size.x, size.y);
    }

    public static void PushQuad(float x, float y, float w, float h) {

        if (ShouldCull(x, y, w, h)) { return; }

        PushVertex(x - w / 2.0f, y + h / 2, 0, 1); //TL
        PushVertex(x + w / 2.0f, y + h / 2, 1, 1); //TR
        PushVertex(x - w / 2.0f, y - h / 2, 0, 0); //BL

        PushVertex(x + w / 2.0f, y + h / 2, 1, 1); //TR
        PushVertex(x - w / 2.0f, y - h / 2, 0, 0); //BL
        PushVertex(x + w / 2.0f, y - h / 2, 1, 0); //BR

        for (int i = 0; i < 6; i++) {
            PushColour();
        }
    }

    private static void PushColour() {
        if (colourCount >= MAX_VERTS) {
            Flush();
        }
        for (int i = 0; i < 3; i++) {
            colours[colourCount * 3 + i] = activeColour[i];
        }

        colourCount += 1;
    }

    public static void Flush() {
        if (vertexCount == 0) return;

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, posVBO);
        glBufferData(GL_ARRAY_BUFFER, positions, GL_STREAM_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, uvVBO);
        glBufferData(GL_ARRAY_BUFFER, uvs, GL_STREAM_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, colorVBO);
        glBufferData(GL_ARRAY_BUFFER, colours, GL_STREAM_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        activeShader.Use();
        if (activeTexture == null) {
            activeTexture = Texture.WHITE;
        }

        activeTexture.SetUnit(0);
        activeTexture.Bind();

        activeShader.SetSampler2DUnit("uAlbedo", 0);

        glDrawArrays(GL_TRIANGLES, 0, vertexCount);
        glBindVertexArray(0);
        verticesDrawn += vertexCount;
        batchCount += 1;
        vertexCount = 0;
        colourCount = 0;
    }

    public static void SetTint(Color c) {
        activeColour[0] = c.getRed() / 255.0f;
        activeColour[1] = c.getGreen() / 255.0f;
        activeColour[2] = c.getBlue() / 255.0f;
    }

    public static void SetTexture(Texture tex) {
        activeTexture = tex;
    }

    public static void SetGlobalTranslation(Vector2f v) {
        Flush();
        activeShader.SetVec2("uTranslation", v);
    }

    public static void SetLocalTranslation(Vector2f v) {
        Vector3f v3 = new Vector3f(v, 1);
        modelMat = modelMat.identity().translation(v3);
    }

    public static void SetLocalTranslation(float x, float y) {
        SetGlobalTranslation(new Vector2f(x, y));
    }

    public static void SetCameraViewport(float w, float h) {
        projectionMat = projectionMat.identity().ortho(0, w, h, 0, -1, 1);
        glViewport(0, 0, (int) w, (int) h);
        width = w;
        height = h;
    }

    public static void Clear(float r, float g, float b) {
        glClearColor(r, g, b, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    private static boolean ShouldCull(float x, float y, float boundWidth, float boundHeight) {
        return
                (x + boundWidth / 2 < 0 || x - boundWidth / 2 > width)
                || (y + boundHeight / 2 < 0 || y - boundHeight / 2 > height);
    }

    public static int BatchCount() { return batchCount; }
    public static int VerticesDrawn() { return verticesDrawn; }

    public static int QuadsDrawn() { return verticesDrawn / 12; }

    public static void ResetStats() {
        batchCount = 0;
        verticesDrawn = 0;
    }
}
