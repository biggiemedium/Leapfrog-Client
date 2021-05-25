package dev.leap.frog.Util;

import dev.leap.frog.Manager.UtilManager;
import net.minecraft.util.math.Vec3d;

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

}
