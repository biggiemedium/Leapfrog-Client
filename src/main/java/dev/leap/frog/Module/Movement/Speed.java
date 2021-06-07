package dev.leap.frog.Module.Movement;

import dev.leap.frog.Event.Movement.PlayerMotionUpdateEvent;
import dev.leap.frog.Module.Module;

import dev.leap.frog.Settings.Settings;
import dev.leap.frog.Util.Mathutil;
import io.netty.util.internal.MathUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.Session;
import net.minecraft.util.math.MathHelper;

import java.util.Set;

public class Speed extends Module {
    public Speed() {
        super("Speed", "Allows you to edit your speed", Type.MOVEMENT);
    }
    Settings jump = create("Jump", "Jump", true);



    @EventHandler
    public Listener<PlayerMotionUpdateEvent> MotionListener = new Listener<>(event -> {
        if(jump.getValue(true)){
            if (mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.moveForward != 0) {
                if (mc.player.onGround) {
                    mc.player.jump();
                    mc.player.motionX *= 1.07F;
                    mc.player.motionZ *= 1.07F;
                }
            }
        }
        mc.player.motionY -= 0.0043F;
        mc.player.collidedHorizontally = false;
        mc.player.collidedVertically = false;
        final double[] speed = Mathutil.directionSpeed(0.30245F);
    mc.player.jumpMovementFactor = 0.012124F;
        mc.player.motionX = speed[0];
        mc.player.motionZ = speed[1];
    });




}
