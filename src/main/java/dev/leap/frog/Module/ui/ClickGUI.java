package dev.leap.frog.Module.ui;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {

    public ClickGUI() {
        super("ClickGUI", "CGUI", Type.GUI);
        setKey(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(dev.leap.frog.GUI.ClickGUI.getInstance);
    }

    @Override
    public void onUpdate() {
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            LeapFrog.moduleManager().getModuleName("ClickGUI").setToggled(false);
        }
    }
}
