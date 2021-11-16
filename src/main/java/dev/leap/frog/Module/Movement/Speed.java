package dev.leap.frog.Module.Movement;

import dev.leap.frog.Event.Movement.EventPlayerMove;
import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Module.Module;

import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Math.Mathutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Speed extends Module {
    public Speed() {
        super("Speed", "Allows you to edit your speed", Type.MOVEMENT);
    }


    Setting<Boolean> jump = create("Jump", true);
    Setting<SpeedMode> mode = create("Mode", SpeedMode.NCP);
    Setting<Boolean> backwards = create("Backwards", false);

    private int delay;

    private enum SpeedMode {
        Strafe,
        NCP
    }

    @Override
    public void onUpdate() {
        if(mc.player == null || mc.world == null || mc.player.isInWeb || mc.player.isInWater() || mc.player.isInLava() || mc.player.isRiding()) {
            return;
        }

        if(mode.getValue() == SpeedMode.Strafe) {

        }
    }

    @EventHandler
    public Listener<EventPlayerMotionUpdate> MotionListener = new Listener<>(event -> {

        if(mode.getValue() == SpeedMode.NCP) {
            if(mc.player.isInLava() || mc.player.isRiding() || mc.player.isOnLadder() || mc.player.isInWeb || mc.player.isInWater()) {
                return;
            }
            if (jump.getValue()) {
                if (mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.moveForward != 0) {
                    if (mc.player.onGround) {
                        mc.player.jump();
                        mc.player.motionX *= 1.04F;
                        mc.player.motionZ *= 1.04F;
                    }
                }
            }
            mc.player.collidedHorizontally = false;
            mc.player.collidedVertically = false;
            final double[] speed = Mathutil.directionSpeed(0.27245F);
            mc.player.jumpMovementFactor = 0.032124F;
            mc.player.motionX = speed[0];
            mc.player.motionZ = speed[1];
        }
    });

    @EventHandler
    private Listener<EventPlayerMove> playermove = new Listener<>(event -> {

        if(mc.player.capabilities.isFlying || mc.player.isOnLadder()) return;

        if(mode.getValue() == SpeedMode.Strafe) {


        }
    });

    @Override
    public String getArrayDetails() {
        return mode.getName();
    }
}
