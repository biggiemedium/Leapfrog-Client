package dev.leap.frog.Util.Entity;

import dev.leap.frog.Manager.UtilManager;
import net.minecraft.client.Minecraft;
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


}
