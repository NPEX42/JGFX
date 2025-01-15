#version 330 core
in vec3 vtxColor;
in vec2 vtxUV;
out vec4 fragColor;
uniform vec4 uTint;

uniform sampler2D uAlbedo;

void main() {
    fragColor = vec4(vtxColor, 1) * texture(uAlbedo, vtxUV);
}