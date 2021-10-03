package dev.leap.frog.Util.Entity;

import dev.leap.frog.Manager.UtilManager;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;


public class InventoryUtil extends UtilManager {

    public static int findBlockInHotbar(Block blockToFind) {
        int slot = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock) || stack == null) {
                continue;
            }
            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (block.equals(blockToFind)) {
                slot = i;
                break;
            }
        }

        return slot;
    }
}
