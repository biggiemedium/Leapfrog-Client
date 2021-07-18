package dev.leap.frog.GUI;

import net.minecraft.client.gui.GuiScreen;

public class Click extends GuiScreen {

    public static Click INSTANCE = new Click();

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
