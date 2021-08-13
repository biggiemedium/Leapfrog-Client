package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;

public class ESP extends Module {

    public ESP() {
        super("ESP", "Shows Entitys through walls", Type.RENDER);
    }

    private HashMap<EntityPlayer, float[][]> entityPlayerHashMap = new HashMap<>();

    Setting<ESPMode> mode = create("Mode", ESPMode.Shader);

    Setting<Boolean> player = create("Player", true);
    Setting<Boolean> mob = create("Mob", false);
    Setting<Boolean> crystals = create("Crystals", true);
    Setting<Boolean> invisible = create("Invisible", true);

    Setting<Integer> r = create("Red", 255, 0, 255);
    Setting<Integer> g = create("green", 255, 0, 255);
    Setting<Integer> b = create("blue", 255, 0, 255);

    Setting<Integer> distance = create("Distance", 75, 1, 255);

    private enum ESPMode {
        Shader,
        ESP,
        SKELETON,
        CSGO
    }

    @Override
    public void onUpdate() {
        if(mc.player == null || mc.world == null) return;

        mc.world.loadedEntityList.forEach(entity -> {

            if(mc.player.getDistance(entity) > distance.getValue()) return;

            if(player.getValue() && mode.getValue() == ESPMode.Shader) {
                if(entity instanceof EntityPlayer && entity != mc.player) {
                    if(FriendManager.isFriend(((EntityPlayer) entity).getDisplayNameString())) {
                        entity.setGlowing(false);
                    } else {
                        entity.setGlowing(true);
                    }
                }
            }

            if(invisible.getValue() && mode.getValue() == ESPMode.Shader) {
                if(entity instanceof EntityPlayer && entity.isInvisible() && entity != mc.player) {
                    entity.setGlowing(true);
                }
            }

            if(crystals.getValue() && mode.getValue() == ESPMode.Shader) {
                if(entity instanceof EntityEnderCrystal) {
                    entity.setGlowing(true);
                }
            }

            if(mob.getValue() && mode.getValue() == ESPMode.Shader) {
                if(entity instanceof EntityAnimal || entity instanceof EntityMob) {
                    entity.setGlowing(true);
                }
            }

            // does not toggle for some reason so this is necessary

            if(!crystals.getValue() || mode.getValue() != ESPMode.Shader) {
                if(entity instanceof EntityEnderCrystal) {
                    entity.setGlowing(false);
                }
            }

            if(!player.getValue() || mode.getValue() != ESPMode.Shader) {
                if(entity instanceof EntityPlayer) {
                    entity.setGlowing(false);
                }
            }

            if(!invisible.getValue() || mode.getValue() != ESPMode.Shader) {
                if(entity instanceof EntityPlayer && entity.isInvisible()) {
                    entity.setGlowing(false);
                }
            }

            if(!mob.getValue() || mode.getValue() != ESPMode.Shader) {
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

            if (player.getValue() || mode.getValue() == ESPMode.Shader) {
                if (entity instanceof EntityPlayer) {
                    entity.setGlowing(false);
                }
            }

            if (invisible.getValue() || mode.getValue() == ESPMode.Shader) {
                if (entity instanceof EntityPlayer && entity.isInvisible() && entity != mc.player) {
                    entity.setGlowing(false);
                }
            }

            if (crystals.getValue() || mode.getValue() == ESPMode.Shader) {
                if (entity instanceof EntityEnderCrystal) {
                    entity.setGlowing(false);
                }
            }

            if (mob.getValue() || mode.getValue() == ESPMode.Shader) {
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
            if(mc.player.getDistance(entity) > distance.getValue()) return;

            for(EntityPlayer players : mc.world.playerEntities) {
                if(players != null && players != mc.getRenderViewEntity()) {

                }
            }

        });
    }

    private void renderSkeleton(RenderEvent event, EntityPlayer player) {



    }

}
