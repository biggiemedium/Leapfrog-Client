package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Render.Chatutil;
import dev.leap.frog.Util.Timer;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class BedAura extends Module {

    public BedAura() {
        super("BedAura", "Testing to see if it works", Type.COMBAT);
    }

    Setting<Boolean> autoSwitch = create("Auto switch", true);
    Setting<Boolean> rotate = create("Rotate", true);
    Setting<TargetMode> targetMode = create("Target Mode", TargetMode.SELF);

    Setting<Integer> range = create("Range", 5, 0, 10);
    Setting<Integer> minDamage = create("Min Damage", 5, 1, 36);

    Setting<Integer> placeDelay = create("Place Delay", 1, 0, 50);
    Setting<Integer> breakDelay = create("Break Delay", 1, 0, 50);

    Setting<Boolean> silentSwitch = create("Silent Switch", false);
    Setting<Boolean> antiSuicide = create("AntiSuicide", true);
    Setting<Integer> antiSuicideHealth = create("Suicide Health", 5, 0, 20);
    Setting<Boolean> friends = create("Friends", false);

    /*
    Misc
     */
    private int beds;
    private boolean moving = false;

    /*
    Slot handling
     */
    private int bedSlot;
    private int previousSlot;

    /*
    Timers
     */
    private Timer timer;
    private Timer placeTimer;
    private Timer breakTimer;

    /*
    Others
     */
    private BlockPos pos;
    private ArrayList<BlockPos> selfPlacedbeds;
    private Entity currentTarget;

    private enum TargetMode {
        ALL, SELF
    }

    @SuppressWarnings("collection")
    @Override
    public void onEnable() {
        if (mc.player == null || mc.world == null || mc.player.dimension == 0) {
            toggle();
            return;
        }

        this.selfPlacedbeds = new ArrayList<>();
        this.selfPlacedbeds.clear();
        this.bedSlot = -1;
        this.previousSlot = -1;
        timer = new Timer();
        timer.reset();

        placeTimer = new Timer();
        placeTimer.reset();

        breakTimer = new Timer();
        breakTimer.reset();
    }

    @Override
    public void onUpdate() {
        this.beds = mc.player.inventory.mainInventory.stream().filter(stack -> stack.getItem() == Items.BED).mapToInt(ItemStack::getCount).sum();
        if(mc.player == null || mc.world == null) return;
        if(beds == 0 || mc.player.dimension == 0) {
            Chatutil.sendMessage("Out of beds!");
            toggle();
            return;
        }

        handleBedSlot();
        this.currentTarget = getTarget();

        if(bedSlot == -1) {
            return;
        }

        if(antiSuicide.getValue() && Playerutil.getPlayerHealth() < antiSuicideHealth.getValue()) {
            return;
        }

        if(autoSwitch.getValue() && mc.player.getHeldItemMainhand().getItem() != Items.BED) {
            this.previousSlot = mc.player.inventory.currentItem;
            if(silentSwitch.getValue()) {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(bedSlot));
            } else {
                mc.player.inventory.currentItem = bedSlot;
                mc.playerController.updateController();
            }
        }

        if(placeTimer.passed(placeDelay.getValue())) {
            placeTimer.reset();
            placeBeds();
        }
        if(breakTimer.passed(breakDelay.getValue())) {
            breakTimer.reset();
            breakBeds();
        }
    }

    @Override
    public void onDisable() {
        //this.selfPlacedbeds.clear();

        if(autoSwitch.getValue() && !(mc.player.inventory.currentItem == previousSlot)) {
            if(previousSlot != -1) { // ensure that the old slot is still good
                if(silentSwitch.getValue()) {
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(previousSlot));
                } else {
                    mc.player.inventory.currentItem = previousSlot;
                    mc.playerController.updateController();
                }
            }
        }
    }

    @EventHandler
    private Listener<EventPlayerMotionUpdate> updateListener = new Listener<>(event -> {
        if(mc.player.isSneaking()) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    });

    private void breakBeds() {

        switch (targetMode.getValue()) {
            case ALL:

            case SELF:


        }

        if(mc.player.isSneaking()) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }

    private void placeBeds() {

        this.targetPositionsTo(this.currentTarget).forEach(position -> {
            
        });
    }

    private void handleBedSlot() {
            int slot = -1;
            for(int i = 0; i < 9; i++) {
                if(mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBed) {
                    slot = i;
                    this.bedSlot = i;
                    break;
                }
            }
    }

    private Entity getTarget() {
        for(Entity targets : mc.world.loadedEntityList) {
            if(targets == mc.player) continue;
            if(targets == null) continue;
            if(targets instanceof EntityPlayer) {
                if(((EntityPlayer) targets).isCreative()) continue;
                if(!friends.getValue() && FriendManager.isFriend(targets.getName())) continue;
                if(Entityutil.isDev(targets)) continue; // he he he

                return targets;
            }
        }

        return null;
    }

    private ArrayList<BlockPos> targetPositionsTo(Entity entity) {
        ArrayList<BlockPos> positions = new ArrayList<>();

        positions.stream().filter(this::canPlaceBed).sorted(Comparator.comparing(p -> 1 - (Entityutil.calculateDamage(p.up().getX(), p.up().getY(), p.up().getZ(), entity)))).collect(Collectors.toList());

        return positions;
    }

    @Override
    public String getArrayDetails() {
        return this.currentTarget != null ? "In combat" : "Idle";
    }

    private boolean canPlaceBed(BlockPos blockPos) {
        if (mc.world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR) {
            return false;
        }

        if (mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR) {
            return false;
        }

        return mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(blockPos)).isEmpty();
    }

    public Entity getCurrentTarget() {
        return this.currentTarget;
    }
}
