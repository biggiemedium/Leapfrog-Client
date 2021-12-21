package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Movement.EventEntityCollision;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {

    public Velocity() {
        super("Velocity", "Makes you take no knockback", Type.COMBAT);
    }

    Setting<Boolean> push = create("Push", true);
    Setting<Boolean> explosion = create("explosion", true);

    Setting<Integer> offput = create("Offset", 0, 0, 100);

    @EventHandler
    private Listener<EventPacket.ReceivePacket> move = new Listener<>(event -> {

        if(explosion.getValue() && event.getPacket() instanceof SPacketExplosion) {
            SPacketExplosion packet = (SPacketExplosion) event.getPacket();
            if(offput.getValue() == 0) {
                event.cancel();
                return;
            }

            packet.motionX = packet.motionX / 100 * this.offput.getValue();
            packet.motionY = packet.motionY / 50 * this.offput.getValue();
            packet.motionZ = packet.motionZ / 100 * this.offput.getValue();

        }

        if(explosion.getValue() && event.getPacket() instanceof SPacketEntityVelocity) {
            SPacketEntityVelocity packet = (SPacketEntityVelocity) event.getPacket();
            if (packet.getEntityID() == mc.player.getEntityId()) {
                if (offput.getValue() == 0) {
                    event.cancel();
                    return;
                } else if(offput.getValue() >= 1) {
                    packet.motionX = packet.motionX / 100 * this.offput.getValue();
                    packet.motionZ = packet.motionY / 100 * this.offput.getValue();
                    packet.motionZ = packet.motionZ / 100 * this.offput.getValue();
                }
            }
        }
    });

    @EventHandler
    private Listener<EventEntityCollision> collision = new Listener<>(event-> {

        if(push.getValue())
            event.cancel();
    });

}
