package dev.leap.frog.Event.Render;

import dev.leap.frog.Event.LeapFrogEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.Vec3d;

public class RenderEvent extends LeapFrogEvent {

    private final ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
    private final Tessellator tessellator;
    private final Vec3d renderPos;

    public RenderEvent(Tessellator tessellator, Vec3d pos) {
        this.tessellator = tessellator;
        this.renderPos = pos;
    }

    public Tessellator getTessellator() {
        return this.tessellator;
    }

    public Vec3d getRenderPos() {
        return renderPos;
    }

    public BufferBuilder getBufferBuild() {
        return this.tessellator.getBuffer();
    }

    public void setTranslation(Vec3d pos) {
        getBufferBuild().setTranslation(-pos.x, -pos.y, -pos.z);
    }

    public void resetTranslation() {
        setTranslation(renderPos);
    }

    public double getScreenWidth() {
        return res.getScaledWidth_double();
    }

    public double getScreenHeight() {
        return res.getScaledHeight_double();
    }

}
