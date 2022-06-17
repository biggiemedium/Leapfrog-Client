package dev.leap.frog.GUI.Effects;

import dev.leap.frog.LeapFrog;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;

import java.awt.*;

public class TaskBarLeapfrog {

    ResourceLocation location = new ResourceLocation("Leapfrog.PNG");

    public TaskBarLeapfrog() {
        if(!SystemTray.isSupported()) {
            LeapFrog.log.info("SystemTray is not supported");
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();
        Image img = Toolkit.getDefaultToolkit().createImage(location.getResourcePath());

    }

}
