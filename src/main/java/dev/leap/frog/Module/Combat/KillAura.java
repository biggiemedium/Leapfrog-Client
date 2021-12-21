package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Block.Blockutil;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Math.Mathutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWeb;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Collections;

public class KillAura extends Module {

    // Gonna comment things on this class because I always forget what im doing and have to rewrite this shit

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

    Setting<Boolean> renderTarget = create("Render target", true);

    Setting<Boolean> ignoreFriends = create("Ignore friends", true);

    Setting<Boolean> webTarget = create("Web target", false);
    Setting<Boolean> walls = create("Walls", true);

    private EntityPlayer target;
    private boolean spoofing = false;

    private double yaw;
    private double pitch;

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck() || mc.player.isDead) return; // default return statements
    }
}
