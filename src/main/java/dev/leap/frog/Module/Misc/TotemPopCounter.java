package dev.leap.frog.Module.Misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Network.EventPacketUpdate;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Client.AutoEZ;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TotemPopCounter extends Module {

    public TotemPopCounter() {
        super("TotemPopCounter", "Counts nearby players that have popped totems", Type.MISC);
    }

    private HashMap<String, Integer> pops = new HashMap<>();

    Setting<Boolean> ignoreFriends = create("IgnoreFriends", false);
    Setting<Boolean> suffix = create("Suffix", true);

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck()) return;
        if(pops == null) {
            pops = new HashMap<>();
        }

        for (EntityPlayer player : mc.world.playerEntities) {
            if(ignoreFriends.getValue() && FriendManager.isFriend(player.getName())) continue;
            assert pops != null;
            if(!pops.containsKey(player.getName())) continue;
            if(player.getHealth() <= 0 || player.isDead) {
                if(pops.containsKey(player.getName())) {
                    Chatutil.sendClientSideMessgage(player.getName() + " " + "has died after popping " + pops.get(player.getName()) + " " + "totems");
                    pops.remove(player.getName());
                }
            }
        }
    }

    @EventHandler
    private Listener<EventPacket.ReceivePacket> receivePacketListener = new Listener<>(event -> {
        if(UtilManager.nullCheck()) return;
        if(event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if(packet.getOpCode() == 35) {
                Entity e = packet.getEntity(mc.world);
                if(e == null || e == mc.player) return;
                int totalpops = 1;
                if (pops.containsKey(e.getName())) {
                    totalpops = pops.get(e.getName());
                    pops.put(e.getName(), totalpops++);
                } else {
                    pops.put(e.getName(), totalpops);
                }
                if(e == mc.player) return;
                if(ignoreFriends.getValue() && FriendManager.isFriend(e.getName())) return;
                if(suffix.getValue()) {
                    Chatutil.sendMessage("Player " + ChatFormatting.RED + e.getName() + ChatFormatting.RESET + " " + "has popped " + totalpops + " times!" + UtilManager.getSuffix());
                } else {
                    Chatutil.sendMessage("Player " + ChatFormatting.RED + e.getName() + ChatFormatting.RESET + " " + "has popped " + totalpops + " times!");
                }
            }
        }
    });
}
