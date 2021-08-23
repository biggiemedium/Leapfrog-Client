package dev.leap.frog.GUI.ClickGUI;

import dev.leap.frog.GUI.ClickGUI.Subbutton.BindButton;
import dev.leap.frog.GUI.ClickGUI.Subbutton.Slider;
import dev.leap.frog.GUI.ClickGUI.Subbutton.SliderTypes.FloatSlider;
import dev.leap.frog.GUI.ClickGUI.Subbutton.WidgetHandler;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Colorutil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.gui.Gui;
import scala.tools.nsc.backend.icode.TypeKinds;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ModuleButton {

    private Module module;
    private Frame frame;

    private int x;
    private int y;
    private int count;
    private boolean Svisable;
    public ArrayList<ModuleButton> settingButton;
    public ArrayList<WidgetHandler> handlers;
    private boolean opened;

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

         // start of subbuttons below


        this.handlers = new ArrayList<>();
        int saveY = 0;
        for(Setting s : LeapFrog.getSettingManager().getSettingsArrayList()) {
            if(s.getValue() instanceof Integer) {
                handlers.add(new FloatSlider(this, s.getValue()));
            }
            if(s.getValue() instanceof Boolean) {
                // insert Boolean here
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
        return Offset;
    }

    public void setOffset(int offset) {
        Offset = offset;
    }

    public Module getModule() {
        return module;
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
            for(WidgetHandler h : handlers){
                    h.draw(mouseX,mouseY);
            }
            }

        }



    public void OnClick(int mx, int my, int button) throws IOException {
        if(Svisable) {
            for (WidgetHandler h : handlers) {
                h.mouseClicked(mx, my, button);
            }
        }
        if(mx >= this.x && mx <= this.x + this.width && my >= this.y && my <= this.y + this.height){
            if(button == 0) {
                module.toggle();
            }
            if(button == 1){
                if(Svisable){
                    Svisable = false;
                }else{
                    Svisable = true;
                }



            }
        }
    }

    public void MouseReleased(int mx, int my, int button){
        if(Svisable){
            for(WidgetHandler h : handlers){
                h.mouseReleased(mx, my, button);
            }
        }

    }

}
