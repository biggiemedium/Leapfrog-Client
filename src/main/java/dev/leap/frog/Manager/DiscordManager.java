package dev.leap.frog.Manager;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

public class DiscordManager { // skidded from myself :)

    private  DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    private  Minecraft mc = Minecraft.getMinecraft();
    private  String user = mc.getSession().getUsername();

    private DiscordRichPresence presence = new DiscordRichPresence();
    private Thread _thread = null;

    public void Start() {

        DiscordRPC lib = DiscordRPC.INSTANCE;
        String applicationId = "830252222372773908";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Ready!");
        lib.Discord_Initialize(applicationId, handlers, true, steamId);

        lib.Discord_UpdatePresence(presence);
        presence.largeImageKey = "leapfrog";
        _thread = new Thread(() ->
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

        _thread.start();
    }

    public static float getSpeedInKM() {
        final double deltaX = Minecraft.getMinecraft().player.posX - Minecraft.getMinecraft().player.prevPosX;
        final double deltaZ = Minecraft.getMinecraft().player.posZ - Minecraft.getMinecraft().player.prevPosZ;

        float distance = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        double floor = Math.floor(( distance/1000.0f ) / ( 0.05f/3600.0f ));

        String formatter = String.valueOf(floor);

        if (!formatter.contains("."))
            formatter += ".0";

        return Float.valueOf(formatter);
    }

    private String setState() { // mc.player != null will run first
        String back = discordRichPresence.state;


        if(mc.player == null)
            return "Main menu";

        if(mc.player != null && mc.player.onGround) {
            return "Moving " + getSpeedInKM() + " KMH";
        }

        if(!mc.player.onGround && mc.player != null) {
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
            return user + " | " + "In menu";

        if(mc.player != null || mc.isSingleplayer()) {
            return user + " | " + "Singleplayer";
        }

        if(mc.player != null || !mc.isSingleplayer() || mc.getCurrentServerData().isOnLAN() || mc.getCurrentServerData().serverIP.contains("9")) {
            return user + " | " + "Multiplayer";
        }

        return detail;
    }

}
