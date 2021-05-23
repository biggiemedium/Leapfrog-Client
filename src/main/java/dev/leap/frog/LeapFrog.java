package dev.leap.frog;

import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.util.UUIDTypeAdapter;
import dev.leap.frog.Manager.*;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.SessionChanger;
import dev.leap.frog.Util.Wrapper;
import me.zero.alpine.fork.bus.EventBus;
import me.zero.alpine.fork.bus.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import com.mojang.authlib.Agent;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;

import java.net.Proxy;

@Mod(modid = LeapFrog.MODID, version = LeapFrog.VERSION)
public class LeapFrog {
    public static final String MODID = "leapfrog";
    public static final String VERSION = "1.0";

    public static final EventBus EVENT_BUS = new EventManager();

    public static final Logger log = LogManager.getLogger("leap");

    private static ModuleManager moduleManager;
    private static SettingsManager settingsManager;
    private static DiscordManager discordManager = new DiscordManager();
    private static FileManager fileManager;
    private static HudManager hudManager;

    @EventHandler
    public void init(FMLInitializationEvent event) {

        SessionChanger.getInstance().setUser("halop56yt@gmail.com", "@s93dm87dhwy&SKSK^@$*");
        System.out.println("Starting Client");
        MinecraftForge.EVENT_BUS.register(this);

      //register managers
        hudManager = new HudManager();
        moduleManager = new ModuleManager();
        settingsManager = new SettingsManager();
        fileManager = new FileManager();


        discordManager.Start();
        fileManager.createDir();


    }


    
    // get classes
    public static ModuleManager moduleManager() {
        return moduleManager;
    }

    public static SettingsManager getSettingsManager() { return settingsManager;}

    public static FileManager getFileManager() { return fileManager; }

    public static DiscordManager getDiscordManager() {
        return discordManager;
    }

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
