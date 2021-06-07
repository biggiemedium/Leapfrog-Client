package dev.leap.frog.Event.Movement;

import dev.leap.frog.Event.LeapFrogEvent;
import net.minecraft.entity.MoverType;

public class EventPlayerMove extends LeapFrogEvent {

    private MoverType move_type;

    public double x, y, z;

    public EventPlayerMove(MoverType type, double x, double y, double z) {
        this.move_type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setMoveType(MoverType type) {
        this.move_type = type;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public MoverType get_move_type() {
        return this.move_type;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

}
