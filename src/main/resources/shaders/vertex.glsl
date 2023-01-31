#version 400 core

layout (location = 0) in vec3 positions;
layout (location = 1) in vec4 colors;
layout (location = 2) in vec2 textureCoords;
layout (location = 3) in vec3 normals;

out vec4 colorsOut;
out vec3 normalsOut;
out vec3 worldSpacePos;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(positions, 1.0);
    worldSpacePos = (transformationMatrix * vec4(positions, 1)).xyz;
    colorsOut = colors;
    normalsOut = normals;
}