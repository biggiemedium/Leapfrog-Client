package dev.leap.frog.Module.Client;

import dev.leap.frog.Module.Module;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class AutoReply extends Module { // saw future had it and wanted it

    public AutoReply() {
        super("AutoReply", "Automatically replys to dms", Type.CLIENT);
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
