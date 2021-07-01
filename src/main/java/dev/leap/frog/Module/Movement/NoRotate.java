package dev.leap.frog.Module.Movement;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class NoRotate extends Module {

    public NoRotate() {
        super("NoRotate", "stops server side rotations", Type.MOVEMENT);
    }

    @EventHandler
    private Listener<EventPacket.ReceivePacket> packetListener = new Listener<>(event -> {

        if(event.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();

            packet.pitch = mc.player.rotationPitch;
            packet.yaw = mc.player.rotationYaw;

        }
    });

}
