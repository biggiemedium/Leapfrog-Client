package dev.leap.frog.GUI.ClickGUI.Subbutton.SliderTypes;

import dev.leap.frog.GUI.ClickGUI.ModuleButton;
import dev.leap.frog.GUI.ClickGUI.Subbutton.WidgetHandler;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class FloatSlider<T> extends WidgetHandler {
    private T val;
    private ModuleButton m;

    public FloatSlider(ModuleButton moduleButton, T val) {
        super();
        this.m = moduleButton;
        this.val = val;
    }

    int currentpos;


    public void draw(int mouseX, int mouseY) {
        if(val instanceof Float){
        }

        Gui.drawRect(m.getX(), m.getY(), m.getX() + m.getWidth(), m.getY() + m.getHeight(), Color.green.getRGB());
    }
    @Override
    public void mouseClicked(int mx, int my, int mb){
        if(mx >= this.getX() && mx <= this.getX() + this.getWidth() && my >= this.getY() && my <= this.getY() + this.getY()){
            currentpos = mx - m.getY();
        }
    }
    @Override
    public void mouseReleased(int mx, int my, int mb){

    }
}

