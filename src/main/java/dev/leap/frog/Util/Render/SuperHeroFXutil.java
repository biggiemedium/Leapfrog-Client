package dev.leap.frog.Util.Render;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Render.SuperHeroFX;
import dev.leap.frog.Util.Timer;
import net.minecraft.util.math.Vec3d;

public class SuperHeroFXutil extends UtilManager {

    private Vec3d pos;
    private long time;

    public SuperHeroFXutil(Vec3d pos, long time) {
        this.pos = pos;
        this.time = time;

    }
}
