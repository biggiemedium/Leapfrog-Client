package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.SuperHeroFXutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketCombatEvent;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityStatus;

public class SuperHeroFX extends Module {

    public SuperHeroFX() {
        super("SuperHeroFX", "Makes cool comic effects", Type.RENDER);
    }

    Setting<Integer> timetoErase = create("Erase time", 50, 0, 500);
    Setting<Integer> distance = create("Distance", 5, 0, 20);



    @EventHandler
    private Listener<EventPacket.ReceivePacket> SpacketHitEffect = new Listener<>(event -> {
        if(event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();

            Entity target = packet.getEntity(mc.world);

            if(packet.getOpCode() == 35 && target != null && target != mc.player) {
                if(mc.player.getDistance(target) <= distance.getValue()) {

                }
            }
        }
    });

    @Override
    public void onRender() {
        if(mc.getRenderManager() == null || mc.entityRenderer == null) return;


    }
}
