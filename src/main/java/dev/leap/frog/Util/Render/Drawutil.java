package dev.leap.frog.Util.Render;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Util.Wrapper;
import dev.px.turok.Turok;
import dev.px.turok.draw.RenderHelp;
import dev.px.turok.task.Rect;
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

    public static void drawRect(Rect rect, int r, int g, int b, int a) {
        Gui.drawRect(rect.get_x(), rect.get_y(), rect.get_screen_width(), rect.get_screen_height(), new PXColor(r, g, b, a).hex());
    }

    public static void drawString(String string, int x, int y, int r, int g, int b, int a) {
        font_renderer.drawStringWithShadow(string, x, y, new PXColor(r, g, b, a).hex());
    }

    public void draw_string_gl(String string, int x, int y, int r, int g, int b) {
        Turok resize_gl = new Turok("Resize");

        resize_gl.resize(x, y, this.size);

        font_renderer.drawString(string, x, y, new PXColor(r, g, b).hex());

        resize_gl.resize(x, y, this.size, "end");

        GL11.glPushMatrix();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);

        GlStateManager.enableBlend();

        GL11.glPopMatrix();

        RenderHelp.release_gl();
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
        bufferbuilder.pos(x- Wrapper.GetMC().getRenderManager().viewerPosX,y-Wrapper.GetMC().getRenderManager().viewerPosY,z-Wrapper.GetMC().getRenderManager().viewerPosZ).endVertex();
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
