package dev.leap.frog.Module.Combat;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class AutoLog extends Module {

    public AutoLog() {
        super("Auto Log", "Logs you out of the game if you are at a certain health", Type.COMBAT);
    }

    private int totems = AutoTotem.Get.totems;

    Setting<Integer> disconnect = create("Disconnection health", 10, 0, 35);

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck() || mc.getConnection() == null) return;

        if(Playerutil.getPlayerHealth() < disconnect.getValue()) {
            mc.getConnection().handleDisconnect(new SPacketDisconnect(new TextComponentString("You are at risk of dying, disconnected client")));
            toggle();
        }
    }
}
