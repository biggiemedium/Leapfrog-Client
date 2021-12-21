package dev.leap.frog.GUI.HUD;

import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class HUD extends GuiScreen {

    public HUD() {
        frames  = new ArrayList<>();
    }

    private ArrayList<HUDFrame> frames;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public ArrayList<HUDFrame> getFrames() {
        return frames;
    }
}
