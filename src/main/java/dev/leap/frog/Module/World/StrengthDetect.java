package dev.leap.frog.Module.World;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;

import java.util.ArrayList;

public class StrengthDetect extends Module {

    public StrengthDetect() {
        super("StrengthDetect", "Detects if player has strength", Type.WORLD);
    }

    Setting<Boolean> strength = create("Strength", true);
    Setting<Boolean> weakness = create("Weakness", true);

    private ArrayList<EntityPlayer> strengthPlayers = new ArrayList<>();
    private ArrayList<EntityPlayer> weaknessPlayers = new ArrayList<>();

    @Override
    public void onUpdate() {

        if(mc.player == null || mc.world == null) return;

        if(strength.getValue()) {
            for (EntityPlayer player : mc.world.playerEntities) {
                    if (!strengthPlayers.contains(player) && player.isPotionActive(MobEffects.STRENGTH) && !player.getName().equals(mc.player.getName())) {
                        Chatutil.ClientSideMessgage(player.getName() + " Has strength!");
                        strengthPlayers.add(player);
                    }
                    if (!(player.isPotionActive(MobEffects.STRENGTH)) && strengthPlayers.contains(player) && !player.getName().equals(mc.player.getName())) {
                        Chatutil.ClientSideMessgage(player.getName() + " No longer has strength!");
                        strengthPlayers.remove(player);
              }
            }
        }

        if(weakness.getValue()) {
            for (EntityPlayer player : mc.world.playerEntities) {
                if (!weaknessPlayers.contains(player) && player.isPotionActive(MobEffects.WEAKNESS) && !player.getName().equals(mc.player.getName())) {
                    Chatutil.ClientSideMessgage(player.getName() + " Has weakness!");
                    weaknessPlayers.add(player);
                }
                if (!(player.isPotionActive(MobEffects.WEAKNESS)) && weaknessPlayers.contains(player) && !player.getName().equals(mc.player.getName())) {
                    Chatutil.ClientSideMessgage(player.getName() + " No longer has weakness!");
                    weaknessPlayers.remove(player);
                }
            }
        }

    }
}
