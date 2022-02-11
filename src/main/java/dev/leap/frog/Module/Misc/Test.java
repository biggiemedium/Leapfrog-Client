package dev.leap.frog.Module.Misc;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalXZ;
import com.mojang.text2speech.Narrator;
import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Event.Movement.EventPlayerStoppedUsingItem;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.GUI.Click;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.BaritoneHandler;
import dev.leap.frog.Util.Block.Blockutil;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Render.Chatutil;
import dev.leap.frog.Util.Render.Renderutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemChorusFruit;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.status.server.SPacketServerInfo;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Test extends Module {

    Setting<Boolean> s = create("test", false); // true of false setting
    Setting<Integer> num = create("number example", 50, 0, 100); // number setting - default value - min - max
    Setting<Boolean> test = create("Consumer", true, v -> s.getValue());
    Setting<Boolean> example = create("Spotify", false);

    public Test() {
        super("Test", "Test", Type.MISC);
        setKey(Keyboard.KEY_N);
    }

    private boolean listening = false;
    int startingHand;
    private ICamera camera;
    private boolean sneaking = false;

    @Override
    public void onEnable() {
        System.out.println(mc.world.getSeed());
        Chatutil.sendClientSideMessgage("On");
        startingHand = mc.player.inventory.currentItem;

        Narrator n = Narrator.getNarrator();

    }

    @Override
    public void onDisable() {
        Chatutil.sendClientSideMessgage("OFF");
    }

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck() || mc.currentScreen instanceof Click) return;
        
    }

    @Override
    public void onRender(RenderEvent event) {
    }

    public int findObbyinHotbar() {
        for (int i = 0; i < 9; i++) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }
            Block block = ((ItemBlock) stack.getItem()).getBlock();

            if (block instanceof BlockWeb) {
                return i;
            }

        }
        return -1;
    }
}
