package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.LeapFrogEvent;
import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import dev.leap.frog.Util.Timer;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;

import java.util.concurrent.ConcurrentHashMap;

public class CrystalAura extends Module {
    public CrystalAura() {
        super("CrystalAura", "Places Crystals To Kill Players", Type.COMBAT);
    }



    Settings breakCrystal = create("Break", "Break", true);
    Settings placeCrystal = create("Place", "Place", true);

    Settings breakDistance = create("BreakDistance", "BreakDistance", 4.0f, 0.0f, 5.0f);
    Settings placeDistance = create("PlaceDistance", "PlaceDistance", 4.0f, 0.0f, 5.0f);

    Settings swing = create("Swing", "Swing", "MainHand", combobox("MainHand", "OffHand", "Both"));
    Settings rotation = create("Rotation", "Rotation", "Norotate", combobox("Norotate", "Off"));
    Settings delay = create("Delay", "Delay", 1, 0, 10);
    Settings stopWhileSneak = create("Sneak Stop", "Sneak Stop", false);

    Settings r = create("Red", "Red", 255, 0, 255);
    Settings g = create("Green", "Green", 255, 0, 255);
    Settings b = create("Blue", "Blue", 255, 0, 255);
    Settings a = create("Alpha", "Alpha", 255, 0, 255);
    Settings displayBlockPlace = create("Show place", "Show place", true);
    Settings chams = create("Chams", "Chams", true);

    private boolean isPlacing = false;
    private boolean mainHand = false;
    private boolean offHand = false;

    private Timer timer = new Timer();

    @Override
    public void onUpdate() {

        if(mc.player == null || mc.world == null || getCrystalSlot() == -1) {
            return;
        }



    }

    @EventHandler
    private Listener<EventPacket.ReceivePacket> packetListener = new Listener<>(event -> {
        if(event.getPacket() instanceof SPacketSoundEffect) {
            SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
            if(packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for(Entity e : mc.world.loadedEntityList) {
                    if(e instanceof EntityEnderCrystal) {
                        if(e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6) {
                            e.setDead();
                        }
                    }
                }

            }
        }

    });

    @EventHandler
    public Listener<EventPlayerMotionUpdate> MotionListener = new Listener<>(event -> {

        if(event.getEra() != LeapFrogEvent.Era.PRE) {
            return;
        }

    });

    int getCrystalSlot() {
        int crystalSlot = -1;
        for (int i = 45; i > 0; i--) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
                crystalSlot = i;
                break;
            }
        }
        return crystalSlot;
    }


    

    @Override
    public void onRender(RenderEvent event) {
        // visualize

        if(displayBlockPlace.getValue(true)) {

        }

    }

    private boolean shouldPause() {

        if(stopWhileSneak.getValue(true) && mc.player.isSneaking()) {
            return true;
        }

        if(LeapFrog.getModuleManager().getModuleName("Auto Trap").isToggled()) {
            return true;
        }

        return false;
    }

    private boolean isValid(EntityEnderCrystal e) {
        if(e == null || e.isDead) {
            return false;
        }

        return true;
    }

    private void placeCrystals() {

    }

    private void breakCrystals() {

    }

}
