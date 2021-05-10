package dev.leap.frog.Util.Entity;

import dev.leap.frog.Util.Mathutil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Entityutil {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isAnimal(Entity entity) {
        if (entity instanceof EntityAgeable || entity instanceof EntityAmbientCreature
                || entity instanceof EntityWaterMob || entity instanceof EntityGolem)
            return true;
        return false;
    }

    public static boolean isLiving(Entity entity) {
        return entity instanceof EntityLivingBase;
    }

    public static boolean isFakeLocalPlayer(Entity entity) {
        return entity != null && entity.getEntityId() == -100 && Minecraft.getMinecraft().player != entity;
    }

    public static boolean isPlayer(Entity entity) {
        return entity instanceof EntityPlayer;
    }

    public static Vec3d getInterpolatedPos(Entity entity, float partialTicks) {
        return Mathutil.interpolateVec3d(entity.getPositionVector(),
                new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ), partialTicks);
    }

    public static BlockPos canCity(final EntityPlayer player, final boolean end_crystal) {

        BlockPos pos = new BlockPos(player.posX, player.posY, player.posZ);

        if (mc.world.getBlockState(pos.north()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.north();
            }
            else if (mc.world.getBlockState(pos.north().north()).getBlock() == Blocks.AIR) {
                return pos.north();
            }
        }
        if (mc.world.getBlockState(pos.east()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.east();
            }
            else if (mc.world.getBlockState(pos.east().east()).getBlock() == Blocks.AIR) {
                return pos.east();
            }
        }
        if (mc.world.getBlockState(pos.south()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.south();
            }
            else if (mc.world.getBlockState(pos.south().south()).getBlock() == Blocks.AIR) {
                return pos.south();
            }
        }
        if (mc.world.getBlockState(pos.west()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.west();
            }
            else if (mc.world.getBlockState(pos.west().west()).getBlock() == Blocks.AIR) {
                return pos.west();
            }
        }
        return null;
    }



}
