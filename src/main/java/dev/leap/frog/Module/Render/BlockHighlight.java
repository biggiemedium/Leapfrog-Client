package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Colorutil;
import dev.leap.frog.Util.Render.Renderutil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import java.awt.*;

public class BlockHighlight extends Module {

    public BlockHighlight() {
        super("BlockHighlight", "Highlights blocks that curser is over", Type.RENDER);
    }

    Setting<Integer> red = create("Red", 255, 0, 255);
    Setting<Integer> green = create("Green", 255, 0, 255);
    Setting<Integer> blue = create("Blue", 255, 0, 255);

    Setting<Integer> alpha = create("Alpha", 255, 0, 255);

    @Override
    public void onRender(RenderEvent event) {
        RayTraceResult ray = mc.objectMouseOver;

        if(ray == null || ray.typeOfHit != RayTraceResult.Type.BLOCK) return;

        BlockPos blockPos = ray.getBlockPos();

        if (mc.world.getBlockState(blockPos).getMaterial() != Material.AIR) {
                Renderutil.drawBoundingBoxBlockPos(blockPos, 1, red.getValue(), green.getValue(), blue.getValue(), alpha.getValue());
        }
    }
}
