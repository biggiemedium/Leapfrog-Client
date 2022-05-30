package dev.leap.frog.Module.Misc;

import com.mojang.authlib.GameProfile;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketVehicleMove;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Blink extends Module {

    public Blink() {
        super("Blink", "Spoofs your location", Type.MISC);
    }

    Setting<Boolean> showPlayer = create("Show Player", true);

    private EntityOtherPlayerMP player;
    private Queue<Packet<?>> packets = new ConcurrentLinkedDeque<>();

    @Override
    public void onEnable() {
        if(UtilManager.nullCheck()) {
            toggle();
            return;
        }

        packets.clear();

        if(showPlayer.getValue()) {
            player = new EntityOtherPlayerMP(mc.world, new GameProfile(mc.player.getUniqueID(), mc.player.getName()));
            player.setLocationAndAngles(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.rotationYaw, mc.player.rotationPitch);
            //player.rotationYaw = mc.player.rotationYaw;
            //player.rotationPitch = mc.player.rotationYaw;
            //player.setPosition(mc.player.posX, mc.player.posY, mc.player.posZ);
            player.inventory = mc.player.inventory;
            mc.player.setPlayerSPHealth(20.0f);
            mc.world.addEntityToWorld(-100, player);
        }
    }

    @Override
    public void onDisable() {
        if(player != null) {
            mc.world.removeEntityFromWorld(-100);
        }

        if(!packets.isEmpty() && mc.world != null) {
            while(!packets.isEmpty()) {
               mc.player.connection.sendPacket(this.packets.poll());
            }
        }
    }

    @EventHandler
    private Listener<EventPacket.SendPacket> packetListener = new Listener<>(event -> {
       if(event.getPacket() instanceof CPacketPlayer || event.getPacket() instanceof CPacketConfirmTeleport || event.getPacket() instanceof CPacketVehicleMove) {
           event.cancel();
           packets.add(event.getPacket());
       }
    });
}
