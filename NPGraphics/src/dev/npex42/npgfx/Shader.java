package dev.npex42.npgfx;

import dev.npex42.npcore.ConsoleLogger;
import dev.npex42.npcore.IO;
import dev.npex42.npcore.Logger;
import org.joml.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.lwjgl.opengl.GL46.*;
public class Shader {
    private static final Logger L = new ConsoleLogger();
    private int programID;

    private Map<String, UniformInfo> uniforms = new HashMap<>();

    public Shader(int programID) {
        this.programID = programID;
    }

    public static Shader Load(String filepath) {
        String vtxSource = IO.ReadText(filepath+".vert");
        String frgSource = IO.ReadText(filepath+".frag");

        int programID, vtxID, frgID;
        vtxID = glCreateShader(GL_VERTEX_SHADER);
        frgID = glCreateShader(GL_FRAGMENT_SHADER);
        programID = glCreateProgram();
        L.Info("Created Shader Program...");


        glShaderSource(vtxID, vtxSource);
        glShaderSource(frgID, frgSource);

        glCompileShader(vtxID);
        if (glGetShaderi(vtxID, GL_COMPILE_STATUS) == GL_FALSE) {
            L.Error("Failed To Compile Vertex Shader: \n\t%s", glGetShaderInfoLog(vtxID));
        }

        glCompileShader(frgID);
        if (glGetShaderi(frgID, GL_COMPILE_STATUS) == GL_FALSE) {
            L.Error("Failed To Compile Fragment Shader: \n\t%s", glGetShaderInfoLog(frgID));
        }

        glAttachShader(programID, vtxID);
        glAttachShader(programID, frgID);

        glLinkProgram(programID);
        if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
            L.Error("Failed To Link Shader Program \n\t%s", glGetProgramInfoLog(programID));
        }

        glDeleteShader(vtxID);
        glDeleteShader(frgID);



        Shader s = new Shader(programID);
        s.UpdateUniforms();
        return s;
    }

    public void Use() {
        glUseProgram(programID);
    }

    public void Destroy() {
        glDeleteProgram(programID);
        programID = 0;
    }

    public UniformInfo GetUniform(String name) {
        if (uniforms.containsKey(name)) {
            return uniforms.get(name);
        } else {
            return new UniformInfo(name, UniformInfo.UniformType.NONE, -1);
        }
    }

    private void UpdateUniforms() {
        Use();

        int uniform_count = glGetProgrami(programID, GL_ACTIVE_UNIFORMS);
        L.Info("Found %d Uniforms Active", uniform_count);
        IntBuffer type = MemoryUtil.memAllocInt(1);
        IntBuffer size = MemoryUtil.memAllocInt(1);
        for (int i = 0; i < uniform_count; i++) {
            String name = glGetActiveUniform(programID, i, size, type);

            L.Info("Uniform #%d: %s - %s", i, name, UniformInfo.UniformType.FromGL(type.get(0)));

            uniforms.put(name, new UniformInfo(name, UniformInfo.UniformType.FromGL(type.get(0)), i));


        }
        MemoryUtil.memFree(type);
        MemoryUtil.memFree(size);
    }


    public void SetVec4(String name, Vector4f v) {
        glUniform4f(GetUniform(name).location(), v.x, v.y, v.z, v.w);
    }

    public void SetVec2(String name, Vector2f v) {
        glUniform2f(GetUniform(name).location(), v.x, v.y);
    }

    public Set<Map.Entry<String, UniformInfo>> Uniforms() {
        return uniforms.entrySet();
    }

    public void SetSampler2DUnit(String name, int unit) {
        glUniform1i(GetUniform(name).location(), unit);
    }


}
