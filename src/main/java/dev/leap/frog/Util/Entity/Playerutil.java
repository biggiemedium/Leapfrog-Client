package dev.leap.frog.Util.Entity;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

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
        return Wrapper.getMC().player.getHealth() + Wrapper.getMC().player.getAbsorptionAmount();
    }

    public static Vec3d getPlayerPosVec3d() {
        return new Vec3d(mc.player.posX, mc.player.posY, mc.player.posZ);
    }

    public static BlockPos getPlayerPosFloored() {
        return new BlockPos(Math.floor(Wrapper.getMC().player.posX), Math.floor(Wrapper.getMC().player.posY), Math.floor(Wrapper.getMC().player.posZ));
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Wrapper.getMC().player.posX, Wrapper.getMC().player.posY, Wrapper.getMC().player.posZ);
    }

    public static boolean isEating() {
        return mc.player.getHeldItemOffhand().getItem() instanceof ItemFood && mc.player.isHandActive();
    }

    public static int getItem(Item item) {
        int slot = -1;
        for(int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getItemStack();
            if(stack.isEmpty()) {
                continue;
            }
            Item f = stack.getItem();
            if(f.equals(item)) {
                slot = i;
                break;
            }

        }
        return slot;
    }

    public static Vec3d[] offsetsDefault = new Vec3d[] {
                    new Vec3d(0.0, 0.0, -1.0),
                    new Vec3d(1.0, 0.0, 0.0),
                    new Vec3d(0.0, 0.0, 1.0),
                    new Vec3d(-1.0, 0.0, 0.0),
                    new Vec3d(0.0, 1.0, -1.0),
                    new Vec3d(1.0, 1.0, 0.0),
                    new Vec3d(0.0, 1.0, 1.0),
                    new Vec3d(-1.0, 1.0, 0.0),
                    new Vec3d(0.0, 2.0, -1.0),
                    new Vec3d(1.0, 2.0, 0.0),
                    new Vec3d(0.0, 2.0, 1.0),
                    new Vec3d(-1.0, 2.0, 0.0),
                    new Vec3d(0.0, 3.0, -1.0),
                    new Vec3d(0.0, 3.0, 0.0)
            };

    public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer player) {
        double dirx = player.posX - px;
        double diry = player.posY - py;
        double dirz = player.posZ - pz;

        double len = Math.sqrt(dirx*dirx + diry*diry + dirz*dirz);

        dirx /= len;
        diry /= len;
        dirz /= len;

        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);

        //to degree
        pitch = pitch * 180.0d / Math.PI;
        yaw = yaw * 180.0d / Math.PI;

        yaw += 90f;

        return new double[]{yaw,pitch};
    }

    public static boolean isHoldingItem(Item item) {
        return mc.player.getHeldItemMainhand().getItem() == item || mc.player.getHeldItemOffhand().getItem() == item;
    }

    public static EnumHand getHand() {
        return mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
    }

    public static boolean isInputKeysPressed() {
        return mc.gameSettings.keyBindForward.isPressed() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown();
    }

    public static double getBaseMotionSpeed() {
        double baseSpeed = 0.2873D;
        if (mc.player.isPotionActive(MobEffects.SPEED)) {
            int amplifier = mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            baseSpeed *= 1.0D + 0.2D * ((double) amplifier + 1);
        }
        return baseSpeed;
    }
}
