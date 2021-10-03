package dev.leap.frog.Module.Misc;

import dev.leap.frog.Event.Movement.EventPlayerStoppedUsingItem;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.GUI.HUD.HUDITEM.Yaw;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.InventoryUtil;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Render.Chatutil;
import dev.leap.frog.Util.Render.Renderutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemChorusFruit;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public class Test extends Module {

    Setting<Boolean> s = create("test", false); // true of false setting
    Setting<Integer> num = create("number example", 50, 0, 100); // number setting - default value - min - max
    Setting<Boolean> test = create("Consumer", true, v -> s.getValue());
    Setting<Boolean> example = create("Spotify", false);

    public Test() {
        super("Test", "Test", Type.MISC);
        setKey(Keyboard.KEY_N);
    }

    private static Yaw yaw = new Yaw();
    int startingHand;

    @Override
    public void onEnable() {
        System.out.println(mc.world.getSeed());
        Chatutil.sendClientSideMessgage("On");
        MinecraftForge.EVENT_BUS.register(yaw);
    }

    @Override
    public void onDisable() {
        Chatutil.sendClientSideMessgage("OFF");
        MinecraftForge.EVENT_BUS.unregister(yaw);
    }

    @Override
    public void onUpdate() {



    }

    @Override
    public void onRender() {

    }
}
