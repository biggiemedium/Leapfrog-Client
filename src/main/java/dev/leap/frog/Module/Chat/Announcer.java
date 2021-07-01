package dev.leap.frog.Module.Chat;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Announcer extends Module {

    public Announcer() {
        super("Announcer", "gets you muted", Type.CHAT);
    }

    Settings clientSide = create("Client sided", "Client sided", true);
    Settings delay = create("Delay", "Delay", 4, 0, 20);
    Settings worldJoin = create("PlayerJoin", "PlayerJoin", true);
    Settings worldLeave = create("PlayerLeave", "PlayerLeave", true);
    Settings jump = create("Jump", "Jump", true);

    Settings queueSize = create("QueueSize", "QueueSize", 5, 0, 20);


    @Override
    public void onEnable() {



    }

    @EventHandler
    private Listener<EventPacket.SendPacket> send = new Listener<>(event -> {
        if(mc.world == null)
            return;
    });

    @EventHandler
    private Listener<LivingEvent.LivingJumpEvent> jumpListener = new Listener<>(event -> {


        if(jump.getValue(true)) {
            if(event.getEntityLiving() == mc.player) {
                if(clientSide.getValue(true)) {
                    Chatutil.ClientSideMessgage("I just jumped!");
                }
                if(!clientSide.getValue(true)) {
                    mc.player.sendChatMessage("I just jumped!");
                }
            }
        }
    });

    @Override
    public void onUpdate() {
        if(mc.player == null || mc.world == null) {
            return;
        }



    }
}
