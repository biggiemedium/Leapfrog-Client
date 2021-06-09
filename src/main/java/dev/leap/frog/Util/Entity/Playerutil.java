package dev.leap.frog.Util.Entity;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Playerutil extends UtilManager {

    public static float getSpeedInKM() {
        final double deltaX = Minecraft.getMinecraft().player.posX - Minecraft.getMinecraft().player.prevPosX;
        final double deltaZ = Minecraft.getMinecraft().player.posZ - Minecraft.getMinecraft().player.prevPosZ;

        float distance = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        double floor = Math.floor(( distance/1000.0f ) / ( 0.05f/3600.0f ));

        String formatter = String.valueOf(floor);

        if (!formatter.contains("."))
            formatter += ".0";

        return Float.valueOf(formatter);
    }

    public static boolean isMoving(EntityLivingBase entity) {
        return entity.moveForward != 0 || entity.moveStrafing != 0;
    }

    public static float getPlayerHealth() {
        return Wrapper.GetMC().player.getHealth() + Wrapper.GetMC().player.getAbsorptionAmount();
    }

    public static BlockPos GetLocalPlayerPosFloored() {
        return new BlockPos(Math.floor(Wrapper.GetMC().player.posX), Math.floor(Wrapper.GetMC().player.posY), Math.floor(Wrapper.GetMC().player.posZ));
    }

}
