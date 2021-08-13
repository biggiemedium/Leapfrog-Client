package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.LeapFrogEvent;
import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Timer;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;

public class CrystalAura extends Module {
    public CrystalAura() {
        super("CrystalAura", "Places Crystals To Kill Players", Type.COMBAT);
    }



    Setting<Boolean> breakCrystal = create("Break", true);
    Setting<Boolean> placeCrystal = create("Place", true);

    Setting<Float> breakDistance = create("BreakDistance", 4.0f, 0.0f, 5.0f);
    Setting<Float> placeDistance = create("PlaceDistance", 4.0f, 0.0f, 5.0f);

    Setting<armSwing> swing = create("Swing", armSwing.Main);
    Setting<rotationMode> rotation = create("Rotation", rotationMode.NoRotate);
    Setting<Integer> delay = create("Delay", 1, 0, 10);
    Setting<Boolean> stopWhileSneak = create("Sneak Stop", false);

    Setting<Integer> r = create("Red", 255, 0, 255);
    Setting<Integer> g = create("Green", 255, 0, 255);
    Setting<Integer> b = create("Blue", 255, 0, 255);
    Setting<Integer> a = create("Alpha", 255, 0, 255);
    Setting<Boolean> displayBlockPlace = create("Show place", true);
    Setting<Boolean> chams = create("Chams", true);

    private boolean isPlacing = false;
    private boolean mainHand = false;
    private boolean offHand = false;

    private Timer timer = new Timer();

    private enum armSwing {
        Main,
        Offhand
    }

    private enum rotationMode {
        NoRotate,
        None
    }

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

        if(displayBlockPlace.getValue()) {

        }

    }

    private boolean shouldPause() {

        if(stopWhileSneak.getValue() && mc.player.isSneaking()) {
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
