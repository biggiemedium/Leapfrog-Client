package dev.leap.frog.Event.Render;

import dev.leap.frog.Event.LeapFrogEvent;

public class EventSetupFog extends LeapFrogEvent {

    public int start;
    public float partialticks;

    public EventSetupFog(int coords, int partialticks) {
        this.start = coords;
        this.partialticks = partialticks;
    }

}
