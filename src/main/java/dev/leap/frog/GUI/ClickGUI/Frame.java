package dev.leap.frog.GUI.ClickGUI;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
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

    public Frame(Module.Type type, int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 13;
        this.type = type;

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

    public void render(int mouseX, int mouseY) {
        int c = new Color(29, 37, 48, 255).getRGB();

        Gui.drawRect(x - 2, y - 2, x + width + 2, y + 13 - 1, c);
        Gui.drawRect(x, y + 13 - 1, x + width, y + 13, new Color(10, 10, 10, 200).getRGB());
        Wrapper.getMC().fontRenderer.drawString(type.getName(), x + 2, y + 2, new Color(255,255, 255).getRGB());

        if(dragging){
            this.x = mouseX - plusX;
            this.y = mouseY - plusY;
        }
        for(ModuleButton moduleButton : moduleButton) {
            moduleButton.draw(mouseX, mouseY);
        }
    }

    public void onClick(int mouseX, int mouseY, int button) throws IOException {
        if(button == 0) {
            if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + 20){
                dragging = true;
                plusX = mouseX - this.x;
                plusY = mouseY - this.y;
            }
        }
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
}
