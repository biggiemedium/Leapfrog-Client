package dev.leap.frog.Module.Movement;

import dev.leap.frog.Event.Network.EventPacket;
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

    private int timer;

    @Override
    public void onEnable() {
        timer = 0;
    }

    @Override
    public void onUpdate() {
        timer++;
        mc.player.rotationYaw = timer * speed.getValue();
        if(timer >= 360) {
            timer = 0;
        }
    }
}
