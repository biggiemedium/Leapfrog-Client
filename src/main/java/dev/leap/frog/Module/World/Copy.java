package dev.leap.frog.Module.World;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraft.client.multiplayer.ServerData;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class Copy extends Module {

    public Copy() {
        super("Copy", "Copys ingame stats", Type.WORLD);
    }
    Setting<Mode> mode = create("Mode", Mode.Coords);

    Setting<Boolean> notify = create("Notify", true);
    private enum Mode {
        Coords,
        ServerIP,
        Username,
    }

    @Override
    public void onEnable() {
        if(UtilManager.nullCheck()) return;

        if(mode.getValue() == Mode.Coords) {
            copy("coordinates: X: " + Math.floor(mc.player.posX) + " Y: " + Math.floor(mc.player.posY) + " Z: " + Math.floor(mc.player.posZ));
        } else if(mode.getValue() == Mode.ServerIP) {
            if(mc.currentServerData != null && mc.getConnection() != null && mc.currentServerData.serverIP != null) {
                copy(mc.currentServerData.serverIP);
            } else if(mc.isSingleplayer()) {
                copy("Singleplayer");
            }
        } else if(mode.getValue() == Mode.Username) {
            copy(mc.player.getName());
        }
        toggle();
    }

    private void copy(String value) {

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(value), null);

        if(notify.getValue()) {
            Chatutil.sendClientSideMessgage(value);
        }
    }
}
