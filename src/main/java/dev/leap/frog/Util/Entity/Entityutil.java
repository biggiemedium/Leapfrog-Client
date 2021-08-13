package dev.leap.frog.Util.Entity;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Util.Math.Mathutil;
import net.minecraft.block.state.IBlockState;
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

    public static boolean isPlayer(Entity entity) {
        return entity instanceof EntityPlayer;
    }

    public static BlockPos EntityPosToFloorBlockPos(Entity e) {
        return new BlockPos(Math.floor(e.posX), Math.floor(e.posY), Math.floor(e.posZ));
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
