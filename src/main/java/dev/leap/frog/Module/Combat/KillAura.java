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
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.input.Keyboard;

public class KillAura extends Module {

    public KillAura() {
        super("KillAura", "Attacks entites for you", Type.COMBAT);
        INSTANCE = this;
    }

    public static KillAura INSTANCE = new KillAura();

    Setting<Integer> distance = create("Distance", 5, 0, 10);
    Setting<Boolean> delay = create("Delay", true); // vanilla hit delay
    Setting<Boolean> rotate = create("Rotate", true);
    Setting<Boolean> smartCheck = create("Smart check", true);
    Setting<Boolean> switchSlot = create("Switch slot", true);

    Setting<Boolean> players = create("Players", true);
    Setting<Boolean> hostiles = create("Hostiles", false);
    Setting<Boolean> passive = create("Passive", true);

    Setting<Boolean> ignoreFriends = create("Ignore friends", true);

    Setting<Boolean> webTarget = create("Web target", false);

    private EntityPlayer target;

    @Override
    public void onUpdate() {

        if(mc.player == null || mc.player.isDead || mc.world.loadedEntityList.isEmpty()) return;

        if(switchSlot.getValue() && mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
            swapItems();
        }

        if(webTarget.getValue() && !target.isInWeb) {
            LeapFrog.getModuleManager().getModule(AutoWeb.class).setToggled(true);

        } else if(target.isInWeb) {
            LeapFrog.getModuleManager().getModule(AutoWeb.class).setToggled(false);
        }

    }

    public EntityPlayer getTarget() {
        return target;
    }

    public void setTarget(EntityPlayer target) {
        this.target = target;
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
}
