package dev.leap.frog.GUI.HUD;

import net.minecraft.client.gui.GuiScreen;

public class Hud extends GuiScreen {

    private int x;
    private int y;

    private int width;
    private int height;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
