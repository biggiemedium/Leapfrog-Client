package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

public class Criticals extends Module {

    public Criticals() {
        super("Critical", "Hits criticals on entites", Type.COMBAT);
    }

    Setting<Mode> mode = create("mode", Mode.Normal);

    private enum Mode {
        Normal,
        Packet,
        Jump
    }

    @EventHandler
    private Listener<EventPacket.SendPacket> sendPacketListener = new Listener<>(event -> {

        if(event.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
            if (packet.action == CPacketUseEntity.Action.ATTACK) {
               // if(packet.getEntityFromWorld(mc.world) instanceof EntityLivingBase && !mc.gameSettings.keyBindJump.isKeyDown())
                if (mode.getValue() == Mode.Normal) {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.0f, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                } else if(mode.getValue() == Mode.Packet) {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));

                } else if(mode.getValue() == Mode.Jump) {
                    mc.player.jump();
                    mc.player.motionY /= 2.0;
                }
            }
        }
    });
}
