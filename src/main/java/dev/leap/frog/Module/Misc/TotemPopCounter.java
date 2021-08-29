package dev.leap.frog.Module.Misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Network.EventPacketUpdate;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TotemPopCounter extends Module {

    public TotemPopCounter() {
        super("TotemPopCounter", "Counts nearby players that have popped totems", Type.MISC);
    }

    private ArrayList<EntityPlayer> players = new ArrayList<>();

    @EventHandler
    private Listener<EventPacketUpdate> updateListener = new Listener<>(event -> {
        if(UtilManager.nullCheck()) return;
        mc.world.playerEntities.forEach(entity -> {
            players.add(entity);
            if(entity.getHealth() == 0.0f || entity.isDead && players.contains(entity)) {
                players.remove(entity);
                sendMessage(entity.getName() + " " + "Has died!");
            }
        });

    });

    @EventHandler
    private Listener<EventPacket.ReceivePacket> listener = new Listener<>(event -> {

        if(event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if(packet.getOpCode() == 35 && packet.getEntity(mc.world) instanceof EntityPlayer) {
                EntityPlayer poppedPlayer = (EntityPlayer) packet.getEntity(mc.world);
                if(poppedPlayer == mc.player) return;
                int counter = 0;
                if(players.contains(poppedPlayer) && FriendManager.isFriend(poppedPlayer.getName())) {
                    sendMessage(ChatFormatting.BLUE + poppedPlayer.getName() + ChatFormatting.RESET + " " + "has popped" + " " + counter + " times!");
                } else if(players.contains(poppedPlayer) && !FriendManager.isFriend(poppedPlayer.getName())) {
                    sendMessage(poppedPlayer.getName() + " " + "has popped" + " " + counter + " times!");
                }
            }
        }

    });

    private void sendMessage(String message) {
        String prefix = UtilManager.getPrefix();

        if(!UtilManager.nullCheck()) {
            mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString(prefix + message), deletionLine());
        }
    }

    private int deletionLine() {
        AtomicInteger x = new AtomicInteger();

        mc.world.playerEntities.forEach(e -> {
                if(e.isDead)
                    x.set(5936);

        });


        return x.get();
    }

}
