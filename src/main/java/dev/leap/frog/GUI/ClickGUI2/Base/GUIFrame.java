package dev.leap.frog.GUI.ClickGUI2.Base;

import dev.leap.frog.GUI.ClickGUI.ModuleButton;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Listeners.IComponent;
import dev.leap.frog.Util.Listeners.Util;
import dev.leap.frog.Util.Render.Colorutil;
import dev.leap.frog.Util.Render.Renderutil;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;

public class GUIFrame implements Util, IComponent {

    private String name;

    private int x;
    private int y;

    private int width;
    private int height;

    private int mouseX;
    private int mouseY;

    private boolean open;
    private boolean dragging;

    private Module.Type type;
    private ArrayList<ButtonModule> buttonModules;

    public GUIFrame(Module.Type type, int x, int y) {
        this.type = type;
        this.width = 100;
        this.height = 13;
        this.x = x;
        this.y = y;
        this.name = type.getName();
        this.buttonModules = new ArrayList<>();
        this.dragging = false;
        this.open = false;
        init();
    }

    private void init() {
        int offset = 0;
        for(Module m : LeapFrog.getModuleManager().getModuleByType(type)) {
            ButtonModule buttonModule = new ButtonModule(this, x, y, m, offset);
            buttonModules.add(buttonModule);
            offset += 13;
        }
    }

    @Override
    public void draw(int mouseX, int mouseY) {

        Renderutil.drawRect(x, y, x + width, y + height, Colorutil.getFrameColor2());
        Renderutil.drawRect(x, y + height, (x + width), y + height + 1, Colorutil.getToggledColor());

        mc.fontRenderer.drawStringWithShadow(this.name, (x + ((x + width) - x) / 2 - mc.fontRenderer.getStringWidth(name) / 2), y + 3, -1);

        if(dragging) {
            this.x = mouseX - this.mouseX;
            this.y = mouseY - this.mouseY;
        }
        //if(!open) return;
        initModuleButtons(mouseX, mouseY);
    }

    private void initModuleButtons(int mouseX, int mouseY) {
        for(ButtonModule m : this.buttonModules) {
            m.draw(mouseX, mouseY);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {

        if(button == 1) {
            if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + height) {
                if (!open) {
                    open = true;
                } else if (open) {
                    open = false;
                }
            }
        }

        if(!open) return;

        for(ButtonModule m : this.buttonModules) {
            m.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {

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

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public Module.Type getType() {
        return type;
    }

    public void setType(Module.Type type) {
        this.type = type;
    }

    public ArrayList<ButtonModule> getButtonModules() {
        return buttonModules;
    }

    public void setButtonModules(ArrayList<ButtonModule> buttonModules) {
        this.buttonModules = buttonModules;
    }
}
