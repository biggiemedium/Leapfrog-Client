package dev.leap.frog.Module.Movement;

import dev.leap.frog.Event.Movement.PlayerMotionUpdateEvent;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraftforge.event.world.NoteBlockEvent;
import org.lwjgl.input.Keyboard;

public class ElytraBypass extends Module {

    public ElytraBypass() {
        super("ElytraFlyBypass", "Allows you to fly in 2b!", Type.EXPLOIT);
        setKey(Keyboard.KEY_P);

    }
   @EventHandler
   public Listener<PlayerMotionUpdateEvent> MotionListener = new Listener<>(event-> {
       mc.player.noClip = true;

       double down = 0;
       mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX + mc.player.motionX, mc.player.posY - 1337, mc.player.posZ + mc.player.motionZ, mc.player.rotationYaw, mc.player.rotationPitch, true));


   });
}