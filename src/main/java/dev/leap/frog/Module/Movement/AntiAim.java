package dev.leap.frog.Module.Movement;

import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Event.Movement.EventPlayerTravel;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Network.EventPacketUpdate;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class AntiAim extends Module { // TODO: Finish this

    public AntiAim() {
        super("Anti Aim", "Exactly what you think", Type.MOVEMENT);
    }

    Setting<Integer> speed = create("Speed", 5, 1, 10);

    private float yaw;

    @EventHandler
    private Listener<EventPacketUpdate> updateListener = new Listener<>(event -> {
        this.yaw = mc.player.rotationYaw;
        this.yaw += speed.getValue();
    });

    @EventHandler
    private Listener<EventPacket.SendPacket> rotationlistener = new Listener<>(event -> {
        if(event.getPacket() instanceof CPacketPlayer.Rotation) {
            CPacketPlayer.Rotation packet = (CPacketPlayer.Rotation) event.getPacket();
        }
    });
}
