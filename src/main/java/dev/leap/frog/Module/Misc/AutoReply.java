package dev.leap.frog.Module.Misc;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class AutoReply extends Module { // saw future had it and wanted it

    public AutoReply() {
        super("AutoReply", "Automatically replys to dms", Type.MISC);
    }

    @EventHandler
    private Listener<ClientChatReceivedEvent> receiver = new Listener<>(event -> {
         if(event.getMessage().getUnformattedText().contains("whispers: ") && !event.getMessage().getUnformattedText().startsWith(mc.player.getName())) {

             Chatutil.SendGlobalChatMessage("/r " + mc.player.getName() + " is unavailable right now - leapfrog");

         }
    });

}
