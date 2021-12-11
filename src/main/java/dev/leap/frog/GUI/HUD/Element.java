package dev.leap.frog.GUI.HUD;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.HudSetting;
import dev.leap.frog.Settings.Setting;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class Element extends Module {

    public Element(String name, String description) {
        super(name, description, Type.CLIENT);
    }


}
