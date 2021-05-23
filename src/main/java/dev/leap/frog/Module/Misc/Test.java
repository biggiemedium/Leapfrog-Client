package dev.leap.frog.Module.Misc;

import dev.leap.frog.GUI.HUD.ArrayList;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.util.Set;

public class Test extends Module {

     // note: settings still return null

    public Test() {
        super("Test", "Test", Type.RENDER);
        setKey(Keyboard.KEY_N);
    }

    private static ArrayList arrayList = new ArrayList();

    @Override
    public void onEnable() {
        Chatutil.ClientSideMessgage("On");
        MinecraftForge.EVENT_BUS.register(arrayList);
    }

    @Override
    public void onDisable() {
        Chatutil.ClientSideMessgage("OFF");
        MinecraftForge.EVENT_BUS.unregister(arrayList);
    }
}
