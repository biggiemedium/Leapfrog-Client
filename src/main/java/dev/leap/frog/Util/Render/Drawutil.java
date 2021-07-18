package dev.leap.frog.Util.Render;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Arrays;

public class Drawutil extends UtilManager {
    private static FontRenderer font_renderer = Minecraft.getMinecraft().fontRenderer;
    private static FontRenderer custom_font   = Minecraft.getMinecraft().fontRenderer;

    private float size;

    public Drawutil(float size) {
        this.size = size;
    }

    public static void drawRect(int x, int y, int w, int h, int r, int g, int b, int a) {
        Gui.drawRect(x, y, w, h, new PXColor(r, g, b, a).hex());
    }

    public static void drawRect(int x, int y, int w, int h, int r, int g, int b, int a, int size, String type) {
        if (Arrays.asList(type.split("-")).contains("up")) {
            drawRect(x, y, x + w, y + size, r, g, b, a);
        }

        if (Arrays.asList(type.split("-")).contains("down")) {
            drawRect(x, y + h - size, x + w, y + h, r, g, b, a);
        }

        if (Arrays.asList(type.split("-")).contains("left")) {
            drawRect(x, y, x + size, y + h, r, g, b, a);
        }

        if (Arrays.asList(type.split("-")).contains("right")) {
            drawRect(x + w - size, y, x + w, y + h, r, g, b, a);
        }
    }

    public static void drawRect(final float x, final float y, final float w, final float h, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(w, h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(w, y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(x, y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect(final float x, final float y, final float w, final float h, final float r, final float g, final float b, final float a) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, h, 0.0).color(r / 255, g / 255, b / 255, a).endVertex();
        bufferbuilder.pos(w, h, 0.0).color(r / 255, g / 255, b / 255, a).endVertex();
        bufferbuilder.pos(w, y, 0.0).color(r / 255, g / 255, b / 255, a).endVertex();
        bufferbuilder.pos(x, y, 0.0).color(r / 255, g / 255, b / 255, a).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public int getStringHeight() {
        FontRenderer fontRenderer = font_renderer;

        return (int) (fontRenderer.FONT_HEIGHT * this.size);
    }

    public int getStringWidth(String string) {
        FontRenderer fontRenderer = font_renderer;

        return (int) (fontRenderer.getStringWidth(string) * this.size);
    }

    public static void drawLine(double posx, double posy, double posz, double posx2, double posy2, double posz2, float red, float green, float blue, float alpha){
        GlStateManager.glLineWidth(1.0f);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GL11.glColor4f(red, green, blue, alpha);
        bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        vertex(posx,posy,posz,bufferbuilder);
        vertex(posx2,posy2,posz2,bufferbuilder);
        tessellator.draw();
    }

    private static void vertex (double x, double y, double z, BufferBuilder bufferbuilder) {
        bufferbuilder.pos(x- Wrapper.getMC().getRenderManager().viewerPosX,y-Wrapper.getMC().getRenderManager().viewerPosY,z-Wrapper.getMC().getRenderManager().viewerPosZ).endVertex();
    }

    public static class PXColor extends Color {
        public PXColor(int r, int g, int b, int a) {
            super(r, g, b, a);
        }

        public PXColor(int r, int g, int b) {
            super(r, g, b);
        }

        public int hex() {
            return getRGB();
        }
    }

}
