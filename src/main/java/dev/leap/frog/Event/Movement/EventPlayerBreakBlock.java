package dev.leap.frog.Event.Movement;

import dev.leap.frog.Event.LeapFrogEvent;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class EventPlayerBreakBlock extends LeapFrogEvent {

    private BlockPos pos;
    private EnumFacing facing;

    public EventPlayerBreakBlock(BlockPos pos, EnumFacing facing) {
        this.pos = pos;
    }

    public BlockPos getPos() {
        return pos;
    }

    public EnumFacing getFacing() {
        return facing;
    }
}
