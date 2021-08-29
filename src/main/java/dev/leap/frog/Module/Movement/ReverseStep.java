package dev.leap.frog.Module.Movement;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;

public class ReverseStep extends Module {

    public ReverseStep() {
        super("Reverse Step", "reverses you", Type.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if(mc.player == null || mc.world == null || mc.player.isInWater() || mc.player.isInLava() || mc.player.isOnLadder() || LeapFrog.getModuleManager().getModule(Speed.class).isToggled() || mc.gameSettings.keyBindJump.isKeyDown())
            return;

        if(mc.player.onGround) {
            --mc.player.motionY;
        }
    }
}
