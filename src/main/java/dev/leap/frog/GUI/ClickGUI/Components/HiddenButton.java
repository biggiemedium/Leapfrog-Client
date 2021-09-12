package dev.leap.frog.GUI.ClickGUI.Components;

import dev.leap.frog.GUI.ClickGUI.ModuleButton;
import dev.leap.frog.Util.Render.Colorutil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;

public class HiddenButton extends Component {

    public HiddenButton(int x, int y, ModuleButton button) {
        super(button, x, y, button.getWidth(), 13);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Gui.drawRect(getX(), getY(), getX() + 2, getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Gui.drawRect(getX() + 2, getY(), getX() + 2 + getWidth(), getY() + getHeight() - 1, getColor(mouseX, mouseY));
        Gui.drawRect(getX() + 2, getY() + getHeight() - 1, getX() + 2 + getWidth(), getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Wrapper.getMC().fontRenderer.drawStringWithShadow(getModuleButton().getModule().isHidden() ? "UnHide" : "Hide", getX() + 4, getY() + 2, -1);
    }

    private int getColor(int mouseX, int mouseY){
        Color color = getModuleButton().getModule().isHidden() ? Colorutil.getToggledC() : new Color(50, 50, 50, 200);
        return isHovered(mouseX, mouseY) ? (getModuleButton().getModule().isHidden() ? color.darker().darker().getRGB() : color.brighter().brighter().getRGB()) : color.getRGB();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(isHovered(mouseX, mouseY)) {
            if(button == 0) {
                getModuleButton().getModule().setHidden(!getModuleButton().getModule().isHidden());
            }
        }
    }
}
