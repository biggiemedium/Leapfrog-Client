package dev.leap.frog.Manager;

import dev.leap.frog.GUI.HUD.Component;
import dev.leap.frog.GUI.HUD.HUDITEM.FPS;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Module.ui.ClickGUI;

import java.util.ArrayList;

public class HudManager {
    public static ArrayList<Component> HUDComponent = new ArrayList<>();
    public HudManager() {
        HUDComponent.add(new FPS());

    }
    public static ArrayList<Module> modules = new ArrayList<>();


}
