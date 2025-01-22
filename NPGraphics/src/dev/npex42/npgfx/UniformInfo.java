package dev.npex42.npgfx;
import static org.lwjgl.opengl.GL46.*;
public record UniformInfo(String name, UniformType type, int location) {


    public static enum UniformType {
        NONE, VEC2, VEC3, VEC4, FLOAT, BYTE, INT,
        SAMPLER_2D, SAMPLER_3D;

        public int ToGL() {
            return switch (this) {
                case NONE   -> 0;
                case VEC2   -> GL_FLOAT_VEC2;
                case VEC3   -> GL_FLOAT_VEC3;
                case VEC4   -> GL_FLOAT_VEC4;
                case FLOAT  -> GL_FLOAT;
                case BYTE   -> GL_BYTE;
                case INT    -> GL_INT;
                case SAMPLER_2D -> GL_SAMPLER_2D;
                case SAMPLER_3D -> GL_SAMPLER_3D;
            };
        }

        public static UniformType FromGL(int type) {
            return switch (type) {
                case GL_FLOAT       -> FLOAT;
                case GL_FLOAT_VEC2  -> VEC2;
                case GL_FLOAT_VEC3  -> VEC3;
                case GL_FLOAT_VEC4  -> VEC4;
                case GL_INT         -> INT;
                case GL_BYTE        -> BYTE;
                case GL_SAMPLER_2D  -> SAMPLER_2D;
                case GL_SAMPLER_3D  -> SAMPLER_3D;
                default             -> NONE;
            };
        }
        
    }
}
