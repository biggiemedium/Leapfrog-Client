package dev.leap.frog.GUI.ClickGUI;

import dev.leap.frog.GUI.IComponent;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.ModuleManager;
import dev.leap.frog.Manager.SettingManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Colorutil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;

public class ModuleButton {

    private Module module;
    private Frame frame;

    private int x;
    private int y;
    private int count;
    private boolean Svisable;

    private int width;
    private int height;
    private int Colour;
    private int Offset;

    public ModuleButton(Module module, int x, int y,Frame frame, int offset) {
        this.module = module;
        this.x = x;
        this.y = y + 2 + offset;
        this.frame = frame;
        this.width = frame.width;
        this.height = 14;
        this.Offset = offset;

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

    public void draw(int mouseX, int mouseY) {
        if(module.isToggled()){
            Colour = Colorutil.getToggledColor();
        }else{
            Colour = Colorutil.getOffColor();
        }
        Gui.drawRect(this.x, this.y, this.x + width, this.y + this.height, Colour);
        Wrapper.getMC().fontRenderer.drawString(module.getName(), x + 2, y + 2, new Color(255, 255, 255).getRGB());
        if(frame.dragging == true){
            setX(mouseX - frame.getPlusX());
            setY(mouseY - frame.getPlusY() + Offset);
        }

        if(Svisable == true){
            for(Setting setting : LeapFrog.getSettingManager().getSettingsForMod(module)){
                Gui.drawRect(this.x + this.width, this.y, this.x + (width * 2), this.y + height, Color.green.getRGB());

            }

        }
    }


    public void OnClick(int x, int y, int button){
        if(x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height){
            if(button == 0) {
                module.toggle();
            }
            if(button == 1){
                if(Svisable == true){
                    Svisable = false;
                }else{
                    Svisable = true;
                }

            }
        }
    }

    public void MouseReleased(int x, int y, Module.Type type){
    }

}
