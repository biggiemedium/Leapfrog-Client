package dev.leap.frog.Module.Render;

import dev.leap.frog.Module.Module;

public class FullBright extends Module {

    public FullBright() {
        super("FullBright", "Shows full brightness", Type.RENDER);
    }

    @Override
    public void onEnable() {
        mc.gameSettings.gammaSetting = 10;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = 1;
    }
}
