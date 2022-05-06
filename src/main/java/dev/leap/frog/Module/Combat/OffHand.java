package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Movement.EventPlayerStoppedUsingItem;
import dev.leap.frog.Event.Network.EventPacketUpdate;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Wrapper;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class OffHand extends Module {

    public OffHand() {
        super("OffHand", "Puts item in your offhand", Type.COMBAT);
    }

    Setting<Integer> health = create("Fallback", 13, 0, 36);
    Setting<itemOffhand> item = create("Item", itemOffhand.Gapple);
    Setting<AutoGapple> autoGapple = create("Auto Gapple", AutoGapple.Constant);
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

        if (mc.currentScreen instanceof GuiContainer || mc.world == null || mc.player == null || mc.player.ticksExisted < 10)
            return;

        if (item.getValue() == itemOffhand.Gapple) {
            if (!shouldTotem()) {
                if (!(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE)) {
                    int slotGapple = getGapSlot() < 9 ? getGapSlot() + 36 : getGapSlot();
                    if (getGapSlot() != -1) {
                        mc.playerController.windowClick(0, slotGapple, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, slotGapple, 0, ClickType.PICKUP, mc.player);
                    }
                }
            } else if (!(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)) {
                int slot = getTotemSlot() < 9 ? getTotemSlot() + 36 : getTotemSlot();
                if (getTotemSlot() != -1) {
                    mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                }
            }
        }

        if (item.getValue() == itemOffhand.Pearl) {
            if (!shouldTotem()) {
                if (!(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() == Items.ENDER_PEARL)) {
                    int pearlSlot = getPearlSlot() < 9 ? getPearlSlot() + 36 : getPearlSlot();
                    if (getPearlSlot() != -1) {
                        mc.playerController.windowClick(0, pearlSlot, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, pearlSlot, 0, ClickType.PICKUP, mc.player);
                    }
                }
            } else if (!(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)) {
                int slot = getPearlSlot() < 9 ? getPearlSlot() + 36 : getPearlSlot();
                if (getPearlSlot() != -1) {
                    mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                }
            }
        }

        if (item.getValue() == itemOffhand.Crystal) {
            if (!shouldTotem() || getItems(Items.TOTEM_OF_UNDYING) == 0) {
                if (!(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)) {
                    final int slot = getCrystalSlot() < 9 ? getCrystalSlot() + 36 : getCrystalSlot();
                    if (getCrystalSlot() != -1) {
                        mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
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

        if(!isPlayerInHole() && autoGapple.getValue() != AutoGapple.Inhole) {
            mc.gameSettings.keyBindUseItem.pressed = false;
        }

    }

    @EventHandler
    private Listener<EventPacketUpdate> listener = new Listener<>(event -> {

        if(mc.player == null || mc.world == null) return;

        // auto gapple methods

        if (autoGapple.getValue() == AutoGapple.Inhole && isPlayerInHole()) {
            if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) {
                if (mc.currentScreen == null) {
                    mc.gameSettings.keyBindUseItem.pressed = true;
                } else {
                    mc.playerController.processRightClick(mc.player, mc.world, EnumHand.OFF_HAND);
                }
            }
        }

        if(autoGapple.getValue() == AutoGapple.Constant) {
            if(mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && !Playerutil.isEating()) {
                if(mc.currentScreen == null) {
                    mc.gameSettings.keyBindUseItem.pressed = true;
                } else {
                    mc.playerController.processRightClick(mc.player, mc.world, EnumHand.OFF_HAND);
                }
            }
        }

    });

    @EventHandler
    private Listener<EventPlayerStoppedUsingItem> itemListener = new Listener<>(event -> {

        if(Playerutil.isEating()) {
            event.cancel();
        }

    });

    @Override
    public void onDisable() {

        int slot = getTotemSlot() < 9 ? getTotemSlot() + 36 : getTotemSlot();
            if (getTotemSlot() != -1) {
                mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
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
        BlockPos pos = Playerutil.getPlayerPosFloored();

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

    public static OffHand INSTANCE = new OffHand();

}
