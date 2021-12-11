package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Block.Blockutil;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Math.Mathutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWeb;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Collections;

public class KillAura extends Module {

    public KillAura() {
        super("KillAura", "Attacks entites for you", Type.COMBAT);
        INSTANCE = this;
    }

    public static KillAura INSTANCE = new KillAura();

    Setting<Integer> distance = create("Distance", 5, 0, 10);
    Setting<Boolean> delay = create("Delay", true); // vanilla hit delay
    Setting<Boolean> rotate = create("Rotate", true);
    Setting<Boolean> smartCheck = create("Smart check", true);
    Setting<Boolean> switchSlot = create("Switch slot", true);

    Setting<Boolean> renderTarget = create("Render target", true);

    Setting<Boolean> ignoreFriends = create("Ignore friends", true);

    Setting<Boolean> webTarget = create("Web target", false);
    Setting<Boolean> walls = create("Walls", true);

    private EntityPlayer target;
    private boolean sneaking;
    private boolean placing = false;
    private boolean attacking = false;
    private boolean rotating = false;
    private double yaw;
    private double pitch;
    private ArrayList<Vec3d> placeList;

    @Override
    public void onEnable() {
        this.sneaking = false;
        this.placeList = new ArrayList<>();
    }

    @Override
    public void onDisable() {
        reset();
    }

    @Override
    public void onUpdate() {

        if(mc.player == null || mc.player.isDead || mc.world.loadedEntityList.isEmpty() || LeapFrog.getModuleManager().getModule(AutoWeb.class).isToggled()) return;
        target = findTarget(distance.getValue());
        if(target == null || placeList == null || findWebsInHotbar() == -1) return;
        Collections.addAll(placeList, Mathutil.feet);
        if(switchSlot.getValue() && mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && !placing && attacking) {
            swapItems();
        }

        if(webTarget.getValue() && !target.isInWeb && Entityutil.isPlayerInHole(target)) {
            placing = true;
            attacking = false;
        } else {
            placing = false;
        }

        if(placing) {
            placeBlock(target.getPosition());
            this.attacking = false;
        }

        if(!Entityutil.canEntityFeetBeSeen(target)) {
            attacking = false;
        }

        if(attacking) {
            attack(target);
        }
    }

    @Override
    public void onRender(RenderEvent event) {
        if(renderTarget.getValue()) {
            if(target != null) {

            }
        }
    }


    @EventHandler
    private Listener<EventPacket.SendPacket> packetListener = new Listener<>(event -> {

        if(event.getPacket() instanceof CPacketPlayer.Rotation && rotating) {
            CPacketPlayer.Rotation packet = (CPacketPlayer.Rotation) event.getPacket();
            packet.pitch = (float) pitch;
            packet.pitch = (float) yaw;
        }

    });

    public EntityPlayer getTarget() {
        return target;
    }

    public void setTarget(EntityPlayer target) {
        this.target = target;
    }

    private void swapItems() {
        int slot = -1;
        for(int i = 0; i < 9; i++) {
            if(mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemSword) {
                slot = i;
                mc.player.inventory.currentItem = i;
                mc.playerController.updateController();
                break;
            }
        }
    }

    public EntityPlayer findTarget(int range) {
        EntityPlayer player = null;
        if(mc.world.playerEntities.isEmpty()) {
            return null;
        }

        for(EntityPlayer targets : mc.world.playerEntities) {
            if(targets == null) continue;
            if(targets == mc.player) continue;
            if(targets.isDead || !Entityutil.isLiving(targets)) continue;
            if(!ignoreFriends.getValue() && FriendManager.isFriend(targets.getName())) continue;
            if(mc.player.getDistance(targets) > range) continue; // check

            player = targets;
        }

        return player;
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

    private void attack(Entity entity) {
        if (mc.player.getCooledAttackStrength(0.0f) >= 1.0f) {
            this.attacking = true;
            if(rotate.getValue()) {
                lookAtPacket(entity.posX, entity.posY, entity.posZ, mc.player);
            }
            mc.playerController.attackEntity(mc.player, entity);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            this.attacking = false;
        }
    }

    private void reset() {
        if(rotating) {
            yaw = mc.player.rotationYaw;
            pitch = mc.player.rotationPitch;
            rotating = false;
        }
    }

    private void lookAtPacket(double x, double y, double z, EntityPlayer player) {
        final double[] v = Playerutil.calculateLookAt(x, y, z, player);
        this.yaw = (float) v[0];
        this.pitch = (float)v[1];
        this.rotating = true;
    }
}
