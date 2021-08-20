package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class KillAura extends Module {

    public KillAura() {
        super("KillAura", "Attacks entites for you", Type.COMBAT);
        setKey(Keyboard.KEY_C);
    }

    Setting<Integer> distance = create("Distance", 5, 0, 10);
    Setting<Boolean> delay = create("Delay", true); // vanilla hit delay
    Setting<Boolean> rotate = create("Rotate", true);
    Setting<Boolean> smartCheck = create("Smart check", true);
    Setting<Boolean> switchSlot = create("Switch slot", true);

    // target details

    Setting<Boolean> players = create("Players", true);
    Setting<Boolean> hostiles = create("Hostiles", false);
    Setting<Boolean> passive = create("Passive", true);

    Setting<Boolean> ignoreFriends = create("Ignore friends", true);

    @Override
    public void onUpdate() {
        if(mc.player.isDead)
            return;

        if(!(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSword) && switchSlot.getValue()) {
            swapItems();
        }
            if (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL && smartCheck.getValue())
                return;

            if (mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE && smartCheck.getValue())
                return;

            if(rotate.getValue()) {

            }

            Entity target = getTarget();

            if(target != null) {
                attack(target);
            }
    }

    private Entity getTarget() {

        Entity target = null;

        for(Entity e : mc.world.loadedEntityList) {
            if(isValid(e)) {
                target = e;
            }
        }
        return target;
    }

    private boolean isValid(Entity entity) {

        if(entity instanceof EntityLivingBase) {
            return true;
        }

        if(entity instanceof EntityPlayer && players.getValue()) {
            if(entity != mc.player && !FriendManager.isFriend(entity.getName())) {
                return true;
            }
        }

        if(passive.getValue() && entity instanceof EntityAnimal) {
            return true;
        }

        if(hostiles.getValue() && entity instanceof EntityMob) {
            return true;
        }

        if(LeapFrog.getModuleManager().getModuleName("CrystalAura").isToggled()) {
            return true;
        }

        if(LeapFrog.getModuleManager().getModuleName("Auto Trap").isToggled()) {
            return true;
        }

        return false;
    }

    private void swapItems() {
        int slot = -1;
        for(int i = 0; i < 9; i++) {
            if(mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemSword) {
                slot = i;
                mc.player.inventory.currentItem = i;
                mc.playerController.updateController();
                break;
            }
        }
    }

    private void attack(Entity entity) {
        if(mc.player.getCooledAttackStrength(0) >= 1 && delay.getValue()) {
            mc.playerController.attackEntity(mc.player, entity);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.player.resetCooldown();
        }
    }
}
