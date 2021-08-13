package dev.leap.frog.Module.Combat;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Render.Chatutil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class OffHand extends Module {

    public OffHand() {
        super("OffHand", "Puts item in your offhand", Type.COMBAT);
    }

    Setting<Integer> health = create("Fallback", 13, 0, 36);
    Setting<itemOffhand> item = create("Item", itemOffhand.Gapple);
    Setting<AutoGapple> autoGapple = create("Auto Gapple", AutoGapple.None);
    Setting<Boolean> msgUser = create("Notify", true);
    Setting<Boolean> lethalCrystal = create("Lethal crystal", true);

    private enum itemOffhand {
        Gapple,
        Crystal,
        Pearl
    }

    private enum AutoGapple {
        None,
        Constant,
        Inhole
    }

    @Override
    public void onUpdate() { // keep only GUI screen of GUIContainer or it will be slow
        if(mc.currentScreen instanceof GuiContainer || mc.world == null || mc.player == null || mc.player.ticksExisted < 10)
            return;

        if(LeapFrog.getModuleManager().getModuleName("Auto Totem").isToggled()) {
            LeapFrog.getModuleManager().getModuleName("Auto Totem").setToggled(false);
        }

        if(item.getValue() == itemOffhand.Gapple) {
            if (!shouldTotem()) {
                if (!(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE)) {
                    final int slotGapple = getGapSlot() < 9 ? getGapSlot() + 36 : getGapSlot();
                    if (getGapSlot() != -1) {
                        mc.playerController.windowClick(0, slotGapple, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, slotGapple, 0, ClickType.PICKUP, mc.player);
                    }
                }
            } else if (!(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)) {
                final int slot = getTotemSlot() < 9 ? getTotemSlot() + 36 : getTotemSlot();
                if (getTotemSlot() != -1) {
                    mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                }
            }
        }

        if(item.getValue() == itemOffhand.Pearl) {
            if(!shouldTotem()) {
                if(!(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() == Items.ENDER_PEARL)) {
                    final int pearlSlot = getPearlSlot() < 9 ? getPearlSlot() + 36 : getPearlSlot();
                    if(getPearlSlot() != -1) {
                        mc.playerController.windowClick(0, pearlSlot, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, pearlSlot, 0, ClickType.PICKUP, mc.player);
                    }
                }
            } else if (!(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)) {
                final int slot = getPearlSlot() < 9 ? getPearlSlot() + 36 : getPearlSlot();
                if(getPearlSlot() != -1) {
                    mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                }
            }
        }

        if(item.getValue() == itemOffhand.Crystal) {
            if (!shouldTotem() || getItems(Items.TOTEM_OF_UNDYING) == 0) {
                if (!(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)) {
                    final int slot = getCrystalSlot() < 9 ? getCrystalSlot() + 36 : getCrystalSlot();
                    if (getCrystalSlot() != -1) {
                        mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                    }
                }
            }
            else if (!(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)) {
                final int slot = getTotemSlot() < 9 ? getTotemSlot() + 36 : getTotemSlot();
                if (getTotemSlot() != -1) {
                    mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                }
            }
        }

        if(msgUser.getValue()) {

        }
    }

    @Override
    public void onEnable() {
        LeapFrog.getModuleManager().getModuleName("Auto Totem").setToggled(false);

        if(msgUser.getValue()) {
            Chatutil.setModuleMessage(this);
        }
    }

    @Override
    public void onDisable() {
        LeapFrog.getModuleManager().getModuleName("Auto Totem").setToggled(true);

        final int slot = getTotemSlot() < 9 ? getTotemSlot() + 36 : getTotemSlot();
        if (getTotemSlot() != -1) {
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
        }

        if(msgUser.getValue()) {
            Chatutil.setModuleMessage(this);
        }

    }

    private boolean shouldTotem() {
        if (mc.player != null) {
            return (mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA || mc.player.getHealth() + mc.player.getAbsorptionAmount() <= health.getValue() || (lethalCrystal.getValue() && isGapplesAABBEmpty()));
        }
        return (Playerutil.getPlayerHealth()) <= health.getValue() || mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA || (lethalCrystal.getValue() && isGapplesAABBEmpty());
    }

    private boolean isEmpty(BlockPos pos) {
        return mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos)).stream().noneMatch(e -> e instanceof EntityEnderCrystal);
    }

    public static int getItems(Item i) {
        return Wrapper.getMC().player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum() + Wrapper.getMC().player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum();
    }

    private boolean isGapplesAABBEmpty() {
        return !isEmpty(mc.player.getPosition().add(1, 0, 0)) || !isEmpty(mc.player.getPosition().add(-1, 0, 0)) || !isEmpty(mc.player.getPosition().add(0, 0, 1)) || !isEmpty(mc.player.getPosition().add(0, 0, -1)) || !isEmpty(mc.player.getPosition());
    }

    private boolean isPlayerInHole() {
        BlockPos pos = Playerutil.GetLocalPlayerPosFloored();

        return mc.world.getBlockState(pos.east()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(pos.west()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(pos.north()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(pos.south()).getBlock() != Blocks.AIR;
    }

    int getGapSlot() {
        int gapSlot = -1;
        for (int i = 45; i > 0; i--) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.GOLDEN_APPLE) {
                gapSlot = i;
                break;
            }
        }
        return gapSlot;
    }

    int getPearlSlot() {
        int gapSlot = -1;
        for (int i = 45; i > 0; i--) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ENDER_PEARL) {
                gapSlot = i;
                break;
            }
        }
        return gapSlot;
    }

    int getCrystalSlot() {
        int crystalSlot = -1;
        for (int i = 45; i > 0; i--) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
                crystalSlot = i;
                break;
            }
        }
        return crystalSlot;
    }


    int getTotemSlot() {
        int totemSlot = -1;
        for (int i = 45; i > 0; i--) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                totemSlot = i;
                break;
            }
        }
        return totemSlot;
    }
}
