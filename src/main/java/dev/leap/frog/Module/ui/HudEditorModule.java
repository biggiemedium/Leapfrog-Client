package dev.leap.frog.Module.ui;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;

public class HudEditorModule extends Module {

    public HudEditorModule() {
        super("HudEditor", "Allows you to edit your on screen HUD", Type.CLIENT);
    }

    @Override
    public void onEnable() {
        LeapFrog.getModuleManager().getModule(ClickGUIModule.class).setToggled(false);
    }

    @Override
    public void onDisable() {
        LeapFrog.getModuleManager().getModule(ClickGUIModule.class).setToggled(true);
    }
}
