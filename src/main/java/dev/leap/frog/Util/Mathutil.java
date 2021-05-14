package dev.leap.frog.Util;

import dev.leap.frog.Manager.UtilManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Mathutil extends UtilManager {

    public static double degToRad(double deg)
    {
        return deg * (float) (Math.PI / 180.0f);
    }

    public static Vec3d direction(float yaw)
    {
        return new Vec3d(Math.cos(degToRad(yaw + 90f)), 0, Math.sin(degToRad(yaw + 90f)));
    }

    public static Vec3d interpolateVec3d(Vec3d current, Vec3d last, float partialTicks) {
        return current.subtract(last).scale(partialTicks).add(last);
    }

    public static double round(double abs_1) {
        if (2 < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal decimal = new BigDecimal(abs_1);

        decimal = decimal.setScale(2, RoundingMode.HALF_UP);

        return decimal.doubleValue();
    }


    public float[] getRotationsOfEntity(Entity e) {
        Minecraft mc = Minecraft.getMinecraft();
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.player.posX;
        double deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.player.posY + mc.player.getEyeHeight();
        double deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.player.posZ;
        double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ));
        float pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance) );

        if(deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));

        }else if(deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }
        return new float[] { yaw, pitch};
    }


}
