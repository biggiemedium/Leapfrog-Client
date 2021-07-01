package dev.leap.frog.Manager;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
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

public class DiscordManager {

    private  DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    private  Minecraft mc = Minecraft.getMinecraft();

    private DiscordRichPresence presence = new DiscordRichPresence();
    private Thread thread = null;
    private String lastChat;

    public void Start() {

        DiscordRPC lib = DiscordRPC.INSTANCE;
        String applicationId = "830252222372773908";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Ready!");
        lib.Discord_Initialize(applicationId, handlers, true, steamId);

        lib.Discord_UpdatePresence(presence);
        presence.largeImageKey = "leapfrog512";
        thread = new Thread(() ->
        {
            while (!Thread.currentThread().isInterrupted())
            {
                lib.Discord_RunCallbacks();
                presence.details = setDetails();
                presence.state = setState();


                lib.Discord_UpdatePresence(presence);
                try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored)
                {
                }
            }
        }, "RPC-Callback-Handler");

        thread.start();
    }

    private String setState() { // mc.player != null will run first
        String back = discordRichPresence.state;



        if(mc.player == null)
            return "Main menu";


        if(mc.player != null && LeapFrog.getModuleManager().getModuleName("ClickGUI").isToggled()) {
            return "Configuring Client";
        }
        /* fucks up entire rpc
        if(Objects.requireNonNull(mc.getCurrentServerData()).serverIP.contains("2b2t") && mc.world.getBiome(mc.player.getPosition()).getBiomeName().toLowerCase().contains("end") && mc.player.getPosition().getZ() == 0) {
            return "In queue";
        }

         */

        if(mc.player.onGround) {
            return "Moving " + Playerutil.getSpeedInKM() + " KM/H";
        }

        if(!mc.player.onGround && !mc.getCurrentServerData().serverIP.contains("2b2t")) {
            return "Chilling in " + mc.world.getBiome(mc.player.getPosition()).getBiomeName();
        }

        if(mc.player.isElytraFlying()) {
            return "Zooming";
        }



        return back;
    }

    private String setDetails() {
        String detail = discordRichPresence.details;

        if(mc.player == null)
            return "Hopping into a game!";

        if(mc.isSingleplayer()) {
            return mc.player.getName() + " | " + "Singleplayer";
        }

        if(mc.player != null && !mc.isSingleplayer() && !(Objects.requireNonNull(mc.getCurrentServerData()).serverIP == null)) {
            return mc.player.getName() + " | " + "Multiplayer";
        }

        return detail;
    }

}
