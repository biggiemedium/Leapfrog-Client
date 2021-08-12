package dev.leap.frog.GUI.ClickGUI.Subbutton;

import net.minecraft.client.gui.Gui;

import java.awt.*;

public class Slider {
    public static void Slider(int x, int y, int width, int height, int val){
        Gui.drawRect(x + width, y, x + width, y + height, Color.green.getRGB());
        System.out.println("e");
    }
}
