package dev.leap.frog.Module.Chat;

import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import dev.leap.frog.Util.Render.Chatutil;
import dev.leap.frog.Util.Wrapper;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class AutoReply extends Module { // saw future had it and wanted it

    public AutoReply() {
        super("AutoReply", "Automatically replys to dms", Type.CHAT);
    }

    @EventHandler
    private final Listener<ClientChatReceivedEvent> listener = new Listener<>(event -> {
        if (event.getMessage().getUnformattedText().contains("whispers: ") && !event.getMessage().getUnformattedText().startsWith(mc.player.getName())) {
            if (event.getMessage().getUnformattedText().contains("I don't speak to newfags!") || event.getMessage().getUnformattedText().contains("Keep popping!")) {
                return;
            }

            mc.player.sendChatMessage("/r " + mc.player.getName() + " " + "is currently unavailable send a message later to them - LeapFrog");
        }
    });

}
