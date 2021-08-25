package dev.leap.frog.Module.Render;

import dev.leap.frog.Module.Module;

public class FullBright extends Module {

    public FullBright() {
        super("FullBright", "Shows full brightness", Type.RENDER);
    }

    float playerBrightness = mc.gameSettings.gammaSetting;

    @Override
    public void onUpdate() {
        if(mc.player == null) return;

        mc.gameSettings.gammaSetting = 10;
    }

    @Override
    public void onDisable() {

        if(playerBrightness <= 1) {
            mc.gameSettings.gammaSetting = 1;
        } else {
            mc.gameSettings.gammaSetting = playerBrightness;
        }
    }
}
