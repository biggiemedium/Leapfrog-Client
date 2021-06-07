package dev.leap.frog.Module.Movement;

import dev.leap.frog.Event.Movement.EventPlayerJump;
import dev.leap.frog.Event.Movement.EventPlayerMove;
import dev.leap.frog.Event.Movement.PlayerMotionUpdateEvent;
import dev.leap.frog.Module.Module;

import dev.leap.frog.Settings.Settings;
import dev.leap.frog.Util.Mathutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;

public class Speed extends Module {
    public Speed() {
        super("Speed", "Allows you to edit your speed", Type.MOVEMENT);
    }


    Settings jump = create("Jump", "Jump", true);
    Settings mode = create("Mode", "Mode", "Strafe", combobox("Strafe", "leap")); // boncorde can rename leap to whatever he wants
    Settings backwards = create("Backwards", "Backwards", false);

    @Override
    public void onUpdate() {
        if(mc.player == null || mc.world == null || mc.player.isInWeb || mc.player.isInWater() || mc.player.isInLava() || mc.player.isRiding()) {
            return;
        }

        if(mode.in("Strafe")) {
            if (mc.player.moveForward != 0 || mc.player.moveStrafing != 0) {

                if (mc.player.moveForward < 0 && !backwards.getValue(true)) return;
                if (mc.player.onGround) {
                    if (jump.getValue(true)) {
                        mc.player.motionY = 0.405f;
                    }

                    final float yaw = getRotationYaw();
                    mc.player.motionX -= MathHelper.sin(yaw) * 0.2f;
                    mc.player.motionZ += MathHelper.cos(yaw) * 0.2f;
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.4, mc.player.posZ, false));
                }
            }
            if (mc.gameSettings.keyBindJump.isKeyDown() && mc.player.onGround) {
                mc.player.motionY = 0.405f;
            }
        }

    }

    @EventHandler
    public Listener<PlayerMotionUpdateEvent> MotionListener = new Listener<>(event -> {

        if(mode.in("leap")) {
            if(mc.player.isInLava() || mc.player.isRiding() || mc.player.isOnLadder() || mc.player.isInWeb || mc.player.isInWater()) {
                return;
            }
            if (jump.getValue(true)) {
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

        if(mode.in("Strafe")) {
            float speed = 0.2873f;
            float moveForward = mc.player.movementInput.moveForward;
            float moveStrafe = mc.player.movementInput.moveStrafe;
            float rotationYaw = mc.player.rotationYaw;

            if(mc.player.isPotionActive(MobEffects.SPEED)) {
                int amplifier = mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();

                speed *= (1.2 * (amplifier + 1));
            }

            speed *= 1.0064f;

            if (moveForward == 0 && moveStrafe == 0) {
                event.setX(0.0D);
                event.setZ(0.0D);
            } else {
                if (moveForward != 0.0f) {
                    if (moveStrafe > 0.0f) {
                        rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
                    } else if (moveStrafe< 0.0f) {
                        rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
                    }
                    moveStrafe = 0.0f;
                    if (moveForward > 0.0f) {
                        moveForward = 1.0f;
                    } else if (moveForward < 0.0f) {
                        moveForward = -1.0f;
                    }
                }
            }
            event.setX((moveForward * speed) * Math.cos(Math.toRadians((rotationYaw + 90.0f))) + (moveStrafe * speed) * Math.sin(Math.toRadians((rotationYaw + 90.0f))));
            event.setZ((moveForward * speed) * Math.sin(Math.toRadians((rotationYaw + 90.0f))) - (moveStrafe * speed) * Math.cos(Math.toRadians((rotationYaw + 90.0f))));

        }

    });

    private float getRotationYaw() {
        float rotation_yaw = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0f) {
            rotation_yaw += 180.0f;
        }
        float n = 1.0f;
        if (mc.player.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (mc.player.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (mc.player.moveStrafing > 0.0f) {
            rotation_yaw -= 90.0f * n;
        }
        if (mc.player.moveStrafing < 0.0f) {
            rotation_yaw += 90.0f * n;
        }
        return rotation_yaw * 0.017453292f;
    }

}
