package dev.leap.frog.Module.ui;

import dev.leap.frog.GUI.Click;
import dev.leap.frog.Module.Module;
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
        mc.displayGuiScreen(Click.INSTANCE);
        toggle(); // this is to ensure that frame closes correctly
    }
}
