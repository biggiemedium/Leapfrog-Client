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
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class AutoEZ extends Module { // You're poppin to Leapfrog Client! - https://discord.gg/fT5JVKVUyt

    public AutoEZ() {
        super("AutoEz", "Taunts enemies for you", Type.CLIENT);
    }

     Setting<Boolean> dm = create("DM player", true);
     Setting<Boolean> pop = create("On Pop", true);
     Setting<Boolean> discordLink = create("Discord link", true);
     Setting<Boolean> suffix = create("Suffix", true);

    private HashMap<String, Integer> players = new HashMap<>();
    private EntityEnderCrystal crystalAttacked;
    private EntityPlayer currentTarget;
    private int delay;

    @Override
    public void onEnable() {
        delay = 0;
    }

    @Override
    public void onUpdate() {
        delay++;
        for(EntityPlayer e : mc.world.playerEntities) {
            if(e.isDead || e.getHealth() <= 0) {
                if(players.containsKey(e.getName())) {
                    players.remove(e.getName());
                    if(!(delay > 150)) {
                        delay = 0;
                        Chatutil.sendMessage(ezMessage());

                        if(dm.getValue()) {
                            Chatutil.sendMessage("/msg" + " " + e.getName() + " " + "You just got fucked by leapfrog client!");
                        }
                    }
                }
            }
        }


        players.forEach((name, timeout) -> {

            if(timeout <= 0) {
                players.remove(name);
            } else {
                players.put(name, timeout - 1);
            }
            return;
        });

    }

    @EventHandler
    private Listener<AttackEntityEvent> attackListener = new Listener<>(event -> {
        if(event.getTarget() instanceof EntityEnderCrystal) {
            crystalAttacked = (EntityEnderCrystal) event.getTarget();
        }
        if(event.getTarget() instanceof EntityPlayer && !FriendManager.isFriend(event.getTarget().getName()) && this.isToggled()) {
            EntityPlayer target = (EntityPlayer) event.getTarget();
            if(target.getHealth() <= 0.0f || target.isDead) {

            }
            players.put(event.getTarget().getName(), 20);
        }
    });

    @EventHandler
    private Listener<LivingDeathEvent> deathEventListener = new Listener<>(event -> {
        if(event.getEntity() instanceof EntityPlayer && event.getSource().getTrueSource() == crystalAttacked) {

        }
    });

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
        if(event.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
            if(packet.getAction() == CPacketUseEntity.Action.ATTACK) {
                Entity e = packet.getEntityFromWorld(mc.world);
                if(e instanceof EntityPlayer) {
                    players.put(e.getName(), 20);
                }
            }
        }
    });
}
