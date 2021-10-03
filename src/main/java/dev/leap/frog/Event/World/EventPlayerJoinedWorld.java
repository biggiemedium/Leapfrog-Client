package dev.leap.frog.Event.World;

import net.minecraft.entity.player.EntityPlayer;

public class EventPlayerJoinedWorld {

    private EntityPlayer player;

    public EventPlayerJoinedWorld(EntityPlayer player) {
        this.player = player;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

}
