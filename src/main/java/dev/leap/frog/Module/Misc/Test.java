package dev.leap.frog.Module.Misc;

import dev.leap.frog.Event.Network.EventPacketUpdate;
import dev.leap.frog.GUI.HUD.ArrayList;
import dev.leap.frog.GUI.HUD.HUDITEM.Speed;
import dev.leap.frog.GUI.HUD.HUDITEM.Yaw;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
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
    private static Speed speed = new Speed();

    @Override
    public void onEnable() {

        System.out.println(mc.world.getSeed());
        Chatutil.ClientSideMessgage("On");
        MinecraftForge.EVENT_BUS.register(arrayList);
        MinecraftForge.EVENT_BUS.register(yaw);
        MinecraftForge.EVENT_BUS.register(speed);
        if(s.getValue()) {
            System.out.println("Hi");
        }
    }

    @Override
    public void onDisable() {
        Chatutil.ClientSideMessgage("OFF");
        MinecraftForge.EVENT_BUS.unregister(arrayList);
        MinecraftForge.EVENT_BUS.unregister(yaw);
    }

    @Override
    public void onUpdate() {

        if(mc.player.ticksExisted < 20) return;

    }

    @EventHandler
    private Listener<EventPacketUpdate> updateListener = new Listener<>(event -> { // same thing as onUpdate
    });

}
