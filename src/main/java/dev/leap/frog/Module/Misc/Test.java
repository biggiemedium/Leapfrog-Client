package dev.leap.frog.Module.Misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.GUI.HUD.ArrayList;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public class Test extends Module {

     // note: settings still return null
    Settings s = create("test", "test", false); // true of false setting
    Settings num = create("number example", "Number example", 50, 0, 100); // number setting - default value - min - max
    Settings combobox = create("combobox example", "combobox example", "default combo", combobox("1", "2", "3")); // combobox setting - default value - item - item- item

    public Test() {
        super("Test", "Test", Type.RENDER);
        setKey(Keyboard.KEY_N);
    }

    private static ArrayList arrayList = new ArrayList();

    @Override
    public void onEnable() {
        Chatutil.ClientSideMessgage("On");
        MinecraftForge.EVENT_BUS.register(arrayList);
        if(s.getValue(true)) {
            System.out.println("Hi");
        }
    }

    @Override
    public void onDisable() {
        Chatutil.ClientSideMessgage("OFF");
        MinecraftForge.EVENT_BUS.unregister(arrayList);
    }

    @Override
    public void onUpdate() {

    }
}
