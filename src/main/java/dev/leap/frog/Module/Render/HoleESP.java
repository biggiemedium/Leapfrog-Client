package dev.leap.frog.Module.Render;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;

public class HoleESP extends Module {

    public HoleESP() {
        super("HoleESP", "Shows holes that are safe nearby", Type.RENDER);
    }

    Settings doubleHole = create("DoubleHole", "DoubleHole", false);
}
