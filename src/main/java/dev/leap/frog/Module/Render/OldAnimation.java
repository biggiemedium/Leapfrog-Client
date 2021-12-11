package dev.leap.frog.Module.Render;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import net.minecraft.item.ItemSword;

public class OldAnimation extends Module {

    public OldAnimation() {
        super("Old Animation", "Displays 1.8 hit animations", Type.RENDER);
        setHidden(true);
    }

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck()) return;

        if(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9f) {
            mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
            mc.entityRenderer.itemRenderer.itemStackMainHand = mc.player.getHeldItemMainhand();
        }


    }
}
