package dev.leap.frog.Event.Movement;

import dev.leap.frog.Event.LeapFrogEvent;

public class EventPlayerJump extends LeapFrogEvent {

    public double motionX;
    public double motionY;

    public EventPlayerJump(double motion_x, double motion_y) {
        super();

        this.motionX = motion_x;
        this.motionY = motion_y;
    }

}
