package dev.leap.frog.Util;

import dev.leap.frog.Manager.UtilManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Mathutil extends UtilManager {

    public static float[] getMovement(float speed, float yaw){
        float moveX = 0;
        float moveZ = 0;
        float timesby;
        if(yaw >= 270 && yaw <= 360) {
            moveZ = ((yaw - 270) * (speed / 90));
            moveX =  speed - moveZ;
        }else if(yaw > 360 && yaw <= 90){
            moveZ = ((yaw) * (speed / 90));
            moveX = -1*(speed - moveZ);
        }


        return new float[]{moveX, moveZ};
    }
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

    public static Vec3d process(Entity entity, double x, double y, double z) {
        return new Vec3d(
                (entity.posX - entity.lastTickPosX) * x,
                (entity.posY - entity.lastTickPosY) * y,
                (entity.posZ - entity.lastTickPosZ) * z);
    }

    public static Vec3d getInterpolatedPos(Entity entity, double ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(process(entity, ticks, ticks, ticks)); // x, y, z.
    }

}
