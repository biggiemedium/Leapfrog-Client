package dev.leap.frog.GUI.ClickGUI;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Module.ui.ClickGUIModule;
import dev.leap.frog.Util.Render.Colorutil;
import dev.leap.frog.Util.Render.Renderutil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Frame {

    private ArrayList<ModuleButton> moduleButton;

    int x;
    int y;
    int width;
    int height;
    boolean dragging;
    public int plusX;
    public int plusY;
    private Module.Type type;
    private boolean open;
    private String name;

    public Frame(Module.Type type, int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 13;
        this.type = type;
        this.open = false;
        this.name = type.getName();

        moduleButton = new ArrayList<>();
        int offsetY = height; // this is to ensure that the mod buttons dont start rendering over frame

        for(Module m : LeapFrog.getModuleManager().getModuleByType(type)) {
            if(this.type == m.type) {
                ModuleButton button = new ModuleButton(m, this.x, this.y, this, offsetY); //17 + offsetY * 14
                moduleButton.add(button);
                offsetY += 13;
            }
            //offsetY++;
        }
    }

    public int getPlusX() {
        return plusX;
    }
    public int getPlusY(){
        return plusY;
    }
    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void render(int mouseX, int mouseY) {
        int c = new Color(29, 37, 48, 255).getRGB();
        int valueChange = new Color(ClickGUIModule.INSTANCE.red.getValue(), ClickGUIModule.INSTANCE.green.getValue(), ClickGUIModule.INSTANCE.blue.getValue(), ClickGUIModule.INSTANCE.alpha.getValue()).getRGB();

        if (LeapFrog.getModuleManager().getModule(ClickGUIModule.class).isToggled() && ClickGUIModule.INSTANCE.GUIColors.getValue() && !ClickGUIModule.INSTANCE.rainbow.getValue()) {
            Renderutil.drawRect(x - 2, y - 2, x + width + 2, y + 13 - 1, valueChange);
        } else if (ClickGUIModule.INSTANCE.rainbow.getValue() && LeapFrog.getModuleManager().getModule(ClickGUIModule.class).isToggled() && ClickGUIModule.INSTANCE.GUIColors.getValue()) {
            int counter[] = {1};
            Renderutil.drawRect(x - 2, y - 2, x + width + 2, y + 13 - 1, Colorutil.Rainbow(counter[0] * 300));
        } else if (!LeapFrog.getModuleManager().getModule(ClickGUIModule.class).isToggled()) {
            Renderutil.drawRect(x - 2, y - 2, x + width + 2, y + 13 - 1, c);
        }

        Renderutil.drawRect(x, y + 13 - 1, x + width, y + 13, new Color(10, 10, 10, 200).getRGB());
        Wrapper.getMC().fontRenderer.drawString(type.getName(), x + 2, y + 2, new Color(255,255, 255).getRGB());

        if(dragging){
            this.x = mouseX - plusX;
            this.y = mouseY - plusY;
        }

        if(!open) {
            return;
        }

        int offset = 0;
        for(ModuleButton moduleButton : moduleButton) {
            if(moduleButton.getX() != x) {
                moduleButton.setX(x);
            }
            if(moduleButton.getY() != y + height + offset) {
                moduleButton.setY(y + height + offset);
            }
            moduleButton.draw(mouseX, mouseY);
            offset += moduleButton.getHeight();
        }
    }

    public void onClick(int mouseX, int mouseY, int button) throws IOException {
        if(button == 0) {
            if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + 13){
                dragging = true;
                plusX = mouseX - this.x;
                plusY = mouseY - this.y;
            }
        }

        if(button == 1) {
            if(mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + 13) {
                if(!open) {
                    open = true;
                } else if(open) {
                    open = false;
                }
            }
        }

        if(!open) return;
        for(ModuleButton m : moduleButton){
            m.mouseClick(mouseX, mouseY, button);
        }
    }
    public void onMouseReleased(int x, int y, int button){
        for(ModuleButton moduleButton : moduleButton) {
            moduleButton.MouseReleased(x, y, button);
        }
        dragging = false;
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        for(ModuleButton b : moduleButton) {
            b.keyTyped(typedChar, keyCode);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }
}
