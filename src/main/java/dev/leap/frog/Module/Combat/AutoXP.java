package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Entity.Playerutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.apache.http.util.EntityUtils;

import java.util.List;
import java.util.Map;

public class AutoXP extends Module {

    public AutoXP() {
        super("XP", "Xp", Type.COMBAT);
    }

    Setting<XPMode> mode = create("Mode", XPMode.Packet);
    Setting<Boolean> silentSwitch = create("Silent Switch", true);
    Setting<Boolean> onSneak = create("Sneak Only", false);
    Setting<Integer> armourState = create("Armour Health", 50, 0, 99);
    Setting<Boolean> centre = create("Centre", true);
    Setting<Boolean> boundingBox = create("Bounding Box", true);

    private int startingHand;
    private int currentHand;
    private List<ItemStack> armour;

    private enum XPMode {
        FootXP,
        Packet
    }

    @EventHandler
    public Listener<EventPacket.SendPacket> listener = new Listener<>(event -> {
        this.currentHand = mc.player.inventory.currentItem;
        if(event.getPacket() instanceof CPacketPlayerTryUseItem && mode.getValue() == XPMode.FootXP) {
            if(onSneak.getValue() && mc.player.isSneaking()) {
                if (mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
                    CPacketPlayer packet = (CPacketPlayer) event.getPacket();
                    packet.pitch = (float) 90;
                }
            } else if(!onSneak.getValue() && armourCheck() && mc.gameSettings.keyBindUseItem.isKeyDown() && mode.getValue() == XPMode.FootXP) {
                if (mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
                    CPacketPlayer packet = (CPacketPlayer) event.getPacket();
                    packet.pitch = (float) 90;
                }
            }
        }
    });

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) return;

        if(boundingBox.getValue()) { // didnt check if method works test in future - px
            for(EntityPlayer player : mc.world.playerEntities) {
                if(player != mc.player && player != null) {
                    if(mc.player.getDistance(player) > 1) {
                        return;
                    }
                }
            }
        }


        this.armour = mc.player.inventory.armorInventory;
        if(onSneak.getValue() && mc.player.isSneaking()) {
            if (getXPSlot() != -1 && mode.getValue() == XPMode.Packet) {
                if(centre.getValue() && mc.player.onGround) {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ), mc.player.onGround));
                    mc.player.setPosition(Math.floor(mc.player.posX + 0.5d), Math.floor(mc.player.posY), Math.floor(mc.player.posZ + 0.5d));
                }
                if(silentSwitch.getValue()) {
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(getXPSlot()));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(currentHand));
                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, 90.0f, mc.player.onGround));
                } else {
                    swap();
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, 90.0f, mc.player.onGround));
                }
            }
        } else if(!onSneak.getValue() && armourCheck() && this.isToggled()) {
            if (getXPSlot() != -1 && mode.getValue() == XPMode.Packet) {
                if(silentSwitch.getValue()) {
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(getXPSlot()));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(currentHand));
                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, 90.0f, mc.player.onGround));
                } else {
                    swap();
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, 90.0f, mc.player.onGround));
                }
            }
        }

        for(int i = 0; i < armour.size(); i++) {
            ItemStack stack = armour.get(i);
            if(stack.isEmpty || stack == null) continue;
            float damageOnArmor = (float) (stack.getMaxDamage() - stack.getItemDamage());
            float damagePercent = 100 - (100 * (1 - damageOnArmor / stack.getMaxDamage()));

            if(damagePercent <= armourState.getValue()) {
                toggle();
            }
        }
    }

    @Override
    public void onEnable() {
        startingHand = mc.player.inventory.currentItem;
        this.armour = mc.player.inventory.armorInventory;
    }

    @Override
    public void onDisable() {
        mc.player.inventory.currentItem = startingHand;
        mc.player.connection.sendPacket(new CPacketHeldItemChange(startingHand));
        mc.rightClickDelayTimer = 4;
    }

    private boolean armourCheck() {
        boolean shouldRepair = false;
        for (int i = 0; i < armour.size(); i++) {
            ItemStack itemStack = armour.get(i);
            if (itemStack.isEmpty) {
                continue;
            }

            float damageOnArmor = (float) (itemStack.getMaxDamage() - itemStack.getItemDamage());
            float damagePercent = 100 - (100 * (1 - damageOnArmor / itemStack.getMaxDamage()));

            if(damagePercent <= armourState.getValue()) {
                shouldRepair = true;
            }
        }
        return shouldRepair;
    }

    public void swap() {
        int slot = -1;
        for(int i = 0; i < 9; i++) {
            if(mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemExpBottle) {
                slot = i;
                mc.player.inventory.currentItem = i;
                mc.playerController.updateController();
                break;
            }
        }
    }


    public int getXPSlot() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemExpBottle)) continue;
            slot = i;
            break;
        }
        return slot;
    }
}
