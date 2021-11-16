package dev.leap.frog.Module.Client;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class AutoReply extends Module { // saw future had it and wanted it

    public AutoReply() {
        super("AutoReply", "Automatically replys to dms", Type.CLIENT);
    }

    Setting<Boolean> watermark = create("Watermark", true);

    @EventHandler
    private final Listener<ClientChatReceivedEvent> listener = new Listener<>(event -> {
        if (event.getMessage().getUnformattedText().contains("whispers: ") && !event.getMessage().getUnformattedText().startsWith(mc.player.getName())) {
            if (event.getMessage().getUnformattedText().contains("I don't speak to newfags!") || event.getMessage().getUnformattedText().contains("Keep popping!")) {
                return;
            }
            String message = "is currently unavailable send a message later to them";
            String suffix = "- LeapFrog";

            if(!watermark.getValue()) {
                mc.player.sendChatMessage("/r " + mc.player.getName() + " " + message);
            } else if(watermark.getValue() && isOn2b()){
                mc.player.sendChatMessage("/r " + mc.player.getName() + " " + message + suffix);
            } else if(watermark.getValue() && !isOn2b()) {
                mc.player.sendChatMessage("/r " + mc.player.getName() + " " + message + UtilManager.getSuffix());
            }
        }
    });

    private boolean isOn2b() {
        return mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.equalsIgnoreCase("2b2t");
    }

}
