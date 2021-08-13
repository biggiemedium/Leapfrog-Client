package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;

public class AutoXP extends Module {

    public AutoXP() {
        super("XP", "Xp", Type.COMBAT);
    }

    Setting<XPMode> mode = create("Mode", XPMode.FootXP);

    public enum XPMode {
        FootXP,
        Packet,
        FastUse
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) return;

        if (mode.getValue() == XPMode.FastUse && mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle || mc.player.getHeldItemOffhand().getItem() instanceof ItemExpBottle) {
            mc.rightClickDelayTimer = 0;
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

    });

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
