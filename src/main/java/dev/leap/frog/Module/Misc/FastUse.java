package dev.leap.frog.Module.Misc;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;

public class FastUse extends Module {

    public FastUse() {
        super("FastUse", "Lets you interact faster", Type.MISC);
    }

    Settings xp = create("XP", "XP", true);
    Settings crystals = create("Crystal", "Crystal", true);

    @Override
    public void onUpdate() {

        Item mainHand = mc.player.getHeldItemMainhand().getItem();
        Item offHand = mc.player.getHeldItemOffhand().getItem();

        if(xp.getValue(true) && mainHand instanceof ItemExpBottle || offHand instanceof ItemExpBottle) {
            mc.rightClickDelayTimer = 0;
        }

        if(crystals.getValue(true) && mainHand instanceof ItemEndCrystal || offHand instanceof ItemEndCrystal) {
            mc.rightClickDelayTimer = 0;
        }

        if(!xp.getValue(true) && !crystals.getValue(true)) {
            mc.rightClickDelayTimer = 0;
        }

    }
}
