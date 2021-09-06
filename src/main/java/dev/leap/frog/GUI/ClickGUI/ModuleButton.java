package dev.leap.frog.GUI.ClickGUI;


import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Render.Colorutil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;

public class ModuleButton {

    private int x;
    private int y;
    private int width;
    private int height;

    private boolean extended;

    private Module module;
    private Frame frame;

    public ModuleButton(int x, int y, Module module, Frame frame) {
        this.module = module;
        this.frame = frame;
        this.x = x;
        this.y = y;
        this.width = frame.getWidth();
        this.height = 13;
        this.extended = false;
    }

    public void draw(int mouseX, int mouseY) {
        Gui.drawRect(x, y, x + width, y + 12, getColor(mouseX, mouseY));
        Gui.drawRect(x, y + 12, x + width, y + 13, new Color(10, 10, 10, 200).getRGB());
        Wrapper.getMC().fontRenderer.drawStringWithShadow(module.getName(), x + 2, y + 2, -1);

        if(frame.isDragging()) {
            setX(mouseX - frame.getDragX());
            setY(mouseY - frame.getDragY());
        }
    }

    private int getColor(int mouseX, int mouseY){
        Color color = module.isToggled() ? Colorutil.getToggledC() : new Color(50, 50, 50, 200);
        return isHovered(mouseX, mouseY) ? (module.isToggled() ? color.darker().darker().getRGB() : color.brighter().brighter().getRGB()) : color.getRGB();
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseY > y && mouseX < x + width && mouseY < y + 13;
    }

    public void mouseClick(int mouseX, int mouseY, int button) throws IOException {

        if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + 13) {
            if (button == 0) {
                module.toggle();
            } else if (button == 1) {
                System.out.println("mouse pressed");
                extended = !extended;
            }
        }
    }

    public void mouseReleased() {

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

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }
}