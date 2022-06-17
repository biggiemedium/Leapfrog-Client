package dev.leap.frog.Module.Misc;

import com.mojang.text2speech.Narrator;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.GUI.Click;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.BlockWeb;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

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

        testBlockPlace();

    }

    @EventHandler
    private Listener<EventPacket.SendPacket> packetListener = new Listener<>(event -> {
        
    });

    @Override
    public void onRender(RenderEvent event) {

        double xt = mc.player.lastTickPosX + (mc.player.lastTickPosX - mc.player.posX) * event.getPartialTicks();
        double yt = mc.player.lastTickPosY + (mc.player.lastTickPosY - mc.player.posY) * event.getPartialTicks();
        double zt = mc.player.lastTickPosZ + (mc.player.lastTickPosZ - mc.player.posZ) * event.getPartialTicks();

        renderTNTTimer(event);
    }

    private void testBlockPlace() {
        BlockPos pos = mc.objectMouseOver.getBlockPos();

    }

    private void renderTNTTimer(RenderEvent event) {
        for(Entity e : mc.world.loadedEntityList) {
            if(e == null) continue;
            if(e instanceof EntityTNTPrimed) {
                EntityTNTPrimed entity = (EntityTNTPrimed) e;
                float timer = entity.getFuse();

                GL11.glPushMatrix();



                GL11.glPopMatrix();
            }
        }
    }

    private boolean canIgnite(BlockPos pos) {

        return false;
    }
}
