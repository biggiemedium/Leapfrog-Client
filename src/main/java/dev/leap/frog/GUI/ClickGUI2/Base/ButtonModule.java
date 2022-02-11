package dev.leap.frog.GUI.ClickGUI2.Base;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Listeners.IComponent;
import dev.leap.frog.Util.Listeners.Util;
import dev.leap.frog.Util.Render.Renderutil;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class ButtonModule implements Util, IComponent {

    private int x;
    private int y;
    private int width;
    private int height;

    private boolean open;

    private GUIFrame frame;
    private Module module;

    public ButtonModule(GUIFrame frame, int x, int y, Module module, int offset) {
        this.frame = frame;
        this.x = x;
        this.y = y + offset;
        this.width = frame.getWidth();
        this.height = frame.getHeight();
        this.open = false;
        this.module = module;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Renderutil.drawRect(x, y + height + 1, x + width, y + height * 2 + 1, 0xFF212121);

        mc.fontRenderer.drawStringWithShadow(module.getName(), x + 2, y + height + 4, -1);
        mc.fontRenderer.drawStringWithShadow("...", x + width - 8, y + height + 4, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {

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

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public GUIFrame getFrame() {
        return frame;
    }

    public void setFrame(GUIFrame frame) {
        this.frame = frame;
    }
}
