package dev.leap.frog.Event.Movement;

import dev.leap.frog.Event.LeapFrogEvent;

public class EventPlayerMotionUpdate extends LeapFrogEvent {
    public int stage;

    public EventPlayerMotionUpdate(int stage) {
        super();
        this.stage = stage;
    }
}
