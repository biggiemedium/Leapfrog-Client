package dev.leap.frog.GUI.HUD.HUDITEM;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Collections;
import java.util.Comparator;

public class ArrayList {

    private Minecraft mc = Minecraft.getMinecraft();

    public static class ModuleCompare implements Comparator<Module> {

        @Override
        public int compare(Module o1, Module o2) {
            if(Minecraft.getMinecraft().fontRenderer.getStringWidth(o1.getName()) > Minecraft.getMinecraft().fontRenderer.getStringWidth(o2.getName())) {
                return -1;
            }
            if(Minecraft.getMinecraft().fontRenderer.getStringWidth(o1.getName()) > Minecraft.getMinecraft().fontRenderer.getStringWidth(o2.getName())) {
                return 1;
            }

            return 0;
        }
    }

    @SubscribeEvent
    public void DrawTopRight(RenderGameOverlayEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        ScaledResolution sc = new ScaledResolution(mc);
        final int[] counter = {1};
        Collections.sort(LeapFrog.getModuleManager().modules, new ModuleCompare());
        FontRenderer fr = mc.fontRenderer;

        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            int y = 2;
            for (Module m : LeapFrog.getModuleManager().getModules()) {
                if (!m.getName().equals("") && m.isToggled()) {
                    fr.drawStringWithShadow(m.getName(), sc.getScaledWidth() - fr.getStringWidth(m.getName()) - 2, y, Rainbow(counter[0] * 300));
                    counter[0]++;
                    y += fr.FONT_HEIGHT;
                }
            }
        }
    }

    public static int Rainbow(int delay) {
        double RainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        RainbowState %= 360;
        return Color.getHSBColor((float) (RainbowState / 360.0f), 0.5f, 1.0f).getRGB();
    }

}
