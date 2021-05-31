package dev.leap.frog.Module.Misc;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import net.minecraft.init.Items;

public class FastUse extends Module {

    public FastUse() {
        super("FastUse", "Lets you interact faster", Type.MISC);
    }

    Settings xp = create("XP", "XP", true);
    Settings crystals = create("Crystal", "Crystal", true);


    @Override
    public void onUpdate() {

        if(xp.getValue(true) && mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE || mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE) {
            mc.rightClickDelayTimer = 0;
        }

        if(crystals.getValue(true) && mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL || mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
            mc.rightClickDelayTimer = 0;
        }

        if(xp.getValue(false) && crystals.getValue(false)) {
            mc.rightClickDelayTimer = 0;
        }

    }
}
