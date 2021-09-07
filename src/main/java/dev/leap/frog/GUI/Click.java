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
        frame = new ArrayList();
        int offset = 0;
        for(Module.Type type : Module.Type.values()) {
            frame.add(new Frame(type, 10 + offset, 20));
            offset += 120;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for(Frame f : frame) {
            f.render(mouseX, mouseY);
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
        super.mouseClicked(mouseX,mouseY, mouseButton);
        for(Frame f : frame) {
            f.onClick(mouseX, mouseY, mouseButton);
        }

    }
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for(Frame f: frame){
            f.onMouseReleased(mouseX, mouseY, state);
        }
    }

    public ArrayList<Frame> getFrame() {
        return frame;
    }
}
