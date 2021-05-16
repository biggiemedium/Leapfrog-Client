package dev.leap.frog.GUI.Items;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.SettingsManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class ModuleButton {

    private Module module;
    private Frame frame;

    private int x;
    private int y;
    private boolean Dragging;
    private int count;

    private int width;
    private int height;
    private int Colour;

    public ModuleButton(Module module, int x, int y,Frame frame) {
        this.module = module;
        this.x = x;
        this.y = y + 2;
        this.frame = frame;
        this.width = frame.width;
        this.height = 14;

    }

    public void draw(int mouseX, int mouseY) {
        if(module.isToggled()){
            Colour = Color.green.getRGB();
        }else{
            Colour = Color.gray.getRGB();
        }
        Gui.drawRect(this.x, this.y, this.x + width, this.y + this.height, Colour);
        Minecraft.getMinecraft().fontRenderer.drawString(module.getName(), x + 2, y + 2, new Color(255, 255, 255).getRGB());
        if(Dragging == true){
            //  this.x + this.x - mouseX;
            //  this.y + this.y - mouseY;

        }
    }

    public void OnClick(int x, int y, int button, Module.Type type){
        if(module.getType() == type){

        }
        if(x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height){
            if(button == 0) {
                module.toggle();

            }
            if(button == 1){
                System.out.println("right click" );
                if(LeapFrog.getSettingsManager().getSettingsForMod(module).isEmpty()){
                    System.out.println("oef");
                }
                    for(Setting setting : LeapFrog.getSettingsManager().getSettingsForMod(module)){
                        System.out.println(setting);
                    }

            }
        }
    }

    public void MouseReleased(int x, int y, Module.Type type){
        if(module.getType() == type){

        }

    }
}
