package dev.leap.frog.Module.Movement;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow", "Stops you from slowing down", Type.MOVEMENT);
    }

    Settings gui = create("GUI", "GUI", true);
    Settings items = create("Items", "Items", false);

    @Override
    public void onUpdate() {
        if(items.getValue(true)) {
            if (mc.player.isHandActive() && !mc.player.isRiding()) {
                mc.player.movementInput.moveStrafe *= 5;
                mc.player.movementInput.moveForward *= 5;
            }
        }

        if(gui.getValue(true)) {

        }
    }

}
