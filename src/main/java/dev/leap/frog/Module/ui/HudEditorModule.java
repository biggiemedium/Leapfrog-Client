package dev.leap.frog.Module.ui;

import dev.leap.frog.GUI.HUD.Hud;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;

import java.util.ArrayList;

public class HudEditorModule extends Module {

    public HudEditorModule() {
        super("HudEditor", "Allows you to edit your on screen HUD", Type.CLIENT);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new Hud());
        toggle();
    }

    @Override
    public void onDisable() {
    }
}
