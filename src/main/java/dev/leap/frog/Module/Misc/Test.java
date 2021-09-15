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
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.GuiModList;
import org.lwjgl.input.Keyboard;

public class Test extends Module {

    Setting<Boolean> s = create("test", false); // true of false setting
    Setting<Integer> num = create("number example", 50, 0, 100); // number setting - default value - min - max

    public Test() {
        super("Test", "Test", Type.MISC);
        setKey(Keyboard.KEY_N);
    }

    private static Yaw yaw = new Yaw();

    int x;

    @Override
    public void onEnable() {

        x = mc.player.inventory.currentItem;

        System.out.println(mc.world.getSeed());
        Chatutil.sendClientSideMessgage("On");
        MinecraftForge.EVENT_BUS.register(yaw);
        if(s.getValue()) {
            System.out.println("Hi");
        }
    }

    @Override
    public void onDisable() {
        Chatutil.sendClientSideMessgage("OFF");
        MinecraftForge.EVENT_BUS.unregister(yaw);

        mc.entityRenderer.shaderGroup.deleteShaderGroup();
    }

    @Override
    public void onUpdate() {

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
