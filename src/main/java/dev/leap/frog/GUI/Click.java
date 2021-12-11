package dev.leap.frog.GUI;

import dev.leap.frog.GUI.ClickGUI.Frame;
import dev.leap.frog.GUI.Effects.Falling;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Module.ui.ClickGUIModule;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;
import java.util.ArrayList;

public class Click extends GuiScreen {

    public static Click INSTANCE = new Click();

    protected Minecraft mc = Minecraft.getMinecraft();

    private ArrayList<Frame> frame;
    private ArrayList<Falling> circles;

    public Click() {
        frame = new ArrayList();
        int offset = 0;
        for(Module.Type type : Module.Type.values()) {
            frame.add(new Frame(type, 10 + offset, 20));
            offset += 120;
        }
        circles = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            for (int y = 0; y < 3; y++) {
                Falling falling = new Falling(25 * i, y * -50);
                circles.add(falling);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for(Frame f : frame) {
            f.render(mouseX, mouseY);
        }

        if(!circles.isEmpty() && LeapFrog.getModuleManager().getModule(ClickGUIModule.class).isToggled() && ClickGUIModule.INSTANCE.falling.getValue()) {
            circles.forEach(f -> { f.render(new ScaledResolution(Wrapper.getMC())); });
        }
    }

    @Override
    public void onGuiClosed() {
        LeapFrog.getFileManager().save();
        if(LeapFrog.getModuleManager().getModule(ClickGUIModule.class).isToggled()) LeapFrog.getModuleManager().getModule(ClickGUIModule.class).setToggled(false);
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

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for(Frame f : frame) {
            f.keyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    public ArrayList<Frame> getFrame() {
        return frame;
    }
}
