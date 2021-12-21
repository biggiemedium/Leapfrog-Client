package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Render.Renderutil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.util.ArrayList;
import java.util.HashMap;

public class ESP extends Module {

    public ESP() {
        super("ESP", "Shows Entitys through walls", Type.RENDER);
    }

    private HashMap<EntityPlayer, float[][]> entityPlayerHashMap = new HashMap<>();
    private ICamera camera = new Frustum();

    public static ESP ESP = new ESP();

    Setting<ESPMode> mode = create("Mode", ESPMode.R6);

    Setting<Boolean> player = create("Player", true);
    Setting<Boolean> mob = create("Mob", false);
    Setting<Boolean> crystals = create("Crystals", true);
    Setting<Boolean> invisible = create("Invisible", true);
    Setting<Boolean> friends = create("Friends", false);

    Setting<Integer> r = create("Red", 255, 0, 255);
    Setting<Integer> g = create("green", 255, 0, 255);
    Setting<Integer> b = create("blue", 255, 0, 255);

    Setting<Integer> distance = create("Distance", 75, 1, 255);

    private enum ESPMode {
        Shader,
        ESP,
        SKELETON,
        R6,
        BOX,
        CSGO
    }

    @Override
    public void onUpdate() {
        if(mc.player == null || mc.world == null) return;

            mc.world.loadedEntityList.forEach(e -> {
                if(mc.player.getDistance(e) > distance.getValue() || mode.getValue() != ESPMode.Shader) {
                    e.setGlowing(false);
                    return;
                }

                if(e instanceof EntityPlayer && e != mc.player && player.getValue() && !e.isInvisible()) {
                    e.setGlowing(true);
                } else {
                    e.setGlowing(false);
                }

                if(e instanceof EntityAnimal && mob.getValue()) {
                    e.setGlowing(true);
                } else {
                    e.setGlowing(false);
                }

                if(e instanceof EntityEnderCrystal && crystals.getValue()) {
                    e.setGlowing(true);
                } else {
                    e.setGlowing(false);
                }

                if(e instanceof EntityPlayer && e != mc.player && invisible.getValue() && e.isInvisible()) {
                    e.setGlowing(true);
                } else {
                    e.setGlowing(false);
                }

        });
    }

    @Override
    public void onDisable() {
        if(mc.player == null || mc.world == null) return;

        mc.world.loadedEntityList.forEach(entity -> { // esp does not seem to toggle so this is necessary
                entity.setGlowing(false);
        });
    }

    @Override
    public void onRender(RenderEvent event) {

        if(mode.getValue() == ESPMode.SKELETON) {
            startEnd(true);
            GL11.glEnable(2903);
            GL11.glDisable(2848);
            entityPlayerHashMap.keySet().removeIf(this::containsEntity);
            mc.world.playerEntities.forEach(e -> renderSkeleton(event, e));

            Gui.drawRect(0, 0, 0, 0, 0);
            startEnd(false);
        } else if(mode.getValue() == ESPMode.R6) {
            mc.world.playerEntities.forEach(e -> renderR6ESP(event, e));
        } else if(mode.getValue() == ESPMode.CSGO) {
            mc.world.playerEntities.forEach(e -> renderCSGO(event, e));
        }
    }

    private void renderSkeleton(RenderEvent event, EntityPlayer player) {
        if(mode.getValue() == ESPMode.SKELETON && player != null) {
            double d3 = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * event.getPartialTicks();
            double d4 = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * event.getPartialTicks();
            double d5 = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * event.getPartialTicks();

            camera.setPosition(d3, d4, d5);
            float[][] entPos = entityPlayerHashMap.get(player);

            if(entityCheck(player, entPos)) {

                float pt = event.getPartialTicks();
                double px = player.lastTickPosX + (player.posX - player.lastTickPosX) * pt;
                double py = player.lastTickPosY + (player.posY - player.lastTickPosY) * pt;
                double pz = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * pt;

                GL11.glPopMatrix();
                GL11.glEnable(2848);
                GL11.glLineWidth(1.0F);
                GlStateManager.color(255 / 255.0F, 255 / 255.0F, 255 / 255.0F, 1.0F);

                Vec3d v = new Vec3d(px, py, pz);
                double x = v.x - mc.getRenderManager().renderPosX;
                double y = v.y - mc.getRenderManager().renderPosY;
                double z = v.z - mc.getRenderManager().renderPosZ;

                GL11.glTranslated(x, y, z);
                GL11.glRotatef(-player.renderYawOffset, 0.0f, 1.0f, 0.0f); // ? float xOff = e.prevRenderYawOffset + (e.renderYawOffset - e.prevRenderYawOffset) * event.getPartialTicks();
                GL11.glTranslated(0.0, 0.0, player.isSneaking() ? -0.235d : 0.0);

                GL11.glPushMatrix();

                GlStateManager.color(1f, 1f, 1f, 1.0F);
                GL11.glTranslated(-0.125D, player.isSneaking() ? 0.6f : 0.75f, 0.0D);

                // redo ?

                if (entPos[3][0] != 0.0f) {
                    GlStateManager.rotate(entPos[3][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                if (entPos[3][1] != 0.0f) {
                    GlStateManager.rotate(entPos[3][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (entPos[3][2] != 0.0f) {
                    GlStateManager.rotate(entPos[3][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                GL11.glBegin(3);
                GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                GL11.glVertex3d(0.0D, player.isSneaking() ? -0.6f : -0.75f, 0.0D);
                GL11.glEnd();

                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslated(0.125, player.isSneaking() ? 0.6f : 0.75f, 0.0);

                if (entPos[4][0] != 0.0F) {
                    GL11.glRotatef(entPos[4][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
                }
                if (entPos[4][1] != 0.0F) {
                    GL11.glRotatef(entPos[4][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
                }
                if (entPos[4][2] != 0.0F) {
                    GL11.glRotatef(entPos[4][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
                }
                GL11.glBegin(3);
                GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                GL11.glVertex3d(0.0D, player.isSneaking() ? -0.6f : -0.75f, 0.0D);
                GL11.glEnd();

                GL11.glPopMatrix();
                GL11.glTranslated(0.0D, 0.0D, player.isSneaking() ? 0.25D : 0.0D);
                GL11.glPushMatrix();
                GlStateManager.color(1f, 1f, 1f, 1.0F);
                GL11.glTranslated(0.0D, player.isSneaking() ? -0.05D : 0.0D, player.isSneaking() ? -0.01725D : 0.0D);
                GL11.glPushMatrix();

                GlStateManager.color(1f, 1f, 1f, 1.0F);
                GL11.glTranslated(-0.375D, player.isSneaking() ? 0.6f : 0.75f + 0.55D, 0.0D);

                if(entPos[1][0] != 0.0f) {
                    GL11.glRotatef(entPos[1][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                if (entPos[1][1] != 0.0f) {
                    GL11.glRotatef(entPos[1][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (entPos[1][2] != 0.0f) {
                    GL11.glRotatef(-entPos[1][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
                }

                GL11.glBegin(3);
                GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                GL11.glVertex3d(0.0D, -0.5D, 0.0D);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPushMatrix();

                GL11.glTranslated(0.375D, player.isSneaking() ? 0.6f : 0.75f + 0.55D, 0.0D);
                if (entPos[2][0] != 0.0F) {
                    GL11.glRotatef(entPos[2][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
                }
                if (entPos[2][1] != 0.0F) {
                    GL11.glRotatef(entPos[2][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
                }
                if (entPos[2][2] != 0.0F) {
                    GL11.glRotatef(-entPos[2][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
                }

                GL11.glBegin(3);
                GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                GL11.glVertex3d(0.0D, -0.5D, 0.0D);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glRotatef(player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * event.getPartialTicks() - player.rotationYawHead, 0.0F, 1.0F, 0.0F);
                GL11.glPushMatrix();
                GlStateManager.color(1f, 1f, 1f, 1.0F);
                GL11.glTranslated(0.0D, player.isSneaking() ? 0.6f : 0.75f + 0.55D, 0.0D);

                if (entPos[0][0] != 0.0F) {
                    GL11.glRotatef(entPos[0][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
                }

                GL11.glBegin(3);
                GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                GL11.glVertex3d(0.0D, 0.3D, 0.0D);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPopMatrix();

                GL11.glRotatef(player.isSneaking() ? 25.0F : 0.0F, 1.0F, 0.0F, 0.0F);
                GL11.glTranslated(0.0D, player.isSneaking() ? -0.16175D : 0.0D, player.isSneaking() ? -0.48025D : 0.0D);
                GL11.glPushMatrix();

                GL11.glTranslated(0.0D, player.isSneaking() ? 0.6f : 0.75f, 0.0D);
                GL11.glBegin(3);
                GL11.glVertex3d(-0.125D, 0.0D, 0.0D);
                GL11.glVertex3d(0.125D, 0.0D, 0.0D);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPushMatrix();

                GlStateManager.color(1f, 1f, 1f, 1.0F);

                GL11.glTranslated(0.0D, player.isSneaking() ? 0.6f : 0.75f, 0.0D);
                GL11.glBegin(3);
                GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                GL11.glVertex3d(0.0D, 0.55D, 0.0D);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslated(0.0D, player.isSneaking() ? 0.6f : 0.75f + 0.55D, 0.0D);
                GL11.glBegin(3);
                GL11.glVertex3d(-0.375D, 0.0D, 0.0D);
                GL11.glVertex3d(0.375D, 0.0D, 0.0D);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            }
        }
    }

    private void renderR6ESP(RenderEvent event, EntityPlayer e) {
        GL11.glPushMatrix();

    }

    private void renderCSGO(RenderEvent event, EntityPlayer player) { // TODO: Finish ESP
        GL11.glPushMatrix();
        if(player != null && !player.isDead && player != mc.player) {

        }
    }

    private boolean containsEntity(EntityPlayer player) {
        return !mc.world.playerEntities.contains(player);
    }

    private boolean entityCheck(EntityPlayer player, float[][] f) {
        return player != null && f != null && player.isEntityAlive() && camera.isBoundingBoxInFrustum(player.getEntityBoundingBox()) && !player.isDead && player != mc.player;
    }

    /**
     * @author elementars
     */
    private void startEnd(boolean revert) {
        if (revert) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GL11.glEnable(2848);
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GL11.glHint(3154, 4354);
        }
        else {
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GL11.glDisable(2848);
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
        GlStateManager.depthMask(!revert);
    }
}
