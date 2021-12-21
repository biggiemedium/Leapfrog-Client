package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BreadCrums extends Module {

    public BreadCrums() {
        super("BreadCrums", "For all my noobs out there", Type.RENDER);
    }

    Setting<Float> width = create("Line Width", 1.5f, 0.1f, 5f);
    Setting<Integer> distance = create("Distance", 75, 0, 255);

    Setting<Integer> red = create("Red", 255, 0, 255);
    Setting<Integer> green = create("Green", 255, 0, 255);
    Setting<Integer> blue = create("Blue", 255, 0, 255);

    Setting<Integer> alpha = create("Alpha", 255, 0, 255);

    private List<Vec3d> posList = new ArrayList<>();

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck()) return;
        if(mc.player.posY == mc.player.lastTickPosY && mc.player.posZ == mc.player.lastTickPosZ) {
            return;
        }

        posList.add(new Vec3d(mc.player.posX, mc.player.posY, mc.player.posZ));
        if (posList.size() >= this.distance.getValue() * 10000) {
            posList.remove(0);
            posList.remove(1);
        }
    }

    @Override
    public void onRender(RenderEvent event) {
        if(UtilManager.nullCheck() || mc.renderManager == null) return;

        Color c = new Color(red.getValue(), green.getValue(), blue.getValue(), alpha.getValue());

        GlStateManager.pushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(width.getValue());
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        mc.entityRenderer.disableLightmap();
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glColor4d(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
        double dX = mc.getRenderManager().viewerPosX;
        double dY = mc.getRenderManager().viewerPosY;
        double dZ = mc.getRenderManager().viewerPosZ;

        for(Vec3d pos : posList) {
            Vec3d subtract = pos.subtract(dX, dY, dZ);
            GL11.glVertex3d(subtract.x, subtract.y, subtract.z);
        }

        GL11.glColor4d(1, 1, 1, 1);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();


    }

    @EventHandler
    private Listener<FMLNetworkEvent.ClientDisconnectionFromServerEvent> disconnectionEvent = new Listener<>(evet -> {
        posList.clear();
    });
}
