package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {

    public Velocity() {
        super("Velocity", "Makes you take no knockback", Type.COMBAT);
    }

    @EventHandler
    private Listener<EventPacket.ReceivePacket> move = new Listener<>(event -> {

        if(event.getPacket() instanceof SPacketEntityVelocity) {
            if(((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId()) {
                event.cancel();
            }
        }

      if(event.getPacket() instanceof SPacketExplosion) {
         event.cancel();
      }

    });
}
