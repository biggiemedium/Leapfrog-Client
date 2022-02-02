package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Render.EventRenderModel;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Block.Blockutil;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Math.Mathutil;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWeb;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
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

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class KillAura extends Module {

    /*
     * Check dev.leap.frog.Mixin.Render.MixinRenderLiving for rendering
     */

    public KillAura() {
        super("KillAura", "Attacks entites for you", Type.COMBAT);
        INSTANCE = this;
    }

    public static KillAura INSTANCE = new KillAura();

    public Setting<Integer> distance = create("Distance", 5, 0, 10);
    public Setting<Boolean> rotate = create("Rotate", false);
    public Setting<Boolean> renderTarget = create("Render target", true);

    public Setting<Boolean> ignoreFriends = create("Ignore friends", true);

    public Setting<Boolean> walls = create("Walls", true);

    private EntityPlayer target;
    private boolean attacking = false;

    private float yaw, pitch;
    private boolean rotating = false;

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck() || mc.player.isDead) return;
        target = findTarget(distance.getValue());
        if(target == null) return;
        if(!walls.getValue() && !Entityutil.canSeePlayer(target)) return;

        if(rotate.getValue() && mc.player.getDistance(target) < distance.getValue()) { // mc.player.getDistance(targets) > range) continue;
            double[] angle = Playerutil.calculateLookAt(target.posX, target.posY, target.posZ, mc.player);
            this.yaw = (float) angle[0];
            this.pitch = (float) angle[1];
            this.rotating = true;
        }

        rotation();
        attack(target);
    }

    @Override
    public void onRender(RenderEvent event) {
        if(renderTarget.getValue()) {
            renderNormal2(distance.getValue());
        }
    }

    @Override
    public void onDisable() {
        this.yaw = mc.player.rotationYaw;
        this.pitch = mc.player.rotationPitch;
        this.rotating = false;
    }

    @EventHandler
    private Listener<EventPacket.SendPacket> listener = new Listener<>(event -> {
        if(event.getPacket() instanceof CPacketPlayer && rotating && rotate.getValue()) {
            CPacketPlayer packet = (CPacketPlayer) event.getPacket();

            packet.yaw = this.yaw;
            packet.pitch = this.pitch;
        }
    });

    private void attack(Entity target) {
        if(mc.player.getCooledAttackStrength(0.0f) >= 1.0f) {
            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(getHand());

            attacking = true;
        }
    }

    private void renderNormal2(float n) {
        for (final Entity entity : mc.world.loadedEntityList) {
            if (entity != mc.getRenderViewEntity()) {
                if (!(entity instanceof EntityPlayer)) {
                    continue;
                }
                if(target == null) continue;
                if(!(entity == target)) continue;
                Render entityRenderObject = mc.getRenderManager().getEntityRenderObject(entity);
                RenderLivingBase renderer = (entityRenderObject instanceof RenderLivingBase) ? (RenderLivingBase) entityRenderObject : null;
                double n2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
                double n3 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
                double n4 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
                if (renderer == null) {
                    continue;
                }
                renderer.doRender((EntityLivingBase)entity, n2 - mc.renderManager.renderPosX, n3 - mc.renderManager.renderPosY, n4 - mc.renderManager.renderPosZ, entity.rotationYaw, mc.getRenderPartialTicks());
            }
        }
    }


    private void rotation() {
        if(!rotate.getValue()) {
            this.rotating = false;
        }

        if(rotate.getValue() && mc.player.getDistance(target) > distance.getValue()) {
            this.rotating = false;
        }
    }

    private EnumHand getHand() {
        if(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
            return EnumHand.MAIN_HAND;
        }

        if(mc.player.getHeldItemOffhand().getItem() instanceof ItemSword && !(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
            return EnumHand.OFF_HAND;
        }

        return EnumHand.MAIN_HAND;
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
            if(ignoreFriends.getValue() && FriendManager.isFriend(targets.getName())) continue;
            if(mc.player.getDistance(targets) > range) continue; // check

            player = targets;
        }

        return player;
    }

    public boolean entityCheck(Entity entity) {
        return entity instanceof EntityPlayer && mc.player.getDistance(entity) < this.distance.getValue();
    }

    public EntityPlayer getTarget() {
        return this.target;
    }

    public EntityLivingBase getTargetLB() {
        return this.target;
    }

    @Override
    public String getArrayDetails() {
        return target == null ? "No Target" : target.getName();
    }
}
