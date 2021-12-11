package dev.leap.frog.Module.World;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Chatutil;
import dev.leap.frog.Util.Render.Renderutil;
import dev.leap.frog.Util.Timer;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.math.BlockPos;

/**
 * TODO: Change where this class is at the modulemanager arraylist
 */

public class ChorusPos extends Module {

    public ChorusPos() {
        super("Chorus pos", "Renders where player moved after eating chorus", Type.WORLD);
    }

    Setting<Boolean> notify = create("Notify", false);

    private BlockPos playerPos;
    private Timer timer = new Timer();

    @Override
    public void onEnable() {
    }

    @Override
    public void onUpdate() {
        if(timer.passed(2000L) && playerPos != null) {
            playerPos = null;
            return;
        }
    }

    @EventHandler
    private Listener<EventPacket.ReceivePacket> packetListener = new Listener<>(event -> {

        if(event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
            if(packet.getSound() == SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT) {
                playerPos = new BlockPos(packet.getX(), packet.getY(), packet.getZ());
                Chatutil.sendClientSideMessgage("Player has used a chorus and is now at " + "X: " + Math.floor(packet.getX()) +"Y:" + Math.floor(packet.getY()) + " Z: " + Math.floor(packet.getZ()));
                timer.reset();
            }
        }

    });

    @Override
    public void onRender(RenderEvent event) {
        if(playerPos != null) {
            Renderutil.drawBoundingBoxBlockPos(playerPos, 1, 255, 0, 0, 200);
        }
    }
}
