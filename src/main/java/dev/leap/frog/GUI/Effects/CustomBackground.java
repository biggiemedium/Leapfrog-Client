package dev.leap.frog.GUI.Effects;

import dev.leap.frog.Util.Listeners.Util;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class CustomBackground extends GuiScreen implements Util {

    private int x;
    private int y;
    private ResourceLocation background = new ResourceLocation("Shaders/HighQ.fsh");

    @Override
    public void initGui() {
        Util.mc.gameSettings.enableVsync = false;
        Util.mc.gameSettings.limitFramerate = 200;
        this.x = this.width / 4;
        this.y = this.height / 4 + 48;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Util.mc.getTextureManager().bindTexture(background);
    }
}
