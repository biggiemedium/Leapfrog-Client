package dev.leap.frog.Module.Misc;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Settings.Settings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;

public class FastUse extends Module {

    public FastUse() {
        super("FastUse", "Lets you interact faster", Type.MISC);
    }

    Setting<Boolean> xp = create("XP", true);
    Setting<Boolean> crystals = create("Crystal", true);

    @Override
    public void onUpdate() {

        Item mainHand = mc.player.getHeldItemMainhand().getItem();
        Item offHand = mc.player.getHeldItemOffhand().getItem();

        if(xp.getValue() && mainHand instanceof ItemExpBottle || offHand instanceof ItemExpBottle) {
            mc.rightClickDelayTimer = 0;
        }

        if(crystals.getValue() && mainHand instanceof ItemEndCrystal || offHand instanceof ItemEndCrystal) {
            mc.rightClickDelayTimer = 0;
        }

        if(!xp.getValue() && !crystals.getValue()) {
            mc.rightClickDelayTimer = 0;
        }

    }
}
