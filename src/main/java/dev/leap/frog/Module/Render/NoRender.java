package dev.leap.frog.Module.Render;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import net.minecraft.init.MobEffects;

public class NoRender extends Module {

    public NoRender() {
        super("NoRender", "Prevents rendering of certain things", Type.RENDER);
    }

    Settings potions = create("Potions", "Potions", true);

    @Override
    public void onUpdate() {
        if(mc.world == null || mc.player == null)
            return;

        if(potions.getValue(true) && mc.player.isPotionActive(MobEffects.BLINDNESS)) {
            mc.player.removeActivePotionEffect(MobEffects.BLINDNESS);
        }
        if(potions.getValue(true) && mc.player.isPotionActive(MobEffects.NAUSEA)) {
            mc.player.removeActivePotionEffect(MobEffects.NAUSEA);
        }

    }



}
