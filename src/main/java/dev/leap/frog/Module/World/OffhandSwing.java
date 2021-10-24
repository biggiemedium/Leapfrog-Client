package dev.leap.frog.Module.World;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import net.minecraft.util.EnumHand;

public class OffhandSwing extends Module {

    public OffhandSwing() {
        super("Offhand Swing", "Swings your offhand", Type.WORLD);
    }

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck()) return;

        mc.player.swingingHand = EnumHand.OFF_HAND;
    }
}
