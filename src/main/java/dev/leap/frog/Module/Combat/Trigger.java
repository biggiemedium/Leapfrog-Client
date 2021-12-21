package dev.leap.frog.Module.Combat;

import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;

public class Trigger extends Module {

    public Trigger() {
        super("Trigger", "Attacks target when mouse is over player", Type.COMBAT);
    }

    Setting<Boolean> playersOnly = create("Players only", true);

    @Override
    public void onUpdate() {
        RayTraceResult ray = mc.objectMouseOver;
        if(mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null) {
            if (ray.typeOfHit == RayTraceResult.Type.ENTITY) {
                Entity e = mc.objectMouseOver.entityHit;

                if (playersOnly.getValue() && !(e instanceof EntityPlayer)) return;
                if (!e.isDead && !FriendManager.isFriend(e.getName())) {
                    attack(e);
                }
            }
        }
    }

    private void attack(Entity entity) {
        if(mc.player.getCooledAttackStrength(0.0f) >= 1) {
            mc.playerController.attackEntity(mc.player, entity);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

}
