package dev.leap.frog.Module.World;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;
import java.util.Objects;

public class Quiver extends Module {

    public Quiver() {
        super("Quiver", "Shoots arrow with positive effect at you", Type.WORLD);
    }

    Setting<Mode> mode = create("Mode", Mode.Strength);

    private enum Mode {
        Strength,
        Speed
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onUpdate() {
        if(findBowSlot() == -1) return;
        if(mc.player.inventory.currentItem == findBowSlot() && mc.player.getItemInUseCount() >= 3) {
            if(strength() && mc.gameSettings.keyBindUseItem.isKeyDown() || speed() && mc.gameSettings.keyBindUseItem.isKeyDown()) {
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, -90, true));
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            }
        }
    }

    private boolean strength() {
        return mode.getValue() == Mode.Strength && isArrowInInventory("Arrow of Strength");
    }

    private boolean speed() {
        return mode.getValue() == Mode.Speed && isArrowInInventory("Arrow of Speed");
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

    private boolean isArrowInInventory(String arrowType) {
        for(int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if(stack.getItem() == Items.TIPPED_ARROW && stack.getDisplayName().equalsIgnoreCase(arrowType)) {
                return true;
            }
        }

        return false;
    }
}
