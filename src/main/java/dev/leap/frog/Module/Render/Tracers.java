package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Tracers extends Module {

    public Tracers() {
        super("Tracers", "Tracers", Type.RENDER);
    }

    Setting<Boolean> friends = create("Friends",  true);
    Setting<Boolean> mobs = create("Mobs",  false);
    Setting<Integer> range = create("Range",  50, 0, 250);
    Setting<Float> width = create("Width",  1.0f, 0.0f, 5.0f);
    Setting<Boolean> colorDistance = create("ColorDistance",  true);

    @Override
    public void onRender(RenderEvent event) {
        if(mc.world == null) return;

        GlStateManager.pushMatrix();
        final float[][] colour = new float[1][1];
        mc.world.loadedEntityList.forEach(entity -> {
            if(!(entity instanceof EntityPlayer)) return;
            if (entity == mc.player) return;

                EntityPlayer player = (EntityPlayer) entity;


            if (mc.player.getDistance(player) > range.getValue()) return;
            if (FriendManager.isFriend(player.getName()) && !friends.getValue()) return;

            if(colorDistance.getValue()) {
                colour[0] = this.getColorByDistance(player);
                this.drawLineToEntity(player, colour[0][0], colour[0][1], colour[0][2], colour[0][3]);
                return;
            } else {
                colour[0] = this.getColorByDistance(player);
                this.drawLineToEntity(player, colour[0][0], colour[0][0], colour[0][0], colour[0][0]);
                return;
            }

        });
        GlStateManager.popMatrix();
    }


    public double interpolate(final double now, final double then) {
        return then + (now - then) * mc.getRenderPartialTicks();
    }

    public double[] interpolate(final Entity entity) {
        final double posX = this.interpolate(entity.posX, entity.lastTickPosX) - mc.getRenderManager().renderPosX;
        final double posY = this.interpolate(entity.posY, entity.lastTickPosY) - mc.getRenderManager().renderPosY;
        final double posZ = this.interpolate(entity.posZ, entity.lastTickPosZ) - mc.getRenderManager().renderPosZ;
        return new double[] { posX, posY, posZ };
    }

    public void drawLineToEntity(final Entity e, final float red, final float green, final float blue, final float opacity) {
        final double[] xyz = this.interpolate(e);
        this.drawLine(xyz[0], xyz[1], xyz[2], e.height, red, green, blue, opacity);
    }

    public void drawLine(final double posx, final double posy, final double posz, final double up, final float red, final float green, final float blue, final float opacity) {
        final Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(mc.player.rotationYaw));
        drawLineFromPosToPos(eyes.x, eyes.y + mc.player.getEyeHeight() + (float) 0.0f, eyes.z, posx, posy, posz, up, red, green, blue, opacity);

    }

    public void drawLineFromPosToPos(final double posx, final double posy, final double posz, final double posx2, final double posy2, final double posz2, final double up, final float red, final float green, final float blue, final float opacity) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth((float) width.getValue());
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, opacity);
        GlStateManager.disableLighting();
        GL11.glLoadIdentity();
        mc.entityRenderer.orientCamera(mc.getRenderPartialTicks());
        GL11.glBegin(1);
        GL11.glVertex3d(posx, posy, posz);
        GL11.glVertex3d(posx2, posy2, posz2);
        GL11.glVertex3d(posx2, posy2, posz2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glColor3d(1.0, 1.0, 1.0);
        GlStateManager.enableLighting();
    }

    public float[] getColorByDistance(final Entity entity) {
        if (entity instanceof EntityPlayer && FriendManager.isFriend(entity.getName())) {
            return new float[] { 0.0f, 0.5f, 1.0f, 1.0f };
        }
        final Color col = new Color(Color.HSBtoRGB((float)(Math.max(0.0, Math.min(mc.player.getDistanceSq(entity), 2500.0f) / 2500.0f) / 3.0), 1.0f, 0.8f) | 0xFF000000);
        return new float[] { col.getRed() / 255.0f, col.getGreen() / 255.0f, col.getBlue() / 255.0f, 1.0f };
    }

    public float[] getDefaultColor(final Entity entity) {
        if (entity instanceof EntityPlayer && !FriendManager.isFriend(entity.getName())) {
            return new float[]{0.0f, 0.5f, 1.0f, 1.0f};
        }
        return new float[] {1.0f, 0.0f, 0.0f, 1.0f};
    }


}

