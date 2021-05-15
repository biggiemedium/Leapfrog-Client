package dev.leap.frog.GUI.Items;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;

public class Frame {

    public ArrayList<ModuleButton> moduleButton;

    int x;
    int y;
    int width;
    int height;

    private Module.Type type;

    public Frame(Module.Type type, int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 180;
        this.type = type;

        moduleButton = new ArrayList<>();
        int offsetY = 0;

        for(Module m : LeapFrog.moduleManager().getModuleByType(type)) {
            if(!(m.getName() == "ClickGui"))
                moduleButton.add(new ModuleButton(m, this.x + 4, this.y + 17 + offsetY * 14, this));
            offsetY++;
        }
    }

    public void render(int mouseX, int mouseY) {
        Gui.drawRect(x, y, x + width, y + 20, new Color(35,225, 64).getRGB());
        Wrapper.GetMC().fontRenderer.drawString(type.getName(), x + 2, y + 2, new Color(255,255, 255).getRGB()); // Name for each frame
        Gui.drawRect(x, y, x + width, y + height, new Color(0, 0, 0, 100).getRGB());
        Gui.drawRect(x, y - 2, x + width, y + 1, new Color(12,255, 12).getRGB());
        // Rectangle drawn for each catogory
        if(mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + 20){
            if(dragging == true){
                this.x = mouseX - 10;
                this.y = mouseY - 5;
            }
        }
        for(ModuleButton moduleButton : moduleButton) {
            moduleButton.draw(mouseX, mouseY);
        }

    }
    boolean dragging;
    public void OnClick(int x, int y, int button){
        for(ModuleButton moduleButton : moduleButton) {
            moduleButton.OnClick(x, y, button, this.type);
        }
    }
    public void OnMouseReleased(int x, int y){
        for(ModuleButton moduleButton : moduleButton) {
            moduleButton.MouseReleased(x, y, this.type);
        }
    }




}
