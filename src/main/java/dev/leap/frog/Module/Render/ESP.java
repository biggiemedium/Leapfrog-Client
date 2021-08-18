package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Render.Renderutil;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
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
    private ArrayList<Entity> glowingEntitys = new ArrayList<>();
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
        BOX
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
    public void onRender(RenderEvent event) { // rgb will not affect if mode is in shader
        mc.world.loadedEntityList.forEach(e -> {
            if(mc.player.getDistance(e) < distance.getValue() && mode.getValue() == ESPMode.BOX && e != mc.player && Entityutil.isLiving(e)) {

                Vec3d v = Entityutil.getInterpolatedPos(e, event.getPartialTicks());
                GlStateManager.pushMatrix();
                GlStateManager.translate(v.x - mc.getRenderManager().renderPosX, v.y - mc.getRenderManager().renderPosY, v.z - mc.getRenderManager().renderPosZ);

                GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate((float) (mc.getRenderManager().options.thirdPersonView == 2 ? -1 : 1), 1.0F, 0.0F, 0.0F);
                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);

                GlStateManager.disableDepth();

                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

                if (e instanceof EntityPlayer) {
                    GL11.glColor3f(r.getValue(), g.getValue(), b.getValue());
                } else if (Entityutil.isPassive(e)) {
                    GL11.glColor3f(0.11f, 0.9f, 0.11f);
                } else {
                    GL11.glColor3f(0.9f, .1f, .1f);
                }

                GlStateManager.disableTexture2D();
                GL11.glLineWidth(2f);
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                GL11.glBegin(GL11.GL_LINE_LOOP);
                {
                    GL11.glVertex2d(-e.width / 2, 0);
                    GL11.glVertex2d(-e.width / 2, e.height);
                    GL11.glVertex2d(e.width / 2, e.height);
                    GL11.glVertex2d(e.width / 2, 0);
                }
                GL11.glEnd();

                GlStateManager.popMatrix();

                GlStateManager.enableDepth();
                GlStateManager.depthMask(true);
                GlStateManager.disableTexture2D();
                GlStateManager.enableBlend();
                GlStateManager.disableAlpha();
                GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                GlStateManager.shadeModel(GL11.GL_SMOOTH);
                GlStateManager.disableDepth();
                GlStateManager.enableCull();
                GlStateManager.glLineWidth(1);
                GL11.glColor3f(1, 1, 1);


            }
    });

        mc.world.playerEntities.forEach(player -> {
            if (mc.player.getDistance(player) < distance.getValue() && mode.getValue() == ESPMode.SKELETON && player != mc.player) {



            }
        });

        mc.world.playerEntities.forEach(e -> {
            if (mc.player.getDistance(e) < distance.getValue() && mode.getValue() == ESPMode.R6 && e != mc.player) {
                renderR6ESP(event, e);
            }
        });
}

    private void renderSkeleton(RenderEvent event, EntityPlayer player) {

    }

    private void renderR6ESP(RenderEvent event, EntityPlayer e) {

        Vec3d v = Entityutil.getInterpolatedPos(e, event.getPartialTicks());
        GlStateManager.pushMatrix();
        GlStateManager.translate(v.x - mc.getRenderManager().renderPosX, v.y - mc.getRenderManager().renderPosY, v.z - mc.getRenderManager().renderPosZ);

        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) (mc.getRenderManager().options.thirdPersonView == 2 ? -1 : 1), 1.0F, 0.0F, 0.0F);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);

        GlStateManager.disableDepth();

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        if(!e.isSneaking()) {
        GL11.glColor3f(0.9f, .1f, .1f);

        GlStateManager.disableTexture2D();
        GL11.glLineWidth(2f);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_LINE_LOOP); {
            GL11.glVertex2d(-e.width / 2, 0);
            GL11.glVertex2d(-e.width / 2, e.height);
            GL11.glVertex2d(e.width / 2, e.height);
            GL11.glVertex2d(e.width / 2, 0);
        }


        if(Entityutil.getHealth(e) >= 20) {
            GL11.glColor3f(0.0f, 1.0f, 0.0f);
        } else if(Entityutil.getHealth(e) < 15) {
            GL11.glColor3f(1.0f, 0.8f, 0.0f);
        } else if(Entityutil.getHealth(e) < 10) {
            GL11.glColor3f(1.0f, 1.0f, 0.0f);
        } else if(Entityutil.getHealth(e) < 5) {
            GL11.glColor3f(1.0f, 0.2f, 0.0f);
        } else {
            GL11.glColor3f(1.0f, 0.0f, 0.0f);
        }

            GL11.glBegin(GL11.GL_POLYGON); {
                GL11.glVertex2d(e.width, 0);
                GL11.glVertex2d(e.width, e.height);
                GL11.glVertex2d(e.width - 1.5f/ 6, e.height);
                GL11.glVertex2d(e.width - 1.5f/ 6, 0);
            }
        }

        GL11.glEnd();

        GlStateManager.popMatrix();

        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableDepth();
        GlStateManager.enableCull();
        GlStateManager.glLineWidth(1);
        GL11.glColor3f(1, 1, 1);
    }
}
