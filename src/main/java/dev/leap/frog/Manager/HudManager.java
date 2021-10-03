package dev.leap.frog.Manager;

import dev.leap.frog.GUI.HUD.Component;
import dev.leap.frog.GUI.HUD.HUDITEM.*;
import dev.leap.frog.Module.Module;

import java.util.ArrayList;

public class HudManager {

    public static ArrayList<Component> HUDComponent = new ArrayList<>();
    public HudManager() {

    }

    private void Add(Component component) {
        HUDComponent.add(component);
    }

    public static ArrayList<Module> modules = new ArrayList<>();

}
