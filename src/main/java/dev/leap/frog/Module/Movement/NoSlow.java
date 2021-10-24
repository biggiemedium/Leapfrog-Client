package dev.leap.frog.Module.Movement;

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

    private boolean sneaking = false;

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

        if(mode.getValue() == Mode.Packet) {
            if(this.sneaking && !mc.player.isHandActive()) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.sneaking = false;
            }
        }
    }

    @EventHandler
    private Listener<InputUpdateEvent> inputUpdateEventListener = new Listener<>(event-> {

        if(mode.getValue() == Mode.Normal) {
            if (mc.player.isHandActive() && !mc.player.isRiding() && !mc.player.isElytraFlying()) {
                event.getMovementInput().moveStrafe *= 5;
                event.getMovementInput().moveForward *= 5;
            }
        }
    });

    @EventHandler
    private Listener<LivingEntityUseItemEvent> itemEventListener = new Listener<>(event -> {
        if(mode.getValue() == Mode.Packet && !this.sneaking && mc.player.getActiveItemStack().getItem() instanceof ItemFood || mc.player.getActiveItemStack().getItem() instanceof ItemBow || mc.player.getActiveItemStack().getItem() instanceof ItemPotion) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            sneaking = true;
        }
    });

    @EventHandler
    private Listener<EventPlayerStoppedUsingItem> itemListener = new Listener<>(event -> {
       if(mode.getValue() == Mode.Packet) {
           event.cancel();
       }
    });

    @EventHandler
    private Listener<EventPacket.SendPacket> packetListener = new Listener<>(event -> {

        if(event.getPacket() instanceof CPacketPlayer && mc.player.isHandActive() && !mc.player.isRiding()) {
            if(mode.getValue() == Mode.Packet) {
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ)), EnumFacing.DOWN));
            }
        }

    });

}
