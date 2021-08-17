package dev.leap.frog.GUI.ClickGUI.Subbutton;

import dev.leap.frog.GUI.ClickGUI.ModuleButton;
import dev.leap.frog.Util.Listeners.IComponent;
import net.minecraft.client.gui.Gui;

import java.io.IOException;

public class BindButton extends WidgetHandler {

    private boolean binding;

    public BindButton(int x, int y, ModuleButton moduleButton) {
        super(x, y, moduleButton.getWidth() - 2, moduleButton.getHeight() - 2, moduleButton);
    }

    @Override
    public void render() {
        Gui.drawRect(getButton().getX(), getButton().getY() + getButton().getOffset(), getButton().getWidth(), getButton().getHeight(), 0xffffff); //  idk offset might fuck things up
    }
}
