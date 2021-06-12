package dev.leap.frog.Module.Combat;

import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import dev.leap.frog.Util.Block.Blockutil;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutoTrap extends Module {

    public AutoTrap() {
        super("Auto Trap", "Traps enemies", Type.COMBAT);
    }

    Settings mode = create("Mode", "Mode", "Normal", combobox("Normal", "Feet", "Face"));
    Settings notify = create("Notify", "Notify", true);
    Settings blocksperTick = create("Ticks", "Delay", 4, 0, 8);
    Settings toggle = create("Toggle", "Toggle", true);
    Settings rotate = create("Rotate", "Rotate", true);
    Settings arm = create("Arm", "Arm", "Mainhand" , combobox("Mainhand", "Offhand"));

    private String lastTickTargetName = "";
    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;
    private boolean isSneaking = false;
    private int offsetStep = 0;
    private boolean firstRun = true;

    @Override
    public void onEnable() {

        if(mc.player == null) {
            toggle();
            return;
        }

        firstRun = true;
        playerHotbarSlot = mc.player.inventory.currentItem;
        lastHotbarSlot = -1;

        if (notify.getValue(true)) {
            Chatutil.setModuleMessage(this);
        }
    }

    @Override
    public void onDisable() {

        if (mc.player == null | mc.world == null) return;

        if (lastHotbarSlot != playerHotbarSlot && playerHotbarSlot != -1)
            mc.player.inventory.currentItem = playerHotbarSlot;

        if (isSneaking)
        {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            isSneaking = false;
        }
        playerHotbarSlot = -1;
        lastHotbarSlot = -1;

        if (notify.getValue(true)) {
            Chatutil.setModuleMessage(this);
        }
    }

    @Override
    public void onUpdate() {

        EntityPlayer target = findClosestTarget();

        if(target == null) {
            if(firstRun) {
                firstRun = false;

                if(notify.getValue(true)) {
                    Chatutil.ClientSideMessgage("Auto trap waiting for target");
                }
            }
            return;
        }

        if(findObsidianInHotbar() == -1) {

            if(notify.getValue(true)) {
                Chatutil.ClientSideMessgage("No obsidian found! toggling");
            }

            toggle();
            return;
        }

        if (firstRun) {
            firstRun = false;
            lastTickTargetName = target.getName();
            if (notify.getValue(true)) {
                Chatutil.ClientSideMessgage("Enabled next target " + lastTickTargetName);
            }
        }
        else if (!lastTickTargetName.equals(target.getName())) {
            lastTickTargetName = target.getName();
            offsetStep = 0;
            if (notify.getValue(true)) {
                Chatutil.ClientSideMessgage("New target found " + lastTickTargetName);
            }
        }

        if (toggle.getValue(true))
        {
            if (Entityutil.IsEntityTrapped(target)) {
                toggle();
                return;
            }
        }

        final List<Vec3d> placeTargets = new ArrayList<Vec3d>();

        if(mode.in("Normal")) {
            Collections.addAll(placeTargets, offsetsDefault);
        } if(mode.in("Feet")) {
            Collections.addAll(placeTargets, feetPlace);
        } if(mode.in("Face")) {
            Collections.addAll(placeTargets, facePlace);
        }

        int blocksPlaced = 0;

        while (blocksPlaced < this.blocksperTick.getValue(1)) {

            if (offsetStep >= placeTargets.size()) {
                offsetStep = 0;
                break;
            }

            final BlockPos offset_pos = new BlockPos((Vec3d) placeTargets.get(offsetStep));
            final BlockPos target_pos = new BlockPos(target.getPositionVector()).down().add(offset_pos.getX(), offset_pos.getY(), offset_pos.getZ());
            boolean shouldTryPlace = true;

            if (!mc.world.getBlockState(target_pos).getMaterial().isReplaceable())
                shouldTryPlace = false;

            for (final Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity((Entity) null, new AxisAlignedBB(target_pos))) {

                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                    shouldTryPlace = false;
                    break;
                }

            }

            if (shouldTryPlace && Blockutil.placeBlock(target_pos, findObsidianInHotbar(), rotate.getValue(true), rotate.getValue(true), arm)) {
                ++blocksPlaced;
            }

            offsetStep++;

        }

    }

    public EntityPlayer findClosestTarget()  {

        if (mc.world.playerEntities.isEmpty())
            return null;

        EntityPlayer closestTarget = null;

        for (final EntityPlayer target : mc.world.playerEntities) {
            if (target == mc.player)
                continue;

            if (FriendManager.isFriend(target.getName()))
                continue;

            if (!Entityutil.isLiving((Entity)target))
                continue;

            if (target.getHealth() <= 0.0f)
                continue;

            if (closestTarget != null)
                if (mc.player.getDistance(target) > mc.player.getDistance(closestTarget))
                    continue;

            closestTarget = target;
        }

        return closestTarget;
    }

    private int findObsidianInHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock) stack.getItem()).getBlock();

                if (block instanceof BlockEnderChest)
                    return i;

                else if (block instanceof BlockObsidian)
                    return i;

            }
        }
        return -1;
    }

    private Vec3d[] facePlace = new Vec3d[] {
            new Vec3d(0.0, 0.0, -1.0),
            new Vec3d(1.0, 0.0, 0.0),
            new Vec3d(0.0, 0.0, 1.0),
            new Vec3d(-1.0, 0.0, 0.0),
            new Vec3d(0.0, 1.0, -1.0),
            new Vec3d(1.0, 1.0, 0.0),
            new Vec3d(0.0, 1.0, 1.0),
            new Vec3d(-1.0, 1.0, 0.0),
            new Vec3d(0.0, 2.0, -1.0),
            new Vec3d(0.0, 3.0, -1.0),
            new Vec3d(0.0, 3.0, 1.0),
            new Vec3d(1.0, 3.0, 0.0),
            new Vec3d(-1.0, 3.0, 0.0),
            new Vec3d(0.0, 3.0, 0.0)
    };

    private Vec3d[] feetPlace = new Vec3d[] {
            new Vec3d(0.0, 0.0, -1.0),
            new Vec3d(1.0, 0.0, 0.0),
            new Vec3d(0.0, 0.0, 1.0),
            new Vec3d(-1.0, 0.0, 0.0),
            new Vec3d(0.0, 1.0, -1.0),
            new Vec3d(0.0, 2.0, -1.0),
            new Vec3d(1.0, 2.0, 0.0),
            new Vec3d(0.0, 2.0, 1.0),
            new Vec3d(-1.0, 2.0, 0.0),
            new Vec3d(0.0, 3.0, -1.0),
            new Vec3d(0.0, 3.0, 1.0),
            new Vec3d(1.0, 3.0, 0.0),
            new Vec3d(-1.0, 3.0, 0.0),
            new Vec3d(0.0, 3.0, 0.0)
    };

    private Vec3d[] offsetsDefault = new Vec3d[] {
            new Vec3d(0.0, 0.0, -1.0),
            new Vec3d(1.0, 0.0, 0.0),
            new Vec3d(0.0, 0.0, 1.0),
            new Vec3d(-1.0, 0.0, 0.0),
            new Vec3d(0.0, 1.0, -1.0),
            new Vec3d(1.0, 1.0, 0.0),
            new Vec3d(0.0, 1.0, 1.0),
            new Vec3d(-1.0, 1.0, 0.0),
            new Vec3d(0.0, 2.0, -1.0),
            new Vec3d(1.0, 2.0, 0.0),
            new Vec3d(0.0, 2.0, 1.0),
            new Vec3d(-1.0, 2.0, 0.0),
            new Vec3d(0.0, 3.0, -1.0),
            new Vec3d(0.0, 3.0, 0.0)
    };

}


