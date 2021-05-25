package dev.leap.frog.GUI;

import dev.leap.frog.GUI.HUD.Component;
import dev.leap.frog.GUI.Items.Frame;
import dev.leap.frog.GUI.Items.ModuleButton;
import dev.leap.frog.Manager.HudManager;
import dev.leap.frog.Module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;

import java.util.ArrayList;

public class ClickGUI extends GuiScreen {

    public static ClickGUI getInstance = new ClickGUI();

    protected Minecraft mc = Minecraft.getMinecraft();

    private ArrayList<Frame> frame;

    public ClickGUI() {
        frame = new ArrayList();
        int offset = 0;
        for(Module.Type type : Module.Type.values()) {
            frame.add(new Frame(type, 10 + offset, 20));
            offset += 120;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for(Component c : HudManager.HUDComponent){
            c.Render(mouseX, mouseY);
        }
        for(Frame f : frame) {
            f.render(mouseX, mouseY);

        }

    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        super.mouseClicked(mouseX,mouseY, mouseButton);
        for(Frame f : frame) {
            f.OnClick(mouseX, mouseY, mouseButton);
        }

    }
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for(Frame f: frame){
            f.OnMouseReleased(mouseX, mouseY);
        }
    }
}
