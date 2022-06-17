package dev.leap.frog.Event.Render;

import dev.leap.frog.Event.LeapFrogEvent;
import net.minecraft.entity.item.EntityEnderCrystal;

public class EventRenderEntityEnderCrystal extends LeapFrogEvent {

    private EntityEnderCrystal crystal;

    private double x, y, z;

    public EventRenderEntityEnderCrystal(EntityEnderCrystal crystal, double x, double y, double z) {
        this.crystal = crystal;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public EntityEnderCrystal getCrystal() {
        return crystal;
    }

    public void setCrystal(EntityEnderCrystal crystal) {
        this.crystal = crystal;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
