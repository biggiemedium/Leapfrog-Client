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


}
