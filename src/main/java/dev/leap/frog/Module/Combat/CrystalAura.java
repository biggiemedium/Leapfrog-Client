package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;

public class CrystalAura extends Module {
    public CrystalAura() {
        super("CrystalAura", "Places Crystals To Kill Players", Type.COMBAT);
    }


    Settings breakCrystal = create("Break", "Break", true);
    Settings placeCrystal = create("Place", "Place", true);
    Settings swing = create("Swing", "Swing", "MainHand", combobox("MainHand", "OffHand", "Both"));
    Settings rotation = create("Rotation", "Rotation", "Norotate", combobox("Norotate", "Off"));

    private boolean isPlacing = false;
    private boolean mainHand = false;
    private boolean offHand = false;

    @Override
    public void onEnable(){
        if(mc.player == null || mc.world == null)
            return;


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

}
