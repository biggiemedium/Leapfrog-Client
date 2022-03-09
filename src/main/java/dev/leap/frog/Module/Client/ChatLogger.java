package dev.leap.frog.Module.Client;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.io.*;

public class ChatLogger extends Module {

    public ChatLogger() {
        super("Chat Logger", "Logs chat messages and sends them to leapfrog directory", Type.CLIENT);
        INSTANCE = this;
    }

    private File f;
    private File dir;
    private BufferedWriter writer;

    public static ChatLogger INSTANCE;

    @Override
    public void onEnable() {
        if(UtilManager.nullCheck()) return;
        open();
    }

    @EventHandler
    private Listener<ClientChatReceivedEvent> chatListener = new Listener<>(event -> {
        if(UtilManager.nullCheck()) return;

        try {
            writer.write(Chatutil.getTimeChat(event.getMessage().getFormattedText()));
            writer.write("\n");
            writer.flush();
        } catch (Exception ignored) {}
    });

    @EventHandler
    private Listener<FMLNetworkEvent.ClientDisconnectionFromServerEvent> disconnection = new Listener<>(event -> {
        this.close(this.writer);
    });

    @Override
    public void onDisable() {
        close(this.writer);
    }

    public void close(BufferedWriter writer) {
        if(writer != null) {
            try { writer.close(); } catch (Exception ignored) {}
        }
    }

    public void open() {
        dir = new File(LeapFrog.getFileManager().path + "/Chat/");
        if(!dir.exists()) {
            dir.mkdirs();
        }

        f = new File(dir + File.separator + Chatutil.getTimeTXT());
        if(!f.exists() || f == null) {
            try {
                assert f != null;
                f.createNewFile(); } catch (Exception ignored) {}
        }
        try {
            writer = new BufferedWriter(new FileWriter(f));
        } catch (Exception ignored) {}
    }

    public BufferedWriter getWriter() {
        return this.writer;
    }
}
