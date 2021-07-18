package dev.leap.frog.GUI.HUD.HUDITEM;

import dev.leap.frog.Util.Entity.Playerutil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class Speed {

    @SubscribeEvent
    public void render(RenderGameOverlayEvent event){
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
            Minecraft mc = Minecraft.getMinecraft();
            mc.fontRenderer.drawString(String.valueOf(Playerutil.getSpeedInKM()), 43, 46, Color.green.getRGB());
        }
    }

}
