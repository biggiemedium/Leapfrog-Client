package dev.leap.frog.Module.Client;

import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArmorNotify extends Module {

    public ArmorNotify() {
        super("Armor Notify", "Warns you if your about to break your armor", Type.CLIENT);
    }

    Setting<Boolean> friends = create("Friends", false);
    Setting<Integer> warnLevel = create("Warn level", 1, 50, 100);

    private boolean notified;

    @Override
    public void onEnable() {
        notified = false;
    }

    @Override
    public void onUpdate() {

        for(EntityPlayer player : mc.world.playerEntities) {
            if(player == null) continue;
            if(!friends.getValue() && FriendManager.isFriend(player.getName())) continue;
            if(player.isDead || !Entityutil.isLiving(player)) continue;

            for(ItemStack stack : mc.player.inventory.armorInventory) {
                if(stack == null) continue;
                if(stack.isEmpty()) continue;

                if(getArmourPercent(stack) <= warnLevel.getValue() && !notified) {
                    if(player == mc.player) {
                        Chatutil.removeableMessage("Your armor has reached " + getArmourPercent(stack) + " " + "percent!");
                    } else {
                        Chatutil.sendMessage("/msg " + player.getName() + " " + "Your armor is low!");
                    }
                    notified = true;
                }

                if(getArmourPercent(stack) >= warnLevel.getValue()) {
                    notified = false;
                }
            }
        }
    }

    private float getArmourPercent(ItemStack stack) {
        float damageOnArmor = (float) (stack.getMaxDamage() - stack.getItemDamage());
        float damagePercent = 100 - (100 * (1 - damageOnArmor / stack.getMaxDamage()));

        return Math.round(damagePercent);
    }

}
