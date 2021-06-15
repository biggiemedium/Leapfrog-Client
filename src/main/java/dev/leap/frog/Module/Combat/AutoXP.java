package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
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

    Settings mode = create("Mode", "Mode", "FootXP", combobox("FootXP", "Packet", "FastUse"));

    @Override
    public void onUpdate() {
        if(mc.player == null || mc.world == null) return;

        if(mode.in("FastUse") && mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle || mc.player.getHeldItemOffhand().getItem() instanceof ItemExpBottle) {
                mc.rightClickDelayTimer = 0;
        }

        if(mode.in("Packet")) {



        }
    }

    @EventHandler
    private Listener<EventPacket.SendPacket> listener = new Listener<>(event -> {
        if(event.getPacket() instanceof CPacketPlayerTryUseItem && mode.in("FootXP")) {
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

    public String getMode() {
        if(mode.in("FootXP")) {
            return "Foot";
        }

        if(mode.in("FastUse")) {
            return "FastUse";
        }

        if(mode.in("Packet")) {
            return "Packet";
        }
        return "None";
    }


}
