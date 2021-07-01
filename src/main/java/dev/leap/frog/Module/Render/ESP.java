package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.List;

public class ESP extends Module {

    public ESP() {
        super("ESP", "Shows Entitys through walls", Type.RENDER);
    }

    private HashMap<EntityPlayer, float[][]> entityPlayerHashMap = new HashMap<>();

    Settings mode = create("Mode", "Mode", "Shader", combobox("Shader", "ESP", "Skeleton", "CSGO"));

    Settings player = create("Player", "Player", true);
    Settings mob = create("Mob", "Mob", false);
    Settings crystals = create("Crystals", "Crystals", true);
    Settings invisible = create("Invisible", "Invisible", true);

    Settings r = create("Red", "Red", 255, 0, 255);
    Settings g = create("green", "green", 255, 0, 255);
    Settings b = create("blue", "blue", 255, 0, 255);

    Settings distance = create("Distance", "Distance", 75, 1, 255);

    @Override
    public void onUpdate() {
        if(mc.player == null || mc.world == null) return;

        mc.world.loadedEntityList.forEach(entity -> {

            if(mc.player.getDistance(entity) > distance.getValue(1)) return;

            if(player.getValue(true) && mode.in("Shader")) {
                if(entity instanceof EntityPlayer && entity != mc.player) {
                    if(FriendManager.isFriend(((EntityPlayer) entity).getDisplayNameString())) {
                        entity.setGlowing(false);
                    } else {
                        entity.setGlowing(true);
                    }
                }
            }

            if(invisible.getValue(true) && mode.in("Shader")) {
                if(entity instanceof EntityPlayer && entity.isInvisible() && entity != mc.player) {
                    entity.setGlowing(true);
                }
            }

            if(crystals.getValue(true) && mode.in("Shader")) {
                if(entity instanceof EntityEnderCrystal) {
                    entity.setGlowing(true);
                }
            }

            if(mob.getValue(true) && mode.in("Shader")) {
                if(entity instanceof EntityAnimal || entity instanceof EntityMob) {
                    entity.setGlowing(true);
                }
            }

            // does not toggle for some reason so this is necessary

            if(!crystals.getValue(true) || !mode.in("Shader")) {
                if(entity instanceof EntityEnderCrystal) {
                    entity.setGlowing(false);
                }
            }

            if(!player.getValue(true) || !mode.in("Shader")) {
                if(entity instanceof EntityPlayer) {
                    entity.setGlowing(false);
                }
            }

            if(!invisible.getValue(true) || !mode.in("Shader")) {
                if(entity instanceof EntityPlayer && entity.isInvisible()) {
                    entity.setGlowing(false);
                }
            }

            if(!mob.getValue(true) || !mode.in("Shader")) {
                if(entity instanceof EntityAnimal || entity instanceof EntityMob) {
                    entity.setGlowing(false);
                }
            }

        });
    }

    @Override
    public void onDisable() {
        if(mc.player == null || mc.world == null) return;

        mc.world.loadedEntityList.forEach(entity -> { // esp does not seem to toggle so this is necessary

            entity.setGlowing(false);

            if (player.getValue(true) || mode.in("Shader")) {
                if (entity instanceof EntityPlayer) {
                    entity.setGlowing(false);
                }
            }

            if (invisible.getValue(true) || mode.in("Shader")) {
                if (entity instanceof EntityPlayer && entity.isInvisible() && entity != mc.player) {
                    entity.setGlowing(false);
                }
            }

            if (crystals.getValue(true) || mode.in("Shader")) {
                if (entity instanceof EntityEnderCrystal) {
                    entity.setGlowing(false);
                }
            }

            if (mob.getValue(true) || mode.in("Shader")) {
                if (entity instanceof EntityAnimal || entity instanceof EntityMob) {
                    entity.setGlowing(false);
                }
            }
        });
    }

    @Override
    public void onRender(RenderEvent event) { // rgb will not affect if mode is in shader
        //GlStateManager.pushMatrix();

        mc.world.loadedEntityList.forEach(entity -> {
            if(mc.player.getDistance(entity) > distance.getValue(1)) return;

            for(EntityPlayer players : mc.world.playerEntities) {
                if(players != null && players != mc.getRenderViewEntity()) {

                }
            }

        });
    }

    private void renderSkeleton(RenderEvent event, EntityPlayer player) {



    }

}
