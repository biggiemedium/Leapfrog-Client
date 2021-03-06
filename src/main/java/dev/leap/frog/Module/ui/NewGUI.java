package dev.leap.frog.Module.ui;

import dev.leap.frog.GUI.ClickGUI2.ClickGUI;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Module.ui.ClickGUIModule;
import org.lwjgl.input.Keyboard;

public class NewGUI extends Module {

    public NewGUI() {
        super("New GUI", "New GUI by PX", Type.CLIENT);
    }

    @Override
    public void onEnable() {
        if(LeapFrog.getModuleManager().getModule(ClickGUIModule.class).isToggled()) {
            LeapFrog.getModuleManager().getModule(ClickGUIModule.class).setToggled(false);
        }

        mc.displayGuiScreen(new ClickGUI());
        toggle();
    }
}
