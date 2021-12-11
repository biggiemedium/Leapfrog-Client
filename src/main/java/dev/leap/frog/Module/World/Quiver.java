package dev.leap.frog.Module.World;

import dev.leap.frog.Module.Module;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.PotionEffect;

import java.util.Collection;

public class Quiver extends Module {

    public Quiver() {
        super("Quiver", "Shoots arrow with positive effect at you", Type.WORLD);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onUpdate() {
        if(findBowSlot() == -1) return;
        if(mc.player.inventory.currentItem == findBowSlot() && mc.player.getItemInUseCount() >= 3) {
            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.cameraYaw, -90, true));
              // mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        }
    }

    private int findBowSlot() {
        int slot = -1;
        for(int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBow)) continue;
            slot = i;
            break;
        }
        return slot;
    }
}
