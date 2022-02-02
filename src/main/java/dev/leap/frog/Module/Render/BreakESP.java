package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Math.Mathutil;
import dev.leap.frog.Util.Render.RenderHelputil;
import dev.leap.frog.Util.Render.Renderutil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class BreakESP extends Module {

    public BreakESP() {
        super("BreakESP", "Renders blocks being broken", Type.RENDER);
    }

    Setting<Mode> mode = create("Mode", Mode.Full);

    Setting<Integer> red = create("Red", 255, 0, 255);
    Setting<Integer> green = create("Green", 255, 0, 255);
    Setting<Integer> blue = create("Blue", 255, 0, 255);

    Setting<Integer> alpha = create("Alpha", 150, 0, 255);
    Setting<Boolean> transparency = create("Fade", true);
    Setting<Integer> transparencyLevel = create("Fade level", 250, 0, 255, v -> transparency.getValue());

    private enum Mode {
        Full,
        Outline
    }

    private ArrayList<BlockPos> breakList = new ArrayList<>();

    @Override
    public void onEnable() {
        if(breakList == null) {
            breakList = new ArrayList<>();
        }
    }

    @Override
    public void onRender(RenderEvent event) {
        int fade = transparency.getValue() ? transparencyLevel.getValue() : 0;
        mc.renderGlobal.damagedBlocks.forEach((integer, damage) -> {
            if(damage != null && integer != null) {
                if(mode.getValue() == Mode.Full) {
                    IBlockState state = mc.world.getBlockState(damage.getPosition());
                    Vec3d interpos = Mathutil.getInterpolatedPos(mc.player, mc.getRenderPartialTicks());
                    Renderutil.drawFullBox(state.getSelectedBoundingBox(mc.world, damage.getPosition()).grow(0.0020000000949949026).offset(-interpos.x, -interpos.y, -interpos.z), damage.getPosition(), 1.5f, red.getValue(), green.getValue(), blue.getValue(), alpha.getValue(), fade);
                    RenderHelputil.prepare("quads");
                    RenderHelputil.drawCube(damage.getPosition(), red.getValue(), green.getValue(), blue.getValue(), alpha.getValue(), "all");
                    RenderHelputil.release();
                } else {
                    IBlockState state = mc.world.getBlockState(damage.getPosition());
                    Vec3d interpos = Mathutil.getInterpolatedPos(mc.player, mc.getRenderPartialTicks());
                    Renderutil.drawFullBox(state.getSelectedBoundingBox(mc.world, damage.getPosition()).grow(0.0020000000949949026).offset(-interpos.x, -interpos.y, -interpos.z), damage.getPosition(), 1.5f, red.getValue(), green.getValue(), blue.getValue(), alpha.getValue(), fade);
                    RenderHelputil.prepare("lines");
                    RenderHelputil.drawCube(damage.getPosition(), red.getValue(), green.getValue(), blue.getValue(), alpha.getValue(), "all");
                    RenderHelputil.release();
                }
            }
        });
    }
}
