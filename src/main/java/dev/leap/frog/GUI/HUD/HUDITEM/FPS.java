package dev.leap.frog.GUI.HUD.HUDITEM;

import dev.leap.frog.GUI.HUD.Component;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class FPS extends Component {
    public FPS() {
        super(50, 50, "FPS");
    }

    @Override
    public void Click(int mouseX, int mouseY, int mouseButton) {
        Gui.drawRect(50, 50, 50, 50, new Color(0.8f, 0.8f, 0.8f).getRGB());
    }
}
