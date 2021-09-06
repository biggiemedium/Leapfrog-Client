package dev.leap.frog;

import dev.leap.frog.Event.EventProcessor;
import dev.leap.frog.Manager.*;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Network.Sessionutil;
import dev.leap.frog.Manager.CapeManager;
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

@Mod(modid = LeapFrog.MODID, version = LeapFrog.VERSION)
public class LeapFrog {
    public static final String MODID = "leapfrog";
    public static final String VERSION = "1.0";

    public static final EventBus EVENT_BUS = new EventManager();

    public static final Logger log = LogManager.getLogger("leap");
    private static ModuleManager moduleManager;
    private static SettingManager settingManager;
    private static DiscordManager discordManager;
    private static CapeManager capeManager;
    public static FriendManager friendManager;
    private static EventProcessor eventManager;
    private static FileManager fileManager;
    private static HudManager hudManager;

    @EventHandler
    public void init(FMLInitializationEvent event) {

        System.out.println("Starting Client");
        MinecraftForge.EVENT_BUS.register(this);
        log.info("Starting Client");

        //register managers
        settingManager = new SettingManager(); // settings manager must come BEFORE module manager or returns null
        fileManager = new FileManager();
        moduleManager = new ModuleManager();
        eventManager = new EventProcessor();
        hudManager = new HudManager();
        capeManager = new CapeManager();
        discordManager = new DiscordManager();
        friendManager = new FriendManager();
        hudManager = new HudManager();

        fileManager.load();

        Display.setTitle("LeapFrog Client");
        Sessionutil.getInstance().setUser("halop56yt@gmail.com", "bhfwb*^GYD7HNj");
    }
    // get classes
    public static ModuleManager getModuleManager() { return moduleManager; }

    public static SettingManager getSettingManager() { return settingManager; }

    public static FileManager getFileManager() { return fileManager; }

    public static DiscordManager getDiscordManager() { return discordManager; }

    public static EventProcessor getEventProcessor() { return eventManager; }

    public static CapeManager getCapeManager() { return capeManager; }

    public static FriendManager getFriendManager() { return friendManager; }

    @SubscribeEvent
    public void key(InputEvent e) {
        if(Wrapper.getMC().world == null || Wrapper.getMC().player == null)
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
