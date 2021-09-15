package dev.leap.frog.Module.Client;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Timer;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.util.concurrent.ConcurrentHashMap;

public class AutoEZ extends Module { // You're poppin to Leapfrog Client! - https://discord.gg/fT5JVKVUyt

    public AutoEZ() {
        super("AutoEz", "Taunts enemies for you", Type.CLIENT);
    }

    private Setting<Boolean> dm = create("DM player", true);
    private Setting<Boolean> discordLink = create("Discord link", true);

    private ConcurrentHashMap<String, Integer> players = new ConcurrentHashMap();

    @Override
    public void onEnable() {
    }

    @Override
    public void onUpdate() {

        for(Entity e : mc.world.loadedEntityList) {
            if(e instanceof EntityPlayer) {
            }
        }
    }

    @EventHandler
    private Listener<AttackEntityEvent> attackEntityEventListener = new Listener<>(event -> {

        if(event.getTarget() instanceof EntityEnderCrystal) {

        }

    });

    @EventHandler
    private Listener<EventPacket.SendPacket> playersAttacked = new Listener<>(event -> {

        if(event.getPacket() instanceof CPacketUseEntity) {

        }

    });
}
