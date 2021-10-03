package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.LeapFrogEvent;
import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Block.BlockInteractionutil;
import dev.leap.frog.Util.Entity.InventoryUtil;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Render.Chatutil;
import dev.leap.frog.Util.Wrapper;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BedAura extends Module {

    public BedAura() {
        super("BedAura", "Testing to see if it works", Type.COMBAT);
    }

    Setting<Boolean> autoSwitch = create("Auto switch", true);
    Setting<Integer> range = create("Range", 5, 0, 6);
    Setting<Boolean> rotate = create("Rotate", true);
    Setting<Boolean> antiSuicide = create("AntiSuicide", true);

    private boolean shouldPlace = false;
    private int beds;
    private boolean moving = false;

    private BlockPos pos;

    @Override
    public void onEnable() {
        if (mc.player == null || mc.world == null || mc.player.dimension == 0) {
            toggle();
            return;
        }
    }

    @Override
    public void onUpdate() {
        this.beds = mc.player.inventory.mainInventory.stream().filter(stack -> stack.getItem() == Items.BED).mapToInt(ItemStack::getCount).sum();
        if(beds == 0) {
            Chatutil.sendMessage("Out of beds!");
            toggle();
            return;
        }

        if(antiSuicide.getValue() && Playerutil.getPlayerHealth() < 5) {
            return;
        }

        if(autoSwitch.getValue() && mc.player.getHeldItemMainhand().getItem() != Items.BED) {
            swap();
        }

    }

    @EventHandler
    private Listener<EventPlayerMotionUpdate> updateListener = new Listener<>(event -> {
        if(event.getEra() != LeapFrogEvent.Era.PRE) return;
        if(mc.player.isSneaking()) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }

    });

    private float getDamageMultiplied(final float damage) {
        final int diff = mc.world.getDifficulty().getDifficultyId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }

    private void swap() {
            int slot = -1;
            for(int i = 0; i < 9; i++) {
                if(mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBed) {
                    slot = i;
                    mc.player.inventory.currentItem = i;
                    mc.playerController.updateController();
                    break;
                }
            }
    }

    @Override
    public String getArrayDetails() {
        return shouldPlace ? "In combat" : "Idle";
    }
}
