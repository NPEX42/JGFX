package dev.npex42.npgfx;
import static org.lwjgl.opengl.GL46.*;
public record UniformInfo(String name, UniformType type, int location) {


    public static enum UniformType {
        NONE, VEC2, VEC3, VEC4, FLOAT, BYTE, INT;

        public int ToGL() {
            return switch (this) {
                case NONE   -> 0;
                case VEC2   -> GL_FLOAT_VEC2;
                case VEC3   -> GL_FLOAT_VEC3;
                case VEC4   -> GL_FLOAT_VEC4;
                case FLOAT  -> GL_FLOAT;
                case BYTE   -> GL_BYTE;
                case INT    -> GL_INT;
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
                default             -> NONE;
            };
        }
        
    }
}
