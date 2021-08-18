package dev.leap.frog.GUI.HUD;

import dev.leap.frog.Manager.UtilManager;

public class Component extends UtilManager {
    private int x;
    private int y;

    private String name;

    public Component(int width, int height, String Name){
        this.x = width;
        this.y = height;
        this.name = Name;

    }
    public void Render(int mouseX, int mouseY){
        this.Render(mouseX,mouseY);

    }
    public void Click(int mouseX, int mouseY, int mouseButton){

    }
}
