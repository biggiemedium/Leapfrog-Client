package dev.leap.frog.GUI.ClickGUI;

import dev.leap.frog.GUI.ClickGUI.Components.BooleanButton;
import dev.leap.frog.GUI.ClickGUI.Components.Component;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Colorutil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.gui.Gui;


import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

public class ModuleButton {

    private Module module;
    private Frame frame;

    private int x;
    private int y;
    private int count;

    private boolean opened;
    private ArrayList<Component> components;

    private int width;
    private int height;
    private int Colour;
    private int offset;

    public ModuleButton(Module module, int x, int y,Frame frame, int offset) {
        this.module = module;
        this.x = x;
        this.y = y + 2 + offset;
        this.frame = frame;
        this.width = frame.width;
        this.height = Wrapper.getMC().fontRenderer.FONT_HEIGHT + 4;
        this.offset = offset;
        this.opened = false;
        this.components = new ArrayList<>();

        int offsetY = 0;
        int heightOffset = 0;
        for(Setting s : LeapFrog.getSettingManager().getSettingsForMod(module)) {
            Component c = null;
            if(s.getValue() instanceof Boolean) {
                c = new BooleanButton(s, x, y + height + offsetY, this);
                heightOffset += 12;
            }
            if(s.getValue() instanceof Integer) {

            }
            if(s.getValue() instanceof Float) {

            }
            if(s.getValue() instanceof Double) {

            }
            if(s.getValue() instanceof Enum || s.getValue() instanceof ArrayList) {

            }

            if(c != null) {
                components.add(c);
                offsetY += c.getHeight();
            }
        }
    }

    public Frame getFrame() {
        return frame;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public int getColour() {
        return Colour;
    }

    public void setColour(int colour) {
        Colour = colour;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Module getModule() {
        return module;
    }

    public void draw(int mouseX, int mouseY)  {
        if (module.isToggled()) {
            Colour = Colorutil.getToggledColor();
        } else {
            Colour = Colorutil.getOffColor();
        }
        Gui.drawRect(x, y, x + width, y + 12, getColor(mouseX, mouseY));
        Gui.drawRect(x, y + 12, x + width, y + 13, new Color(10, 10, 10, 200).getRGB());
        Wrapper.getMC().fontRenderer.drawStringWithShadow(module.getName(), x + 2, y + 2, -1);
        height = Wrapper.getMC().fontRenderer.FONT_HEIGHT + 4;

        if (frame.dragging) {
            setX(mouseX - frame.getPlusX());
            setY(mouseY - frame.getPlusY() + offset);
        }

        if(opened) {
            for(Component c : components) {
                if(c.getX() != x) {
                    c.setX(x);
                }
                if(c.getY() != y) {
                    c.setY(y + height);
                }
                c.draw(mouseX, mouseY);
                height += c.getHeight();
            }
        }
    }

    private int getColor(int mouseX, int mouseY){
        Color color = module.isToggled() ? Colorutil.getToggledC() : new Color(50, 50, 50, 200);
        return isHovered(mouseX, mouseY) ? (module.isToggled() ? color.darker().darker().getRGB() : color.brighter().brighter().getRGB()) : color.getRGB();
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseY > y && mouseX < x + width && mouseY < y + 13;
    }

    public void mouseClick(int x, int y, int button) throws IOException {
        if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {

            if (button == 0) {
                module.toggle();
            } else if (button == 1) {
                this.opened = !this.opened;
            }
        }

            if(opened) {
                for(Component c : components) {
                    c.mouseClicked(x, y, button);
                }
            }
    }


    public void MouseReleased(int mx, int my, int button){
    }
}