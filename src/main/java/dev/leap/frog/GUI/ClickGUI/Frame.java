package dev.leap.frog.GUI.ClickGUI;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Frame {

    private int x;
    private int y;
    private int width;
    private int height;
    private int barHeight;
    private Module.Type type;

    //private boolean open = false;
    private boolean dragging = false;
    private ArrayList<ModuleButton> moduleButtons;

    private int dragX;
    private int dragY;

    private int gap;

    public Frame(int x, int y, Module.Type type) {
        this.x = x;
        this.y = y;
        this.barHeight = Wrapper.getMC().fontRenderer.FONT_HEIGHT + 4;
        this.width = 100;
        this.height = 100;
        this.type = type; // im actually autistic and didn't initalize this - px
        this.moduleButtons = new ArrayList<>();
    }


    public void draw(int mouseX, int mouseY) {
        int c = new Color(29, 37, 48, 255).getRGB();
        int leapColor = new Color(10, 200, 100, 255).getRGB();

        Gui.drawRect(x - 2, y - 2, x + width + 2, y + barHeight - 1, c);
        Gui.drawRect(x, y + barHeight - 1, x + width, y + barHeight, new Color(10, 10, 10, 200).getRGB());
        Wrapper.getMC().fontRenderer.drawString(type.getName(), x + 2, y + 2, new Color(255,255, 255).getRGB());

        if(this.dragging) { // old method ty boncorde
            this.x = mouseX - dragX;
            this.y = mouseY - dragY;
        }

        for(ModuleButton b : moduleButtons) {

        }
    }


    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if (button == 0) {
            if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + 20) {
                dragging = true;
                dragY = mouseY - this.y;
                dragX = mouseX - this.x;
            }
        }

        for(ModuleButton b : moduleButtons) {

        }
    }


    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;
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

    public int getGap() {
        return gap;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBarHeight() {
        return barHeight;
    }

    public void setBarHeight(int barHeight) {
        this.barHeight = barHeight;
    }

    public Module.Type getType() {
        return type;
    }

    public void setType(Module.Type type) {
        this.type = type;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public int getDragX() {
        return dragX;
    }

    public void setDragX(int dragX) {
        this.dragX = dragX;
    }

    public int getDragY() {
        return dragY;
    }

    public void setDragY(int dragY) {
        this.dragY = dragY;
    }
}
