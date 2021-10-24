package dev.leap.frog.Module.Combat;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;

public class AutoLog extends Module {

    public AutoLog() {
        super("Auto Log", "Logs you out of the game if you are at a certain health", Type.COMBAT);
    }

    int totems = AutoTotem.Get.totems;

    Setting<Integer> totem = create("Totems", 1, 0, 20);
    Setting<Integer> disconnect = create("Disconnection health", 10, 0, 35);

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck()) return;

        if(totems < totem.getValue() && Playerutil.getPlayerHealth() < disconnect.getValue()) {
            mc.world.sendQuittingDisconnectingPacket();
            toggle();
        } else {

        }
    }
}
