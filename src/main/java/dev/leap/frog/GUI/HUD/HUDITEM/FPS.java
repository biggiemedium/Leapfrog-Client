package dev.leap.frog.GUI.HUD.HUDITEM;

import dev.leap.frog.GUI.HUD.Component;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class FPS extends Component {
    public FPS() {
        super(43, 65, "FPS");
    }
    @Override
    public void Render(int mouseX, int mouseY){
        Gui.drawRect(34,67,43,83, Color.green.getRGB());
    }


}
