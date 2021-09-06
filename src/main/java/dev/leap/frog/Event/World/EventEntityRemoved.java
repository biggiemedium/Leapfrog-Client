package dev.leap.frog.Event.World;

import dev.leap.frog.Event.LeapFrogEvent;
import net.minecraft.entity.Entity;

public class EventEntityRemoved extends LeapFrogEvent {
    private Entity entity;

    public EventEntityRemoved(Entity entity) {
        this.entity = entity;
    }
}
