package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class AutoXP extends Module {

    public AutoXP() {
        super("XP", "Xp", Type.COMBAT);
    }

    Setting<XPMode> mode = create("Mode", XPMode.Packet);

    int xp;

    public enum XPMode {
        FootXP,
        Packet
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) return;
        if(mode.getValue() == XPMode.Packet && Playerutil.getItem(Items.EXPERIENCE_BOTTLE) != -1 && mc.player.isSneaking()) {
            mc.player.connection.sendPacket(new CPacketHeldItemChange(getXPSlot()));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.player.connection.sendPacket(new CPacketHeldItemChange(xp));
        }
    }

    @EventHandler
    private Listener<EventPacket.SendPacket> listener = new Listener<>(event -> {
        if(event.getPacket() instanceof CPacketPlayerTryUseItem && mode.getValue() == XPMode.FootXP) {
            if(mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
                mc.rightClickDelayTimer = 0;
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, 90.0f, mc.player.onGround));
            }
        }
        if(event.getPacket() instanceof CPacketPlayer && mode.getValue() == XPMode.Packet) {
            CPacketPlayer packet = (CPacketPlayer) event.getPacket();

            packet.pitch = 90;
        }
    });

    @Override
    public void onEnable() {
        xp = mc.player.inventory.currentItem;
    }

    @Override
    public void onDisable() {
        mc.player.inventory.currentItem = xp;
        mc.player.connection.sendPacket(new CPacketHeldItemChange(xp));
    }

    int getXPSlot() {
        int XPSlot = -1;
        for (int i = 45; i > 0; i--) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.EXPERIENCE_BOTTLE) {
                XPSlot = i;
                break;
            }
        }
        return XPSlot;
    }
}
