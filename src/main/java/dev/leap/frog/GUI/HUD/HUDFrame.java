package dev.leap.frog.GUI.HUD;

import dev.leap.frog.GUI.ClickGUI.Frame;
import dev.leap.frog.GUI.HUD.HUD;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Util.Render.Renderutil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

/**
 * @author Elementars/HeroCode
 *
 * Ive never wriiten a HUD before so I took the concept from XULU
 * TODO: rewrite HUD
 */

public class HUDFrame {

    private String name;

    private int x, x2;
    private int y, y2;

    private int width;
    private int height;

    private boolean dragging;
    private boolean pinned;
    private boolean visible;

    private HUD hud;

    public HUDFrame(String name, int x, int y, int width, int height, boolean pinned, HUD hud) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.hud = hud;
        this.pinned = pinned;
        this.dragging = false;
        exec();
    }

    public void exec() {}
    int changeAlpha(int origColor, int valueChanged) {
        origColor = origColor & 0x00ffffff;
        return (valueChanged << 24) | origColor;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.visible = LeapFrog.getModuleManager().getModuleName(this.name).isToggled();
        if (!this.visible)
            return;

        if (this.dragging) {
            x = x2 + mouseX;
            y = y2 + mouseY;
            ScaledResolution sr = new ScaledResolution(Wrapper.getMC());
            if (x < 0) x = 0;
            if (y < 0) y = 0;
            if (x > sr.getScaledWidth() - width) x = sr.getScaledWidth() - width;
            if (y > sr.getScaledHeight() - height) y = sr.getScaledHeight() - height;
        }

        // set x y method here

        if (this.dragging) {
            Renderutil.drawRect((int) x, (int) y, (int) (x + width), (int) (y + height), changeAlpha(Color.lightGray.getRGB(), 100));
            Renderutil.drawRect((int) (x + 4), (int) (y + 2), (int) (x + 4.3), (int) (y + height - 2), changeAlpha(Color.lightGray.getRGB(), 100));
        } else {
            Renderutil.drawRect((int) x, (int) y, (int) (x + width), (int) (y + height), changeAlpha(0xff121212, 100));
            Renderutil.drawRect((int) (x + 4), (int) (y + 2), (int) (x + 4.3), (int) (y + height - 2), changeAlpha(0xffaaaaaa, 100));
        }
        if (LeapFrog.getModuleManager().getModuleName(this.name) != null) {
            LeapFrog.getModuleManager().getModuleName(this.name).onRender();
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (!this.visible) {
            return;
        }
        if (state == 0) {
            this.dragging = false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public HUD getHud() {
        return hud;
    }

    public void setHud(HUD hud) {
        this.hud = hud;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

}
