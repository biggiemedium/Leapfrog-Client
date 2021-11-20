package dev.leap.frog.Module.Movement;

import dev.leap.frog.Event.LeapFrogEvent;
import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Event.Movement.EventPlayerStoppedUsingItem;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import org.lwjgl.input.Keyboard;

public class NoSlow extends Module {

    public NoSlow() {
        super("NoSlow", "Stops you from slowing down", Type.MOVEMENT);
    }

    Setting<Mode> mode = create("Mode", Mode.Normal);
    Setting<Boolean> run = create("Eat run", true);
    Setting<Boolean> web = create("Webs", true);

    private enum Mode {
        Normal,
        Packet
    }


    @Override
    public void onUpdate() {

        if(mc.player == null) return;

        if(mode.getValue() == Mode.Normal && run.getValue()) {
            if(Playerutil.isEating() && mc.gameSettings.keyBindForward.isKeyDown() && mc.player.moveForward > 0 || mc.player.moveStrafing > 0) {
                mc.player.setSprinting(true);
            }
        }

        if(web.getValue()) {
            if(mc.player.isInWeb) {
                mc.player.onGround = false;
                mc.player.isInWeb = false;
                mc.player.motionX *= 0.84;
                mc.player.motionZ *= 0.84;
            }
        }
    }

    @EventHandler
    private Listener<InputUpdateEvent> inputUpdateEventListener = new Listener<>(event-> {

        if(mode.getValue() == Mode.Normal && run.getValue()) {
            if (mc.player.isHandActive() && !mc.player.isRiding() && !mc.player.isElytraFlying()) {
                event.getMovementInput().moveStrafe *= 5;
                event.getMovementInput().moveForward *= 5;
            }
        }
    });

    @EventHandler
    private Listener<EventPlayerMotionUpdate> updateListener = new Listener<>(event -> {
        if(event.getEra().equals(LeapFrogEvent.Era.POST) && mode.getValue() == Mode.Packet) {
            if(run.getValue() && mc.player.isHandActive() && !mc.player.isRiding()) {
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
        }
    });

    @EventHandler
    private Listener<EventPacket.SendPacket> packetListener = new Listener<>(event -> {

        if (event.getPacket() instanceof CPacketClickWindow && mode.getValue() == Mode.Packet && run.getValue()) {
                if (mc.player.isActiveItemStackBlocking()) {
                    mc.playerController.onStoppedUsingItem(mc.player);
                }
                if (mc.player.isSneaking())
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                if (mc.player.isSprinting())
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
    });

}
