package dev.leap.frog.Module.Render;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;

public class HandSize extends Module {

    public HandSize() {
        super("HandSize", "Adjust the hand of player hand", Type.RENDER);
    }

    Setting<Float> mainhand = create("MainHand", 0.5f, 0.0f, 1.0f);
    Setting<Float> offhand = create("OffHand", 0.5f, 0.0f, 1.0f);

    @Override
    public void onUpdate() {
        mc.entityRenderer.itemRenderer.equippedProgressMainHand = mainhand.getValue();
        mc.entityRenderer.itemRenderer.equippedProgressOffHand = offhand.getValue();
    }
}
