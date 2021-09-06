package dev.leap.frog.GUI;

import dev.leap.frog.GUI.ClickGUI.Frame;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

public class Click extends GuiScreen {

    public static Click INSTANCE = new Click();

    protected Minecraft mc = Minecraft.getMinecraft();

    private ArrayList<Frame> frame;

    public Click() {
        this.frame = new ArrayList<>();
        int offset = 10;
        for(Module.Type type : Module.Type.values()) {
            Frame f = new Frame(offset, 10, type);
            this.frame.add(f);
            offset += f.getWidth() + 15;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for(Frame f : frame) {
            f.draw(mouseX, mouseY);
        }
    }

    @Override
    public void onGuiClosed() {
        //LeapFrog.getFileManager().save();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for(Frame f : frame) {
            f.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for(Frame f : frame) {
            f.mouseReleased(mouseX, mouseY, state);
        }
    }

    public ArrayList<Frame> getFrame() {
        return frame;
    }
}
