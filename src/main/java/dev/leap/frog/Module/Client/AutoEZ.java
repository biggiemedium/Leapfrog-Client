package dev.leap.frog.Module.Client;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Render.Chatutil;
import dev.leap.frog.Util.Timer;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class AutoEZ extends Module { // You're poppin to Leapfrog Client! - https://discord.gg/fT5JVKVUyt

    public AutoEZ() {
        super("AutoEz", "Taunts enemies for you", Type.CLIENT);
    }

     Setting<Boolean> dm = create("DM player", true);
     Setting<Boolean> discordLink = create("Discord link", true);
     Setting<Boolean> suffix = create("Suffix", true);

    @Override
    public void onEnable() {
    }

    @Override
    public void onUpdate() {
    }

    private String ezMessage() {
        String message = "You just got cummed on by leapfrog client!";

        if(discordLink.getValue()) {
            message += " " + "Join the discord at" + " " + "https://discord.gg/fT5JVKVUyt";
        }

        if(suffix.getValue()) {
            message += " " + UtilManager.getSuffix();
        }

        return message;
    }

    @EventHandler
    private Listener<EventPacket.SendPacket> playersAttacked = new Listener<>(event -> {
        if(UtilManager.nullCheck()) return;
    });
}
