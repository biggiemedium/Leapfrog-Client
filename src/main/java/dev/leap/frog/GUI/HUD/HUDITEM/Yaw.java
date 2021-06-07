package dev.leap.frog.GUI.HUD.HUDITEM;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class Yaw {

    @SubscribeEvent
    public void render(RenderGameOverlayEvent event){
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
            Minecraft mc = Minecraft.getMinecraft();
            mc.fontRenderer.drawString(String.valueOf(mc.player.rotationYawHead), 43, 56, Color.green.getRGB());
        }
    }
}
