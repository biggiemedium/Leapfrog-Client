package dev.leap.frog.Module.Movement;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Math.Mathutil;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;

public class FastSwim extends Module {

    public FastSwim() {
        super("Fast Swim", "Changes default vanilla swim speed", Type.MOVEMENT);
    }

    Setting<Mode> mode = create("Mode", Mode.Normal);

    Setting<Double> boost = create("Boost", 0.0, 0.0, 5.0, v -> mode.getValue() == Mode.AAC);
    Setting<Double> lavaBoost = create("Lava boost", 0.0, 0.0, 5.0, v -> mode.getValue() == Mode.AAC);

    Setting<Boolean> yawSpoof = create("YawSpoof", false);

    private enum Mode {
        Normal,
        AAC
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onUpdate() {

        if(mode.getValue() == Mode.AAC && mc.player.isInWater() && !mc.player.onGround) {
            double[] dir = Mathutil.directionSpeed(0.095f);

            mc.player.motionX = dir[0] * boost.getValue();
            mc.player.motionZ = dir[1] * boost.getValue();
        }

        if(mode.getValue() == Mode.Normal && !mc.player.onGround) {
            if (mc.player.isInLava() && !mc.player.onGround) {
                if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                    mc.player.motionX -= MathHelper.sin(yawRotations()) * lavaBoost.getValue() / 10;
                    mc.player.motionZ += MathHelper.cos(yawRotations()) * lavaBoost.getValue() / 10;
                }
            } else if(mc.player.isInWater() && !mc.player.onGround) {
                mc.player.setSprinting(true);
                if (mc.gameSettings.keyBindJump.isKeyDown() && !mc.player.collidedHorizontally && !mc.player.collidedVertically) {
                    mc.player.motionY = .725 / 5;
                }
                if(yawSpoof.getValue()) {
                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation(yawRotations(), mc.player.rotationPitch, mc.player.onGround));
                }

                if (mc.gameSettings.keyBindSneak.isKeyDown() || mc.player.isSneaking()) {
                    int offset = 5 * -1;
                    mc.player.motionY = 2.2 / offset;
                }

                double[] dir = Mathutil.directionSpeed(0.10f);
                mc.player.motionX = dir[0] * boost.getValue();
                mc.player.motionZ = dir[1] * boost.getValue();
            }
        }
    }

    /**
     * @author ionar
     */

    private float yawRotations() {
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
}
