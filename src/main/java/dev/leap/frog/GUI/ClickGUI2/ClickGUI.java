package dev.leap.frog.GUI.ClickGUI2;

import dev.leap.frog.GUI.ClickGUI2.Base.GUIFrame;
import dev.leap.frog.Module.Module;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;

public class ClickGUI extends GuiScreen {

    private ArrayList<GUIFrame> framesArray = new ArrayList<>();

    public ClickGUI() {
        int offset = 0;
        for(Module.Type t : Module.Type.values()) {
            framesArray.add(new GUIFrame(t, 10 + offset, 20));
            offset += 120;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for(GUIFrame f : framesArray) {
            f.draw(mouseX, mouseY);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for(GUIFrame f : framesArray) {
            f.mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for(GUIFrame f : framesArray) {
            f.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public ArrayList<GUIFrame> getFramesArray() {
        return framesArray;
    }
}
