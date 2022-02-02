package dev.leap.frog.Module.Misc;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

public class MiddleClickPearl extends Module {

    public MiddleClickPearl() {
        super("Middle click pearl", "Throws a pearl for you when you press your middle mouse button", Type.MISC);
    }

    @EventHandler
    private Listener<InputEvent.MouseInputEvent> listener = new Listener<>(event -> {
        if(Mouse.getEventButton() == 2) {
                int slot = getPearl(true);
                mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
        }
    });

    private boolean pearlCheck() {
        return getPearl(true) == -1 || getPearl(false) == -1 || UtilManager.nullCheck();
    }

    private int getPearl(boolean hotbar) {
        for(int i = 0; hotbar ? i < 9 : i < 36; i++) {
            if(mc.player.inventory.getStackInSlot(i).getItem() == Items.ENDER_PEARL) {
                return i;
            }
        }
        return -1;
    }
}
