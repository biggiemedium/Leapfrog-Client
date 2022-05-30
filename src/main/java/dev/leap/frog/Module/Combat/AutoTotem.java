package dev.leap.frog.Module.Combat;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

public class AutoTotem extends Module { // TODO: Rewite so return methods + fix offhand glitches

    public AutoTotem() {
        super("Auto Totem", "Puts totem in offhand", Type.COMBAT);
        Get = this;
    }

    Setting<Boolean> soft = create("Soft", true);

    public int totems;
    boolean moving = false;
    boolean returnI = false;

    @Override
    public void onUpdate() { // if you change the instace of current screen it will slow down

        if(mc.player == null)
            return;

        if (mc.currentScreen instanceof GuiContainer || LeapFrog.getModuleManager().getModule(OffHand.class).isToggled() && OffHand.INSTANCE.health.getValue() < Playerutil.getPlayerHealth())
            return;

        if (mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING) {
            if (returnI) {
                int t = -1;
                for (int i = 0; i < 45; i++) {
                    if (mc.player.inventory.getStackInSlot(i).isEmpty()) {
                        t = i;
                        break;
                    }
                }
                if (t == -1) {
                    return;
                }
                mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
                returnI = false;
            }
            totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
            if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
                totems++;
            } else {
                if (soft.getValue() && !mc.player.getHeldItemOffhand().isEmpty()) {
                    return;
                }
                if (moving) {
                    mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                    moving = false;
                    if (!mc.player.inventory.getItemStack().isEmpty()) {
                        returnI = true;
                    }
                    return;
                }
                if (mc.player.inventory.getItemStack().isEmpty()) {
                    if (totems == 0) {
                        return;
                    }
                    int t = -1;
                    for (int i = 0; i < 45; i++) {
                        if (mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                            t = i;
                            break;
                        }
                    }
                    if (t == -1) {
                        return;
                    }
                    mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
                    moving = true;
                } else if (!soft.getValue()) {
                    int t = -1;
                    for (int i = 0; i < 45; i++) {
                        if (mc.player.inventory.getStackInSlot(i).isEmpty()) {
                            t = i;
                            break;
                        }
                    }
                    if (t == -1) {
                        return;
                    }
                    mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
                }
            }
        }
    }


    public static AutoTotem Get;

    @Override
    public String getArrayDetails() {
        return "" + this.totems;
    }
}
