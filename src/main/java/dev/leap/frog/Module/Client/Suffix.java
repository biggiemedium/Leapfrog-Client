package dev.leap.frog.Module.Client;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;

public class Suffix extends Module {

    public Suffix() {
        super("Suffix", "Suffix", Type.CLIENT);
    }
    //                             |    L      e       a  p        f   r       o    g
    private String suffix =  "  \u23D0 \u029f\u1d07\u1d00\u1d18\ua730\u0280\u1d0f\u0262";

    @EventHandler
    private Listener<EventPacket.SendPacket> sendPacketListener = new Listener<>(event -> {

        if((event.getPacket() instanceof CPacketChatMessage)) {
            String message = ((CPacketChatMessage) event.getPacket()).getMessage();

            if (message.startsWith("/")) return; // checking for command prefix
            if (message.startsWith("\\"))return;
            if (message.startsWith("!")) return;
            if (message.startsWith(":")) return;
            if (message.startsWith(";")) return;
            if (message.startsWith(".")) return;
            if (message.startsWith(",")) return;
            if (message.startsWith("@")) return;
            if (message.startsWith("&")) return;
            if (message.startsWith("*")) return;
            if (message.startsWith("$")) return;
            if (message.startsWith("#")) return;
            if (message.startsWith("(")) return;
            if (message.startsWith(")")) return;

            message += suffix;

            if(message.length() >= 250) {
                message = message.substring(0, 250);
            }

            ((CPacketChatMessage) event.getPacket()).message = message;

        }

    });

}
