package dev.leap.frog.Event.Render;

import dev.leap.frog.Event.LeapFrogEvent;

public class EventRenderHurtCam extends LeapFrogEvent {

    public float Ticks;

    public EventRenderHurtCam(float p_Ticks) {
        super();
        Ticks = p_Ticks;
    }

}
