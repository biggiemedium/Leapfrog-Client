package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.LeapFrogEvent;
import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Event.World.EventEntityRemoved;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Module.Movement.NoRotate;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Timer;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;

public class CrystalAura extends Module {
    public CrystalAura() {
        super("CrystalAura", "Places Crystals To Kill Players", Type.COMBAT);
    }

    Setting<Float> distance = create("Distance", 5.0f, 1.0f, 10.0f); // general distance to engage with target

    Setting<Boolean> smartStop = create("Smart stop", true);
    Setting<Integer> healthStop = create("Health stop", 15, 1, 35, v -> smartStop.getValue());

    // targeting mode
    Setting<TargetMode> targetMode = create("TargetMode", TargetMode.Normal);
    Setting<Boolean> hostiles = create("Hostiles", false);

    private ConcurrentHashMap<EntityEnderCrystal, Integer> crystalMap = new ConcurrentHashMap<>();
    private Entity target;

    private enum TargetMode {
        Nearest,
        Priority,
        Weakest,
        Normal
    }

    @Override
    public void onUpdate() {
        if (UtilManager.nullCheck()) return;

        findTarget(distance.getValue());
    }

    @EventHandler
    private Listener<EventEntityRemoved> removedListener = new Listener<>(e -> {
        if(e.getEntity() instanceof  EntityEnderCrystal) {
            crystalMap.remove((EntityEnderCrystal) e.getEntity());
        }
    });

    private void findTarget(float range) {
        for(Entity player : mc.world.loadedEntityList) {
            if(player == null) continue;
            if(player == mc.player) continue;
            if(mc.player.getDistance(player) > range) continue;
            if(!hostiles.getValue() && player instanceof EntityMob) continue;


            target = player;
        }
    }

    private boolean shouldStop() {

        if(LeapFrog.getModuleManager().getModule(KillAura.class).isToggled() && smartStop.getValue()) {
            return true;
        }



        return false;
    }

    private boolean validCrystal(EntityEnderCrystal crystal) {

        if(crystal == null ||crystal.isDead) {
            return false;
        }


        return true;
    }

    @Override
    public String getArrayDetails() {
        return target.getName();
    }
}
