package dev.leap.frog.Manager;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import club.minnced.discord.rpc.DiscordUser;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenServerList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class DiscordManager {

    private  DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    private  Minecraft mc = Minecraft.getMinecraft();

    private DiscordRichPresence presence = new DiscordRichPresence();
    private Thread thread;
    private  DiscordRPC lib = DiscordRPC.INSTANCE;

    public DiscordManager() {
        Start();
    }

    public void Start() {
        String applicationId = "830252222372773908";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Ready!");
        lib.Discord_Initialize(applicationId, handlers, true, steamId);

        lib.Discord_UpdatePresence(presence);
        presence.largeImageKey = "leapfrog512";
        this.thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                presence.details = setDetails();
                presence.state = setState();


                lib.Discord_UpdatePresence(presence);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
            }
        }, "RPC-Callback-Handler");
        thread.start();
    }

    public void Stop() {
        if(!this.thread.isInterrupted() || thread.isAlive()) {
            this.thread.interrupt();
            this.lib.Discord_Shutdown();
            this.lib.Discord_ClearPresence();
            return;
        }
    }

    private String setState() { // mc.player != null will run first
        String back = discordRichPresence.state;

        if(mc.player == null)
            return "Leapfrog Client by PX";

        if(Playerutil.isMoving(mc.player)) {
            return "Moving " + Playerutil.getSpeedInKM() + " KM/H";
        }

        if(mc.player.isElytraFlying()) {
            return "Elytra flying";
        }

        if(mc.player != null && !Playerutil.isMoving(mc.player) && !mc.player.isElytraFlying()) {
            String[] quotes = {"Leapfrog Client on top!", "Join the discord! https://discord.gg/fT5JVKVUyt", "Leapfrog client by PX and Boncorde"};

            Random r = new Random();
            int index = r.nextInt(quotes.length);
            return quotes[index];
        }

        return back;
    }

    private String setDetails() {
        String detail = discordRichPresence.details;

        if(mc.player == null)
            return "Main Menu";

        if(mc.isSingleplayer()) {
            return mc.player.getName() + " | " + "Singleplayer";
        }

        if(mc.player != null && !mc.isSingleplayer() && !(Objects.requireNonNull(mc.getCurrentServerData()).serverIP == null)) {
            return mc.player.getName() + " | " + "Multiplayer";
        }

        return detail;
    }

    private boolean get2bState() {
        return LeapFrog.getEventProcessor().getLastChat() != null && LeapFrog.getEventProcessor().getLastChat().startsWith("Position in queue:");
    }
}
