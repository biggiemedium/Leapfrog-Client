package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import dev.leap.frog.Util.Pair;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class HoleESP extends Module {

    public HoleESP() {
        super("HoleESP", "Shows holes that are safe nearby", Type.RENDER);
    }

    Settings doubleHole = create("DoubleHole", "DoubleHole", false);
    Settings height = create("Height", "Height", "hole", combobox("hole", "flat"));
    Settings range = create("range", "Range", 5, 1, 15);


}
