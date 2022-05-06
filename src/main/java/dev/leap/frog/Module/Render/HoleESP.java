package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Block.Blockutil;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Render.RenderTessellatorutil;
import dev.leap.frog.Util.Render.Renderutil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class HoleESP extends Module {

    public HoleESP() {
        super("HoleESP", "Shows holes that are safe nearby", Type.RENDER);
    }

    Setting<Integer> distance = create("Distance", 5, 1, 20);
    Setting<RenderMode> renderMode = create("Render Mode", RenderMode.Outline);
    Setting<Boolean> doubleHoles = create("Double Hole", true);

    Setting<Integer> obbyRed = create("Obby Red", 255, 0, 255);
    Setting<Integer> obbyGreen = create("Obby Green", 255, 0, 255);
    Setting<Integer> obbyBlue = create("Obby Blue", 255, 0, 255);
    Setting<Integer> obbyAlpha = create("Obby Alpha", 255, 0, 255);

    Setting<Integer> bR = create("BedRock Red", 255, 0, 255);
    Setting<Integer> bG = create("BedRock Green", 255, 0, 255);
    Setting<Integer> bB = create("BedRock Blue", 255, 0, 255);
    Setting<Integer> bA = create("BedRock Alpha", 255, 0, 255);

    Setting<Float> glowHeight = create("Glow Height", 0.5f, 0.0f, 5.0f);
    Setting<Boolean> renderSelf = create("Render Self", true);

    private enum RenderMode {
        Full,
        Outline,
        Flat,
        Glow
    }

    private HashMap<BlockPos, Boolean> holesMap;
    private BlockPos playerPos;

    @Override
    public void onUpdate() {
        if(holesMap == null) {
            holesMap = new HashMap<>();
        } else {
            holesMap.clear();
        }
        playerPos = Playerutil.getPlayerPosFloored();
        List<BlockPos> holes = Blockutil.getSphere(playerPos, (int) Math.ceil(distance.getValue()), (int) Math.ceil(distance.getValue()), false, true, 0);
        for(BlockPos pos : holes) {

            if (!mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
                continue;
            }

            if (!mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            if (!mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }

            if(!renderSelf.getValue() && mc.player.getDistanceSq(pos) <= 1) {
                continue;
            }


            boolean safe = true;
            boolean bedrock = true;

        for(BlockPos offset : Blockutil.surroundOffset) {
            Block block = mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
            if (block != Blocks.BEDROCK) {
                bedrock = false;
            }
            if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                safe = false;
                break;
            }
        }
        if (!safe) {
            continue;
        }

        this.holesMap.put(pos, bedrock);
        }

    }

    @Override
    public void onRender(RenderEvent event) {
        if(UtilManager.nullCheck() || holesMap == null || holesMap.isEmpty() || mc.getRenderViewEntity() == null) return;
            holesMap.forEach((hole, bedrock) -> {
                int red = bedrock ? bR.getValue() : obbyRed.getValue();
                int green = bedrock ? bG.getValue() : obbyGreen.getValue();
                int blue = bedrock ? bB.getValue() : obbyBlue.getValue();
                int alpha = bedrock ? bA.getValue() : obbyAlpha.getValue();

                if(renderMode.getValue() == RenderMode.Full) {
                    RenderTessellatorutil.prepare("quads");
                    RenderTessellatorutil.drawCube(hole, red, green, blue, alpha, "all");
                    RenderTessellatorutil.release();
                }
               if(renderMode.getValue() == RenderMode.Outline) {
                   Renderutil.drawBoundingBoxBlockPos(hole, 1, red, green, blue, alpha);
               }

               if(renderMode.getValue() == RenderMode.Flat) {
                   
               }

               if(renderMode.getValue() == RenderMode.Glow) {

                   AxisAlignedBB axisAlignedBB = new AxisAlignedBB(hole.getX() - mc.getRenderManager().viewerPosX, hole.getY() - mc.getRenderManager().viewerPosY, hole.getZ() - mc.getRenderManager().viewerPosZ, hole.getX() + 1 - mc.getRenderManager().viewerPosX, hole.getY() + 1 - mc.getRenderManager().viewerPosY, hole.getZ() + 1 - mc.getRenderManager().viewerPosZ);

                   GlStateManager.disableCull();
                   GlStateManager.disableAlpha();
                   GlStateManager.shadeModel(GL11.GL_SMOOTH);

                   Renderutil.drawSelectionGlowFilledBox(axisAlignedBB, glowHeight.getValue(), 0, 0, new Color(red, green, blue, alpha / 2), new Color(red, green, blue, 0));

                   GlStateManager.enableCull();
                   GlStateManager.enableAlpha();
                   GlStateManager.shadeModel(GL11.GL_FLAT);
               }
        });
    }
}
