#version 330 core

in vec3 vWorldNormal;
in vec3 vWorldPos;
in vec2 vUV;

uniform sampler2D uAlbedo;
uniform vec3 uLightDir;  
uniform vec3 uLightColor;
uniform float uLightIntensity;

uniform vec3 uCameraPos;

out vec4 FragColor;

void main()
{
    // normalize needed after interpolation
    vec3 N = normalize(vWorldNormal);
    vec3 L = normalize(-uLightDir);  // sun direction is stored as "points toward surface"

    // diffuse
    float diff = max(dot(N, L), 0.0);

    vec3 albedo = texture(uAlbedo, vUV).rgb;

    vec3 color = albedo * diff * uLightColor * uLightIntensity;

    FragColor = vec4(color, 1.0);
}
