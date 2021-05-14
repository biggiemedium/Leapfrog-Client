package dev.leap.frog.Manager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;

public class UtilManager {

    private static Minecraft mc = Minecraft.getMinecraft();

    public Minecraft getMc() {
        return mc;
    }

    public World getWorld() {
        return mc.world;
    }

    public EntityPlayerSP getPlayer() {
        return mc.player;
    }



}
