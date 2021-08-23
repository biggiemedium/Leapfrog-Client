package dev.leap.frog.GUI.ClickGUI.Subbutton;

import net.minecraft.client.gui.Gui;

import java.awt.*;

public class Slider {

    public boolean dragged;
    public void Slider(int x, int y, int width, int height, float val){
        float slideup = val / (float) width;
        Gui.drawRect(x, y,x + width,y +  height, Color.green.getRGB());
    }
    public void mousePos(int mx, int my, int mb){

    }
    public void mDown(int mx, int my, int mb){

    }
    public void mReleased(int mx, int my, int mb){

    }


}
