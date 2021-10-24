package dev.leap.frog.Module.World;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.BaritoneHandler;

public class GOTO extends Module {

    public GOTO() {
        super("Go to", "Walks you anywhere you want to go using baritone", Type.WORLD);
    }

    double x;
    double y;
    double z;

    @Override
    public void onEnable() {

    }

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck()) return;
    }

    @Override
    public void onDisable() {
        if(BaritoneHandler.isActive() || BaritoneHandler.isPathFinding()) {
            BaritoneHandler.stopAll();
        }
    }
}
