package dev.leap.frog.Module.Movement;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;

public class Centre extends Module {

    public Centre() {
        super("Centre", "Centres you in a block", Type.MOVEMENT);
    }

    Setting<Double> offsetX = create("X offset", 0.0, -0.5, 0.5);
    Setting<Double> offsetZ = create("Z offset", 0.0, -0.5, 0.5);

    @Override
    public void onEnable() {

        mc.player.setPosition(Math.floor(mc.player.posX) +  (0.5D - offsetX.getValue()), mc.player.posY, Math.floor(mc.player.posZ) + (0.5D - offsetZ.getValue()));

        toggle();
    }

    @Override
    public void onUpdate() {

    }
}
