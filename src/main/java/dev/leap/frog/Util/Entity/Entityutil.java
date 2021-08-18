package dev.leap.frog.Util.Entity;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Util.Math.Mathutil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Entityutil extends UtilManager {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static double getHealth(EntityPlayer entity) {
        return Math.floor(entity.getHealth() + entity.getAbsorptionAmount());
    }

    public static boolean isLiving(Entity entity) {
        return entity instanceof EntityLivingBase;
    }

    public static boolean isFakeLocalPlayer(Entity entity) {
        return entity != null && entity.getEntityId() == -100 && Minecraft.getMinecraft().player != entity;
    }

    public static float getPlayerHealth(EntityPlayer player) {
        return player.getHealth() + player.getAbsorptionAmount();
    }

    public static boolean isPlayer(Entity entity) {
        return entity instanceof EntityPlayer;
    }

    public static boolean isPassive(Entity entity) {

        if(entity instanceof EntityWolf && ((EntityWolf) entity).isAngry()) {
            return false;
        }


        if(entity instanceof EntityAnimal || entity instanceof EntityTameable || entity instanceof EntitySquid || entity instanceof EntityAgeable) {
            return true;
        }

        if(entity instanceof EntityIronGolem && ((EntityIronGolem) entity).getRevengeTarget() == null) {
            return true;
        }

        return false;
    }

    public static BlockPos EntityPosToFloorBlockPos(Entity e) {
        return new BlockPos(Math.floor(e.posX), Math.floor(e.posY), Math.floor(e.posZ));
    }

    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    public static boolean IsEntityTrapped(Entity e) {
        BlockPos playerlayerPos = EntityPosToFloorBlockPos(e);

        final BlockPos[] trapPos = {
                playerlayerPos.up().up(),
                playerlayerPos.north(),
                playerlayerPos.south(),
                playerlayerPos.east(),
                playerlayerPos.west(),
                playerlayerPos.north().up(),
                playerlayerPos.south().up(),
                playerlayerPos.east().up(),
                playerlayerPos.west().up(),
        };

        for (BlockPos l_Pos : trapPos) {
            IBlockState l_State = mc.world.getBlockState(l_Pos);

            if (l_State.getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(l_Pos).getBlock() != Blocks.BEDROCK)
                return false;
        }

        return true;
    }

    public static boolean isAnimal(Entity entity) {
        if (entity instanceof EntityAgeable || entity instanceof EntityAmbientCreature
                || entity instanceof EntityWaterMob || entity instanceof EntityGolem)
            return true;
        return false;
    }

}
