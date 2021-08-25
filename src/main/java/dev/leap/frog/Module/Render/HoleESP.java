package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class HoleESP extends Module {

    public HoleESP() {
        super("HoleESP", "Shows holes that are safe nearby", Type.RENDER);
    }

    Setting<Integer> distance = create("Distance", 75, 0, 250);

    private ArrayList<BlockPos> holes = new ArrayList<>();
    private double x;
    private double y;
    private double z;

    private BlockPos playerPosition = new BlockPos(x, y, z);

    @Override
    public void onUpdate() {
        x = mc.player.posX;
        y = mc.player.posY;
        z = mc.player.posZ;



    }

    @Override
    public void onRender(RenderEvent event) {

    }
}
