package dev.leap.frog.Module.Movement;

import dev.leap.frog.Module.Module;

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow", "Stops you from slowing down", Type.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if(!mc.player.isRiding() && mc.player.handActive) {


        }
    }
}
