package dev.leap.frog.Module.ui;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import org.lwjgl.input.Keyboard;

public class ClickGUIModule extends Module {

    public ClickGUIModule() {
        super("ClickGUI", "CGUI", Type.RENDER);
        setKey(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onUpdate() {


    }


    @Override
    public void onEnable() {

    }
}
