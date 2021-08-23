package dev.leap.frog.Util.Listeners;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;

public interface Util {

    Minecraft mc = Minecraft.getMinecraft();
    World world = mc.world;
    EntityPlayerSP player = mc.player;

}
