package dev.leap.frog.GUI.ClickGUI;

import dev.leap.frog.GUI.ClickGUI.Components.BooleanButton;
import dev.leap.frog.GUI.ClickGUI.Components.Component;
import dev.leap.frog.GUI.ClickGUI.Components.HiddenButton;
import dev.leap.frog.GUI.ClickGUI.Components.KeybindButton;
import dev.leap.frog.GUI.ClickGUI.Components.Types.FloatSlider;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Module.ui.ClickGUIModule;
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
    private int offset;

    public ModuleButton(Module module, int x, int y,Frame frame, int offset) {
        this.module = module;
        this.x = x;
        this.y = y + 2 + offset;
        this.frame = frame;
        this.width = frame.width;
        this.height = 13;
        this.offset = offset;
        this.opened = false;
        this.components = new ArrayList<>();

        int offsetY = height + 1;
        int heightOffset = 0;
        if(LeapFrog.getSettingManager().getSettingsForMod(module) != null && !LeapFrog.getSettingManager().getSettingsForMod(module).isEmpty()) {
        for(Setting s : LeapFrog.getSettingManager().getSettingsForMod(module)) {
            if (s.getValue() instanceof Boolean) {
                this.components.add(new BooleanButton(s, x, y + height + offsetY, this));
                //c = new BooleanButton(s, x, y + height + offsetY, this);
                offsetY += 13;
                continue;
            }
            if (s.getValue() instanceof Integer) {

            }
            if (s.getValue() instanceof Float) {
                this.components.add(new FloatSlider(s, x, y + offsetY, this));
                offsetY += 13;
                continue;
            }
            if (s.getValue() instanceof Double) {

            }
            if (s.getValue() instanceof Enum || s.getValue() instanceof ArrayList) {

                }
            }
        }
        this.components.add(new HiddenButton(x, y + height + offsetY, this));
        if(opened) {
            this.y += offset;
        }
        //this.components.add(new KeybindButton(x, y + height + offsetY, this));
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
        Gui.drawRect(x , y, x + width, y + 12, getColor(mouseX, mouseY));
        Gui.drawRect(x, y + 12, x + width, y + 13, new Color(10, 10, 10, 200).getRGB());
        Wrapper.getMC().fontRenderer.drawStringWithShadow(module.getName(), x + 2, y + 2, -1);
        if(components.size() > 1) { // checking for hidden button
            Wrapper.getMC().fontRenderer.drawStringWithShadow(opened ? "v" : "^", getX() + this.getWidth() - 5, getY() + 5, -1);
        }
        height = Wrapper.getMC().fontRenderer.FONT_HEIGHT + 4;

        if(ClickGUIModule.INSTANCE.descriptionn.getValue() && isHovered(mouseX, mouseY)) {
            Wrapper.getMC().fontRenderer.drawStringWithShadow(module.getDescription(), 1, 1, -1);
        }

        if (frame.dragging) {
            setX(mouseX - frame.getPlusX());
            setY(mouseY - frame.getPlusY() + offset + 2);
        }
        height = 13;
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

    public void mouseClick(int mouseX, int mouseY, int button) throws IOException {
        if(mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + 13){
            if(button == 0){
                module.toggle();
            } else if(button == 1){
                opened = !opened;
            }
            return;
        }

        if(opened){
            for(Component b : components){
                b.mouseClicked(mouseX, mouseY, button);
            }
        }
    }


    public void MouseReleased(int mx, int my, int button){
    }
}