package dev.leap.frog.GUI.ClickGUI2;

import dev.leap.frog.GUI.ClickGUI2.Base.ClickFrame;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

public class ClickGUI extends GuiScreen {

    private ArrayList<ClickFrame> frames = new ArrayList<>();

    public ClickGUI() {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for(ClickFrame f : this.frames) {
            f.draw(mouseX, mouseY);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for(ClickFrame f : this.frames) {
            f.mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for(ClickFrame f : this.frames) {
            f.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}
