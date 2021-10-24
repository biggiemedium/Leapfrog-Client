package dev.leap.frog.Event.Movement;

import dev.leap.frog.Event.LeapFrogEvent;
import net.minecraft.entity.player.EntityPlayer;

public class EventPlayerStoppedUsingItem extends LeapFrogEvent {

    private EntityPlayer player;

    public EventPlayerStoppedUsingItem(EntityPlayer player) {
        this.player = player;
    }

    public EntityPlayer getPlayer() {
        return player;
    }
}
