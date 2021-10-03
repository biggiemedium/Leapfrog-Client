package dev.leap.frog.Event.World;

import dev.leap.frog.Event.LeapFrogEvent;

public class EventSetDayTime extends LeapFrogEvent {

    long time;

    public EventSetDayTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
