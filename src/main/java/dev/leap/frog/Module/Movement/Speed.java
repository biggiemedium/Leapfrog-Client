package dev.leap.frog.Module.Movement;

import dev.leap.frog.Event.Movement.PlayerMotionUpdateEvent;
import dev.leap.frog.Module.Module;

import dev.leap.frog.Util.Mathutil;
import io.netty.util.internal.MathUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

public class Speed extends Module {
    public Speed() {
        super("Speed", "Allows you to edit your speed", Type.MOVEMENT);

    }
    @EventHandler
    public Listener<PlayerMotionUpdateEvent> MotionListener = new Listener<>(event -> {


        mc.player.motionX = Mathutil.getMovement(0.65F, mc.player.rotationYawHead)[0];
        mc.player.motionZ = Mathutil.getMovement(0.65F, mc.player.rotationYawHead)[1];
    });

//skidded lol :)
    public float getDirection(){
        Minecraft mc = Minecraft.getMinecraft();
        float var1 = mc.player.rotationYaw;

        if(mc.player.moveForward < 0.0F){
            var1 += 180.0F;
        }
        float forward = 1.0F;
        if(mc.player.moveForward < 0.0F){
            forward = -0.5F;
        }else if(mc.player.moveForward > 0.0F){
            forward = 0.5F;
        }
        if(mc.player.moveStrafing > 0.0F){
            var1 -= 90.0F * forward;
        }
        if(mc.player.moveStrafing < 0.0F){
            var1 += 90.0F * forward;
        }
        var1 *= 0.017453292F; //apparently a faster version of maths.toradians like ik what that is
        return var1;
    }

}
