package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Network.EventPacketUpdate;
import dev.leap.frog.Event.World.EventEntityRemoved;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Timer;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CrystalAura extends Module {

    public CrystalAura() {
        super("CrystalAura", "Places Crystals To Kill Players", Type.COMBAT);
    }

    /*
    Placement boolean options
     */

    private Setting<Boolean> breakCrystal = create("Break", true);
    private Setting<Boolean> placeCrystal = create("Place", true);

    /*
    Placement Number options
     */
    private Setting<Float> placeRange = create("Place range", 4.0f, 0.1f, 5.0f);
    private Setting<Float> breakRange = create("Break range", 4.0f, 0.1f, 5.0f);


    private ConcurrentHashMap<EntityEnderCrystal, Integer> crystalMap;
    private ArrayList<BlockPos> placedList;
    private List<Entity> ignoreList;
    private Timer visualTimer = new Timer();

    private int crystalSlot;

    @Override
    public void onEnable() {
        this.crystalMap = new ConcurrentHashMap<>();
        this.crystalMap.clear();

        this.ignoreList = new ArrayList<>();

        this.placedList = new ArrayList<>();
        this.placedList.clear();

        this.crystalSlot = mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL ? mc.player.inventory.currentItem : -1;
    }

    private void handleEntityMaps() {
        for(Entity e : mc.world.loadedEntityList) {
            if(e == mc.player || e == null) continue;
            if(e instanceof EntityEnderCrystal) {

            }
        }
    }

    private void crystalAura() {
        if(visualTimer.passed(1000)) {
            visualTimer.reset();
            this.crystalMap.clear();
        }
    }

    @EventHandler
    private Listener<EventPacketUpdate> updateListener = new Listener<>(event -> {
        if(mc.world == null || mc.player == null) return;
        this.crystalSlot = mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL ? mc.player.inventory.currentItem : -1;

    });

    @EventHandler
    private Listener<EventPacket.ReceivePacket> crystalPacketListener = new Listener<>(event -> {
        if(event.getPacket() instanceof SPacketSoundEffect) {
            SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
            Vec3d vec = new Vec3d(packet.getX(), packet.getY(), packet.getZ());
            if(packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                mc.world.loadedEntityList.forEach(entity -> {
                    if(entity instanceof EntityEnderCrystal) {
                        if(entity.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0f) {
                            entity.setDead();
                        }
                        placedList.removeIf(placed -> placed.getDistance((int) packet.getX(), (int) packet.getY(), (int) packet.getZ()) <= 6.0f);
                    }
                });
            }
        }
    });

    /*
    Removes null crystals off the list
     */
    @EventHandler
    private Listener<EventEntityRemoved> removedEntityListener = new Listener<>(event -> {
        if(event.getEntity() instanceof EntityEnderCrystal) {
            this.crystalMap.remove(event.getEntity());
        }
    });

    private boolean shouldPause() {

        if(Playerutil.isEating()) {
            return true;
        }



        return false;
    }
}
