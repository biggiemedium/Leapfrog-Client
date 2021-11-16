package dev.leap.frog.Module.Misc;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalXZ;
import dev.leap.frog.Event.Movement.EventPlayerStoppedUsingItem;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.GUI.Click;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.BaritoneHandler;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.init.Items;
import net.minecraft.item.ItemChorusFruit;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.status.server.SPacketServerInfo;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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

    private boolean listening = false;
    int startingHand;
    private ICamera camera;

    @Override
    public void onEnable() {
        System.out.println(mc.world.getSeed());
        Chatutil.sendClientSideMessgage("On");
        startingHand = mc.player.inventory.currentItem;
    }

    @EventHandler
    private Listener<EventPacket.SendPacket> packetListener = new Listener<>(event -> {
    });

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

    @EventHandler
    private Listener<EventPlayerStoppedUsingItem> stoppedUsingItemListener = new Listener<>(event -> {
        if(event.getPlayer() == mc.player) {

        }
    });

    int getBowSlot() {
        int gapSlot = -1;
        for (int i = 9; i > 0; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.BOW) {
                gapSlot = i;
                break;
            }
        }
        return gapSlot;
    }
}
