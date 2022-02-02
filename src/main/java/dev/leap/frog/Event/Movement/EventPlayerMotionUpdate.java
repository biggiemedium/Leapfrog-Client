package dev.leap.frog.Event.Movement;

import dev.leap.frog.Event.LeapFrogEvent;

public class EventPlayerMotionUpdate extends LeapFrogEvent {

    private int stage;

    public EventPlayerMotionUpdate(int stage) {
        this.stage = stage;
    }

    public int getStage() {
        return this.stage;
    }
}
