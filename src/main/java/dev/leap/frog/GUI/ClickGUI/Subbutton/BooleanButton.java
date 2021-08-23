package dev.leap.frog.GUI.ClickGUI.Subbutton;

import dev.leap.frog.GUI.ClickGUI.ModuleButton;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class BooleanButton extends WidgetHandler {

    private Setting<Boolean> setting;

    public BooleanButton(Setting<Boolean> setting, int x, int y, ModuleButton button) {

        this.setting = setting;
    }

    @Override
    public void draw(int mouseX, int mouseY) { // new Color(10, 10, 10, 200).getRGB()

        Gui.drawRect(getX(), getY(), getX() + 2, getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());


    }
}
