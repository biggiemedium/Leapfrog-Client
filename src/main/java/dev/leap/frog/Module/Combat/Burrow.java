package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Movement.EventPlayerJump;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Block.Blockutil;
import dev.leap.frog.Util.Entity.Playerutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Burrow extends Module {

    public Burrow() {
        super("Burrow", "Attempts to hide you in a block", Type.COMBAT);
    }

    Setting<Boolean> jump = create("Jump", true);
    Setting<Boolean> silentSwitch = create("SilentSwitch", false);
    Setting<Boolean> centre = create("Centre", false);
    Setting<Boolean> rotate = create("Rotate", true);
    Setting<Boolean> enderChest = create("EnderChest", false);

    private int startSlot;

    @Override
    public void onEnable() {
        startSlot = mc.player.inventory.currentItem;
    }

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck() || findObsidianInHotbar() == -1) return;

        

    }

    private int findObsidianInHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock) stack.getItem()).getBlock();

                if (block instanceof BlockEnderChest && enderChest.getValue())
                    return i;

                else if (block instanceof BlockObsidian)
                    return i;

            }
        }
        return -1;
    }
}
