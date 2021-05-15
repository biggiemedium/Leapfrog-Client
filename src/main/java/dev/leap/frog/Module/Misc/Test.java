package dev.leap.frog.Module.Misc;

import dev.leap.frog.GUI.HUD.ArrayList;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public class Test extends Module {

    Setting.b testSetting;
    Setting.i intSetting;

    public Test() {
        super("Test", "Test", Type.RENDER);
        setKey(Keyboard.KEY_N);
    }

    private static ArrayList arrayList = new ArrayList();

    @Override
    public void createSetting() {
        this.testSetting = registerB("Boolean example", "Boolean example", true); // name, configname, value: T/F
        this.intSetting = registerI("Int example", "Int example", 255, 0, 255); // name, configname, default value, min, max
    }

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
