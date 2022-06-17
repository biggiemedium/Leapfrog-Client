package dev.leap.frog.Util.Entity;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Util.Math.Mathutil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

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

    public static BlockPos getPositionEntity(Entity e) {
        return new BlockPos(Math.floor(e.posX), Math.floor(e.posY), Math.floor(e.posZ));
    }

    public static boolean canEntityFeetBeSeen(final Entity entityIn) {
        return mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posX + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(entityIn.posX, entityIn.posY, entityIn.posZ), false, true, false) == null;
    }


    public static boolean isPlayerInHole(Entity e) {
        BlockPos pos = getPositionEntity(e);

        return mc.world.getBlockState(pos.east()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(pos.west()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(pos.north()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(pos.south()).getBlock() != Blocks.AIR;
    }

    public static Float getDistanceToPlayer(EntityPlayer entityPlayer) {
        return mc.player.getDistance(entityPlayer);
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

    public static Vec3d getInterpolateOffset(Entity entity){
        double d1 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)mc.getRenderPartialTicks();
        double d2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)mc.getRenderPartialTicks();
        double d3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)mc.getRenderPartialTicks();
        return new Vec3d(-d1, -d2, -d3);
    }

    public static boolean IsEntityTrapped(Entity e) {
        BlockPos playerlayerPos = EntityPosToFloorBlockPos(e);
        BlockPos[] trapPos = {
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

        for (BlockPos pos : trapPos) {
            if (mc.world.getBlockState(pos).getBlock() != Blocks.OBSIDIAN && mc.world.getBlockState(pos).getBlock() != Blocks.BEDROCK)
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

    public static boolean canSeePlayer(EntityPlayer target) {
        return mc.player.canEntityBeSeen(target);
    }

    public static boolean canSeePlayer(Entity target) {
        return mc.player.canEntityBeSeen(target);
    }

    public static Entity findTarget(double range, boolean playersOnly) {
        for(Entity e : mc.world.loadedEntityList) {
            if(e == null) {
                continue;
            }
            if(e == mc.player) {
                continue;
            }
            if(playersOnly && !(e instanceof EntityPlayer)) {
                continue;
            }
            if(mc.player.getDistance(e) > range) {
                continue;
            }
            if(e.isDead) {
                continue;
            }

            return e;
        }
        return null;
    }

    public static boolean isDev(Entity entity) {
        if(entity instanceof EntityPlayer) {
            if(entity.getName().equalsIgnoreCase("PX__") || entity.getName().equalsIgnoreCase("Boncorde")) {
                return true;
            }
        }
        return false;
    }

    public static List<BlockPos> getSphere(BlockPos loc, float radius, int height, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> circleBlocks = new ArrayList<>();
        int locX = loc.getX();
        int locY = loc.getY();
        int locZ = loc.getZ();

        for (int x = locX - (int) radius; x <= locX + radius; x++) {
            for (int z = locZ - (int) radius; z <= locZ + radius; z++) {
                for (int y = (sphere ? locY - (int) radius : locY); y < (sphere ? locY + radius : locY + height); y++) {
                    double dist = (locX - x) * (locX - x) + (locZ - z) * (locZ - z) + (sphere ? (locY - y) * (locY - y) : 0);
                    if (dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleBlocks.add(l);
                    }
                }
            }
        }

        return circleBlocks;
    }

    public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float finalDamage = 1.0f;
        try {
            float doubleExplosionSize = 12.0F;
            double distancedSize = entity.getDistance(posX, posY, posZ) / (double) doubleExplosionSize;
            double blockDensity = entity.world.getBlockDensity(new Vec3d(posX, posY, posZ), entity.getEntityBoundingBox());
            double v = (1.0D - distancedSize) * blockDensity;
            float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) doubleExplosionSize + 1.0D));

            if (entity instanceof EntityLivingBase) {
                finalDamage = getBlastReduction((EntityLivingBase) entity, getDamageMultiplied(damage), new Explosion(mc.world, null, posX, posY, posZ, 6F, false, true));
            }
        } catch (NullPointerException ignored) {}

        return finalDamage;
    }

    public static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer) entity;
            DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) ep.getTotalArmorValue(), (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            int k;
            try {
                k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            }catch (NullPointerException e) {
                k = 0;
            }
            float f = MathHelper.clamp(k, 0.0F, 20.0F);
            damage *= 1.0F - f / 25.0F;

            if (entity.isPotionActive(Potion.getPotionById(11))) {
                damage = damage - (damage / 4);
            }
            damage = Math.max(damage, 0.0F);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }

    public static float getDamageMultiplied(float damage) {
        final int diff = mc.world.getDifficulty().getDifficultyId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
}
