#ifdef GL_ES
precision mediump float;
#endif

uniform vec2 resolution;
uniform float time;

float circle(in vec2 _st, in float _radius){
    vec2 dist = _st-vec2(0.5);
    return 1.-smoothstep(_radius-(_radius*0.01),
    _radius+(_radius*0.01),
    dot(dist,dist)*4.0);
}

float rand (vec2 st) {
    return fract(sin(dot(st.xy,
    vec2(12.98458,78.233)))*
    43758.5453123);
}

float noise(vec2 p){
    vec2 ip = floor(p);
    vec2 u = fract(p);
    u = u*u*(3.0-2.0*u);

    float res = mix(
    mix(rand(ip),rand(ip+vec2(1.0,0.0)),u.x),
    mix(rand(ip+vec2(0.0,1.0)),rand(ip+vec2(1.0,1.0)),u.x),u.y);
    return res*res;
}



float mapRange(float value, float min1, float max1, float min2, float max2) {
    return min2 + (value - min1) * (max2 - min2) / (max1 - min1);
}

float seaLimit = 0.2;
float seaRadius = 0.3;
float seaSpread = 0.1;
float sunRadius = 0.35;
float sunLightRadius = sunRadius * 0.85;
float sunLightSpread = 0.15;
float stripsSize = 13.0;


void main() {
    vec2 u_resolution = resolution;
    float duration = 1.6;
    float u_time = time;
    float ctime = clamp(log(u_time* 0.8) * 0.9, 0.0, duration);

    seaRadius += sin(ctime) * 0.1;
    sunRadius += sin(ctime) * 0.2;

    float sunStart = 0.9 - sin(ctime) * 0.9;

    vec2 uv = gl_FragCoord.xy/u_resolution.xy;

    vec2 circleUv = vec2(
    uv.x * u_resolution.x / u_resolution.y - ((u_resolution.x - u_resolution.y) / u_resolution.y * 0.5) / u_resolution.y,
    uv.y + sunStart
    );

    vec3 backgroundColor = vec3(0.172, 0.129, 0.254);
    vec3 color = backgroundColor;

    // stars
    float stars = 1. - clamp(rand(uv) * 800.0, 0.0, 1.0);
    stars = mix(0.0, stars, 1. - step(uv.y, seaLimit));
    color = mix(color, vec3(0.631, 0.603, 0.745), stars);

    // sun
    float sunLight = 1. - smoothstep(sunLightRadius - sunLightSpread, sunLightRadius + sunLightSpread, length(circleUv - vec2(0.5, 0.55 - sunStart)));
    color = mix(color, vec3(1.0, 0.145, 0.729), sunLight * 0.9 * (0.6 + noise(vec2(uv.x * 10. + u_time, uv.y * 60. + u_time)) * 0.4));

    vec3 sunColor = mix(vec3(0.996, 0.984, 0.227), vec3(1.0, 0.008, 0.729), 1. - uv.y);
    float sunCut = step(1.0 - uv.y - sunStart, 0.75);
    float sun = circle(circleUv, sunRadius) * sunCut;

    ///
    float sy = mapRange(pow(1.0 - uv.y + 0.0, 7.), 0.0,  seaLimit, 0.0, 1.0) ;
    float sunStrips = 1.0 - step(fract(sy * stripsSize), 0.5);
    sunStrips = mix(sunStrips, 0.0, step(1.0 - uv.y , seaLimit));

    float sunStripsFade = 1.0 - smoothstep(0.32 - seaSpread * 2., 0.32 + seaSpread * 2., length(circleUv - vec2(0.5, 0.45)));
    sunStrips *= sunStripsFade * (1.0 - (duration - ctime));
    // vec3 sunReflectColor = mix(vec3(1., 0.588, 0.357), vec3(1.0, 0.106, 0.729), 1. - mapRange(uv.y, seaLimit * 0.5, seaLimit, 0.0, 1.0));

    sunColor = mix(sunColor, backgroundColor, sunStrips);
    color = mix(color, sunColor, sun);
    ///
    float y = mapRange(fract(uv.y), 0.0, seaLimit, 0.0, 1.0) - 0.04 + u_time * 0.05;
    float seaStrips = step(fract(y * stripsSize), 0.5);
    seaStrips = mix(seaStrips, 1.0, step(1.0 - uv.y, 1.0 - seaLimit));

    float seaGradient = mapRange(uv.y, 0.0, seaLimit, 0.0, 1.0);
    float seaFade = smoothstep(seaRadius - seaSpread, seaRadius + seaSpread, length(uv - vec2(0.5, 0.35)));
    vec3 seaColor = mix(vec3(0.212, 0.263, 0.467), vec3(0.208, 0.376, 0.761), seaGradient);
    seaColor = mix(seaColor, backgroundColor, seaFade);
    float seaStripsFade = 1.0 - smoothstep(0.32 - seaSpread * 2., 0.32 + seaSpread * 2., length(circleUv - vec2(0.5, 0.45)));
    seaStrips *= seaStripsFade;
    vec3 seaReflectColor = mix(vec3(1., 0.588, 0.357), vec3(1.0, 0.106, 0.729), 1. - mapRange(uv.y, seaLimit * 0.5, seaLimit, 0.0, 1.0));
    seaColor = mix(seaColor, seaReflectColor, seaStrips);
    color = mix(color, seaColor, step(uv.y, seaLimit));

    float noise = rand(uv * 200.);
    color = mix(color, vec3(noise), 0.05);

    gl_FragColor = vec4(color, 2.0);
}