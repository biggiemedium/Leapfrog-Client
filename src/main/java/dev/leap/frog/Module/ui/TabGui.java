package dev.leap.frog.Module.ui;

import dev.leap.frog.Module.Module;
import org.lwjgl.input.Keyboard;

public class TabGui extends Module {

    public TabGui() {
        super("TabGui", "Tab", Type.GUI);
        setKey(Keyboard.KEY_B);
    }



}
