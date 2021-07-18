package dev.leap.frog.Module.Movement;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.client.event.InputUpdateEvent;
import org.lwjgl.input.Keyboard;

public class NoSlow extends Module {

    public NoSlow() {
        super("NoSlow", "Stops you from slowing down", Type.MOVEMENT);
        setKey(Keyboard.KEY_J);
    }


    @Override
    public void onUpdate() {

        if(mc.player == null) return;



    }

    @EventHandler
    private Listener<InputUpdateEvent> inputUpdateEventListener = new Listener<>(event-> {
            if (mc.player.isHandActive() && !mc.player.isRiding() && !mc.player.isElytraFlying()) {
                event.getMovementInput().moveStrafe *= 5;
                event.getMovementInput().moveForward *= 5;
            }
    });
}
