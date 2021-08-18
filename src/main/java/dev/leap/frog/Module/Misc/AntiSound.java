package dev.leap.frog.Module.Misc;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class AntiSound extends Module {

    public AntiSound() {
        super("AntiSound", "Prevents sound from rendering", Type.MISC);
    }

    Setting<Boolean> explosion = create("Explosions", true);
    Setting<Boolean> wither = create("Wither", false);

    @EventHandler
    private Listener<EventPacket.ReceivePacket> packetListener = new Listener<>(event -> {

        if(event.getPacket() instanceof SPacketSoundEffect) {



        }
    });
}
