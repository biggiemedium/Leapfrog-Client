package dev.leap.frog.Manager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;

public class UtilManager {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static Minecraft getMc() {
        return mc;
    }

    public static World getWorld() {
        return mc.world;
    }

    public static EntityPlayerSP getPlayer() {
        return mc.player;
    }



}
