package dev.leap.frog.Module.Movement;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import net.minecraft.network.play.client.CPacketPlayer;

public class FastWeb extends Module {

    public FastWeb() {
        super("Fast Web", "ALlows you to go through webs faster", Type.MOVEMENT);
    }

    Setting<Mode> mode = create("Mode", Mode.Normal);

    private enum Mode {
        Normal,
        _2b
    }

    @Override
    public void onUpdate() {

        if(mode.getValue() == Mode.Normal && mc.player.isInWeb) {
            mc.player.onGround = true;
            mc.player.isInWeb = false;
            mc.player.motionX *= 0.84f;
            mc.player.motionZ *= 0.84f;
        } else if(mode.getValue() == Mode._2b && mc.player.isInWeb) {
            mc.player.motionY = 1.1 / -5;
        }

    }
}
