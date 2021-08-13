package dev.leap.frog.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;

public class Wrapper {

    public static Minecraft mc = Minecraft.getMinecraft();

    public static Minecraft getMC() {
        return mc;
    }

    public static EntityPlayerSP getPlayer() {
        return mc.player;
    }

    public static World getWorld() {
        return mc.world;
    }

    public static boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }
}
