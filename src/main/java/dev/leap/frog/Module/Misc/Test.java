package dev.leap.frog.Module.Misc;

import dev.leap.frog.Event.Network.EventPacketUpdate;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.GUI.HUD.HUDITEM.ArrayList;
import dev.leap.frog.GUI.HUD.HUDITEM.Yaw;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public class Test extends Module {

    Setting<Boolean> s = create("test", false); // true of false setting
    Setting<Integer> num = create("number example", 50, 0, 100); // number setting - default value - min - max

    public Test() {
        super("Test", "Test", Type.MISC);
        setKey(Keyboard.KEY_N);
    }

    private static ArrayList arrayList = new ArrayList();
    private static Yaw yaw = new Yaw();

    int x;

    @Override
    public void onEnable() {

        x = mc.player.inventory.currentItem;

        System.out.println(mc.world.getSeed());
        Chatutil.sendClientSideMessgage("On");
        MinecraftForge.EVENT_BUS.register(arrayList);
        MinecraftForge.EVENT_BUS.register(yaw);
        if(s.getValue()) {
            System.out.println("Hi");
        }
    }

    @Override
    public void onDisable() {
        Chatutil.sendClientSideMessgage("OFF");
        MinecraftForge.EVENT_BUS.unregister(arrayList);
        MinecraftForge.EVENT_BUS.unregister(yaw);
    }

    @Override
    public void onUpdate() {

        if(Entityutil.isPlayerInHole(mc.player) && getGapSlot() != -1) {

            mc.player.connection.sendPacket(new CPacketHeldItemChange(getGapSlot()));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.player.connection.sendPacket(new CPacketHeldItemChange(x));

        }



    }

    @Override
    public void onRender(RenderEvent event) {

    }

    @EventHandler
    private Listener<EventPacketUpdate> updateListener = new Listener<>(event -> { // same thing as onUpdate
    });

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

}
