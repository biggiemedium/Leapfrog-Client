package dev.leap.frog.Module.Misc;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

/*

No clue if this works, im high rn - px

 */

public class MiddleClickPearl extends Module {

    public MiddleClickPearl() {
        super("Middle click pearl", "Throws a pearl for you when you press your middle mouse button", Type.MISC);
    }

    Setting<Boolean> inventory = create("Inventory", false);
    Setting<Boolean> spoof = create("Spoof", false);

    private boolean clicked = false;

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck() || mc.player.inventory == null || getPearlSlotHotbar() == -1) return;

        if(Mouse.isButtonDown(2)) {
            if(!inventory.getValue() && !spoof.getValue()) {
                RayTraceResult ray = mc.objectMouseOver;
                if(mc.objectMouseOver == null) return;
                if(ray.typeOfHit == RayTraceResult.Type.MISS) {
                    int startHand = mc.player.inventory.currentItem;
                    assert getPearlSlotHotbar() != -1;

                    mc.player.inventory.currentItem = getPearlSlotHotbar();
                    mc.rightClickMouse();
                    mc.player.inventory.currentItem = startHand;
                }
            }
        }

    }

    @EventHandler
    private Listener<InputEvent.MouseInputEvent> eventListener = new Listener<>(event -> {

    });

    int getPearlSlot() {
        int pearlSlot = -1;
        for (int i = 45; i > 0; i--) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ENDER_PEARL) {
                pearlSlot = i;
                break;
            }
        }
        return pearlSlot;
    }

    int getPearlSlotHotbar() {
        int pearlSlot = -1;
        for (int i = 9; i > 0; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ENDER_PEARL) {
                pearlSlot = i;
                break;
            }
        }
        return pearlSlot;
    }
}