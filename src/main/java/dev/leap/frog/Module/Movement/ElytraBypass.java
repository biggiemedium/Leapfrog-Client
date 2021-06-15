package dev.leap.frog.Module.Movement;

import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Module.Module;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import org.lwjgl.input.Keyboard;

public class ElytraBypass extends Module {

    public ElytraBypass() {
        super("ElytraFlyBypass", "Allows you to fly in 2b!", Type.EXPLOIT);
        setKey(Keyboard.KEY_P);

    }
   @EventHandler
   public Listener<EventPlayerMotionUpdate> MotionListener = new Listener<>(event-> {

       if(mc.player.isElytraFlying() == true){
           mc.player.motionY = 0;
           mc.player.moveForward = 1.32F;
           if (mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.moveForward != 0) {
           }
       }
   });
}
