package dev.leap.frog.Event.Movement;

import dev.leap.frog.Event.LeapFrogEvent;

public class PlayerMotionUpdateEvent extends LeapFrogEvent {
    public int stage;

    public PlayerMotionUpdateEvent(int stage) {
        super();
        this.stage = stage;
    }
}
