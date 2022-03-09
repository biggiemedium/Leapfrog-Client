package dev.leap.frog.Util.Network;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Manager.UtilManager;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listenable;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraftforge.common.MinecraftForge;

// Going to use alpine for this class instead of forge handler bc fuck it
public class TPSutil extends UtilManager implements Listenable {

    private long time;
    private float[] tickRate;

    public TPSutil() {
        MinecraftForge.EVENT_BUS.register(this);
        this.time = -1;
        this.tickRate = new float[20];
    }

    @EventHandler
    private Listener<EventPacket.ReceivePacket> packetListener = new Listener<>(event -> {
        if(event.getPacket() instanceof SPacketTimeUpdate) {

        }
    });
}
