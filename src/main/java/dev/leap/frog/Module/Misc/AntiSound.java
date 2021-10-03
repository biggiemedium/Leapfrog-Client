package dev.leap.frog.Module.Misc;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class AntiSound extends Module {

    public AntiSound() {
        super("AntiSound", "Prevents sound from rendering", Type.MISC);
    }

    Setting<Boolean> explosion = create("Explosions", true);
    Setting<Boolean> wither = create("Wither", false);
    Setting<Boolean> rain = create("Rain", false);

    @EventHandler
    private Listener<EventPacket.ReceivePacket> packetListener = new Listener<>(event -> {

        if(event.getPacket() instanceof SPacketSoundEffect) {

            SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();

            if(packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE && explosion.getValue()) {
                event.cancel();
            }

            if(packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_WITHER_SPAWN && wither.getValue()) {
                event.cancel();
            }

            if(packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_WITHER_HURT && wither.getValue()) {
                event.cancel();
            }

            if(packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.WEATHER_RAIN && rain.getValue()) {
                event.cancel();
            }

        }
    });
}
