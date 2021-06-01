package dev.leap.frog.Module.Chat;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class Announcer extends Module {

    public Announcer() {
        super("Announcer", "gets you muted", Type.MISC);
    }

    Settings clientSide = create("Client sided", "Client sided", true);
    Settings worldJoin = create("PlayerJoin", "PlayerJoin", true);
    Settings worldLeave = create("PlayerLeave", "PlayerLeave", true);

    @EventHandler
    private Listener<EventPacket.SendPacket> send = new Listener<>(event -> {

        if(mc.world == null)
            return;


    });





    @Override
    public void onUpdate() {
        if(mc.player == null || mc.world == null)
            return;



    }
}
