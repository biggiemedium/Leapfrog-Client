package dev.leap.frog;

import dev.leap.frog.Event.EventProcessor;
import dev.leap.frog.GUI.ClickGUI;
import dev.leap.frog.Manager.*;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Network.Sessionutil;
import dev.leap.frog.Util.Wrapper;
import me.zero.alpine.fork.bus.EventBus;
import me.zero.alpine.fork.bus.EventManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.io.IOException;

@Mod(modid = LeapFrog.MODID, version = LeapFrog.VERSION)
public class LeapFrog {
    public static final String MODID = "leapfrog";
    public static final String VERSION = "1.0";

    public static final EventBus EVENT_BUS = new EventManager();

    public static final Logger log = LogManager.getLogger("leap");
    public static ClickGUI clickGUI;
    private static ModuleManager moduleManager;
    private static SettingsManager settingsManager;
    private static DiscordManager discordManager = new DiscordManager();
    private static EventProcessor eventManager;
    private static FileManager fileManager;
    private static HudManager hudManager;

    @EventHandler
    public void init(FMLInitializationEvent event) {

        System.out.println("Starting Client");
        MinecraftForge.EVENT_BUS.register(this);

        //register managers
        settingsManager = new SettingsManager(); // settings manager must come BEFORE module manager or returns null
        fileManager = new FileManager();
        hudManager = new HudManager();
        moduleManager = new ModuleManager();
        clickGUI = new ClickGUI();
        eventManager = new EventProcessor();

        fileManager.loadConfig(); // file saves after client shutsdown in MixinMinecraft
        discordManager.Start();
        Display.setTitle("LeapFrog Client");
        Sessionutil.getInstance().setUser("halop56yt@gmail.com", "BD(*\"&9fuen*)INHr4");
    }

    // get classes
    public static ModuleManager getModuleManager() { return moduleManager; }

    public static SettingsManager getSettingsManager() { return settingsManager;}

    public static FileManager getFileManager() { return fileManager; }

    public static DiscordManager getDiscordManager() { return discordManager; }

    public static EventProcessor getEventManager() { return eventManager; }

    @SubscribeEvent
    public void key(InputEvent e) {
        if(Wrapper.GetMC().world == null || Wrapper.GetMC().player == null)
            return;
        try {
            if(Keyboard.isCreated()) {
                if(Keyboard.getEventKeyState()) {
                    int keyCode = Keyboard.getEventKey();
                    if(keyCode <= 0)
                        return;
                    for(Module m : moduleManager.modules) {
                        if(m.getKey() == keyCode && keyCode > 0) {
                            m.toggle();
                        }
                    }
                }
            }
        } catch (Exception q) { q.printStackTrace(); }
    }
}
