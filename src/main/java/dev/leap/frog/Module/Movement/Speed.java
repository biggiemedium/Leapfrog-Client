package dev.leap.frog.Module.Movement;

import dev.leap.frog.Event.Movement.EventPlayerJump;
import dev.leap.frog.Event.Movement.EventPlayerMove;
import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Module.Module;

import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Math.Mathutil;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.Potion;
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
    private double jumpY = 0;

    private enum SpeedMode {
        Strafe,
        NCP
    }

    @Override
    public void onDisable() {
        mc.timer.tickLength = 50;
        mc.player.setSprinting(false);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null || mc.player.isInWeb || mc.player.isInWater() || mc.player.isInLava() || mc.player.isRiding()) {
            return;
        }

        if (mc.player.isElytraFlying()) {
            Chatutil.sendClientSideMessgage("Cannot use speed module when elytra flying! Toggling...");
            toggle();
        }

        if(mode.getValue() == SpeedMode.Strafe) {
            if(!backwards.getValue() && mc.player.moveForward < 0) {
                return;
            }

            mc.player.setSprinting(true);
            if(Playerutil.isMoving(mc.player)) {
                if(mc.player.onGround) {
                    mc.player.motionX -= MathHelper.sin(getRotationYaw()) * 0.2f;
                    mc.player.motionZ += MathHelper.cos(getRotationYaw()) * 0.2f;
                }
            }

            if(mc.player.onGround && Playerutil.isInputKeysPressed() || mc.gameSettings.keyBindJump.isKeyDown()) {
                double jumpYHandler = this.jumpY == 0 ? 0.405f : 0.405f - this.jumpY;
                mc.player.motionY = 0.405f;
            }
        }
    }

    @EventHandler
    public Listener<EventPlayerMove> MotionListener = new Listener<>(event -> {

        if(mode.getValue() == SpeedMode.Strafe && isToggled()) {
            if (strafeCheck()) {
                return;
            }

            double speed = 0.0f;
            double offsetY = 0; // change to 0 ?
            double forward = mc.player.movementInput.moveForward;
            double strafe = mc.player.movementInput.moveStrafe;
            float yaw = mc.player.rotationYaw;

            if (mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                offsetY += (mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F;
            }

                speed = 0.2873f;
            this.jumpY = offsetY;

            if (mc.player.isPotionActive(MobEffects.SPEED)) {
                int amplifier = mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                speed *= (1.0f + 0.2f * (amplifier + 1));
            }

            if (forward == 0.0D && strafe == 0.0D) {
                event.setX(0.0D);
                event.setZ(0.0D);
            } else {
                if (forward != 0.0D) {
                    if (strafe > 0.0D) {
                        yaw += (float) ((forward > 0.0D) ? -45 : 45);
                    } else if (strafe < 0.0D) {
                        yaw += (float) ((forward > 0.0D) ? 45 : -45);
                    }

                    strafe = 0.0D;

                    if (forward > 0.0D) {
                        forward = 1.0D;
                    } else if (forward < 0.0D) {
                        forward = -1.0D;
                    }
                }

                event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)));
                event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F)));
            }
        } else if(mode.getValue() == SpeedMode.NCP && isToggled()) {

            if(strafeCheck()) return;



        }

        event.cancel();
    });

    @Override
    public String getArrayDetails() {
        return mode.getName();
    }

    private float getRotationYaw() {
        float rotationYaw = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float n = 1.0f;
        if (mc.player.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (mc.player.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (mc.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (mc.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }

    private boolean strafeCheck() {
        return mc.player.onGround || mc.player.isInLava() || mc.player.isRiding() || mc.player.isOnLadder() || mc.player.isInWeb || mc.player.isInWater();
    }
}
