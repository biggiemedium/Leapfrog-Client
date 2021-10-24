package dev.leap.frog.Module.Combat;

import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Block.Blockutil;
import dev.leap.frog.Util.Entity.Entityutil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWeb;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutoWeb extends Module {

    public AutoWeb() {
        super("AutoWeb", "Webs enemies near you", Type.COMBAT);
    }

    Setting<Boolean> friends = create("Friends", false);
    Setting<Integer> distance = create("Distance", 4, 0, 6);
    Setting<Boolean> rotate = create("Rotate", true);
    Setting<Integer> delayValue = create("Delay", 1, 0, 5);

    private EntityPlayer target;
    private int webSlot;
    private boolean running = false;
    private List<Vec3d> placePositions = new ArrayList<>();
    private boolean place = false;

    @Override
    public void onEnable() {
        if(webSlot == -1) {
            toggle();
        }
        webSlot = this.findWebsInHotbar();
        this.running = true;
    }

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck()) return;

        findTarget();
        if(target == null) {
            if(running) {
                running = false;
            }
            return;
        }

        if(target.isInWeb) {
            return;
        }

        if(findWebsInHotbar() == -1) {
            toggle();
            return;
        }

        Collections.addAll(placePositions, feet);
        int placeSpeed = 0;
        int offset = 0;
        while(placeSpeed < delayValue.getValue()) {

            if(offset >= placePositions.size()) {
                offset = 0;
                break;
            }
            BlockPos pos = new BlockPos(placePositions.get(offset));
            BlockPos pos1 = new BlockPos(target.getPositionVector()).down().add(pos.getX(), pos.getY(), pos.getZ());

            Blockutil.placeBlock(pos1, findWebsInHotbar(), rotate.getValue(), rotate.getValue(), EnumHand.MAIN_HAND);


            for(Entity target : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos1))) {
                if(!(target instanceof EntityItem) && !(target instanceof EntityExpBottle) && !(target instanceof EntityFireworkRocket)) {
                    place = true;
                    break;
                }
            }

            offset++;
        }
    }

    private void findTarget() {
        for(EntityPlayer player : mc.world.playerEntities) {
            if(target == mc.player) continue;
            if(!friends.getValue() && FriendManager.isFriend(player.getName())) continue;
            if(Entityutil.isLiving(player)) continue;
            if(target.isDead || target.getHealth() <= 0) continue;

            if (target == null) {
                target = player;
                continue;
            }

            if (mc.player.getDistance(player) < mc.player.getDistance(target)) {
                target = player;
            }
        }
    }

    private int findWebsInHotbar() {
        for (int i = 0; i < 9; i++) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }
                Block block = ((ItemBlock) stack.getItem()).getBlock();

                if (block instanceof BlockWeb) {
                    return i;
                }

        }
        return -1;
    }

    private Vec3d[] feet = {
        new Vec3d(0, 2, 0),
            new Vec3d(0, 1, 0)
    };
}
