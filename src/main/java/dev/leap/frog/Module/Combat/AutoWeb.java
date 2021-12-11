package dev.leap.frog.Module.Combat;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Block.Blockutil;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Entity.Friendutil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWeb;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutoWeb extends Module {

    public AutoWeb() {
        super("AutoWeb", "Webs enemies near you", Type.COMBAT);
        INSTANCE = this;
    }

    Setting<Boolean> friends = create("Friends", false);
    Setting<Integer> distance = create("Distance", 4, 0, 6);
    Setting<Boolean> rotate = create("Rotate", true);
    Setting<Boolean> holeOnly = create("Hole only", false);

    private EntityPlayer target;
    private int webSlot;
    private boolean sneaking = false;
    private BlockPos targetPos;
    private ArrayList<Vec3d> placeList;
    private int delay;

    public static AutoWeb INSTANCE = new AutoWeb();

    @Override
    public void onEnable() {
        if(webSlot == -1) {
            toggle();
        }
        placeList = new ArrayList<>();
        webSlot = this.findWebsInHotbar();
        delay = 0;
    }

    @Override
    public void onDisable() {
        if(sneaking) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            sneaking = false;
        }
    }

    @Override
    public void onUpdate() {
        if (UtilManager.nullCheck()) return;
        if (findWebsInHotbar() == -1) return;
        if (LeapFrog.getModuleManager().getModule(CrystalAura.class).isToggled()) return;
        target = findTarget(distance.getValue());
        if(target == null) return;
        if(placeList == null) return;
        Collections.addAll(placeList, feet);

        if(holeOnly.getValue() && !Entityutil.isPlayerInHole(target)) return;
        placeBlock(Entityutil.getPositionEntity(target));
    }

    private void placeBlock(BlockPos pos) {
        if (!mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return;
        }
        if (!Blockutil.checkForNeighbours(pos)) {
            return;
        }

        Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);

        for(EnumFacing side : EnumFacing.values()) {
            BlockPos neighbour = pos.offset(side);
            EnumFacing side2 = side.getOpposite();
            if (mc.world.getBlockState(neighbour).getBlock().canCollideCheck(mc.world.getBlockState(neighbour), false)) {
                Vec3d hitVec = new Vec3d((Vec3i) neighbour).add(new Vec3d(0.5, 0.5, 0.5)).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (eyesPos.distanceTo(hitVec) <= distance.getValue()) {
                    int web = findWebsInHotbar();
                    if(web == -1) {
                        toggle();
                        return;
                    }

                    int oldSlot = mc.player.inventory.currentItem;
                    if(oldSlot != web) {
                        mc.player.inventory.currentItem = web;
                    }

                    if(!mc.player.isSneaking() && Blockutil.blackList.contains(neighbour)) {
                        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                        this.sneaking = true;
                    }

                    if(rotate.getValue()) {
                        Blockutil.faceVectorPacketInstant(hitVec);
                    }

                    mc.playerController.processRightClickBlock(mc.player, mc.world, neighbour, side2, hitVec, EnumHand.MAIN_HAND);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            }
        }
    }

    private boolean isTrapped(Entity entity) {
        return entity.isInWeb;
    }

    private EntityPlayer findTarget(int range) {
        EntityPlayer player = null;
        if(mc.world.playerEntities.isEmpty()) {
            return null;
        }

        for(EntityPlayer targets : mc.world.playerEntities) {
            if(targets == null) continue;
            if(targets == mc.player) continue;
            if(targets.isDead || !Entityutil.isLiving(targets)) continue;
            if(!friends.getValue() && FriendManager.isFriend(targets.getName())) continue;
            if(mc.player.getDistance(targets) > range) continue; // check

            player = targets;
        }

        return player;
    }

    public int findWebsInHotbar() {
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

    public EntityPlayer getTarget() {
        return this.target;
    }

    private Vec3d[] feet = {
        new Vec3d(0, 2, 0),
            new Vec3d(0, 1, 0)
    };
}
