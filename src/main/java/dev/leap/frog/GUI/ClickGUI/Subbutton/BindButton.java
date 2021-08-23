package dev.leap.frog.GUI.ClickGUI.Subbutton;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.GUI.ClickGUI.ModuleButton;
import dev.leap.frog.Util.Listeners.IComponent;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;


public class BindButton extends WidgetHandler {

    private boolean binding = false;
    private int offset;

    private ModuleButton m;
    public BindButton(int x, int y, ModuleButton moduleButton) {
        this.m = moduleButton;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Gui.drawRect(m.getX() + m.getWidth(), m.getY(), m.getX() + (m.getWidth() * 2), m.getY() + m.getHeight(), Color.green.getRGB());
        Wrapper.mc.fontRenderer.drawString("null", m.getX() + (m.getWidth() / 2), m.getY() + (m.getHeight() / 2), Color.black.getRGB());
    }

    private int getColor(int mouseX, int mouseY) {
        Color color = new Color(50, 50, 50, 200);
        boolean hovered = mouseX > getX() + 2 && mouseY > getY() && mouseX < getX() + 2 + getWidth() && mouseY < getY() + getHeight() - 1;
        return hovered ? color.brighter().brighter().getRGB() : color.getRGB();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        super.mouseClicked(mouseX, mouseY, button);
        if (mouseX > getX() + 2 && mouseY > getY() && mouseX < getX() + 2 + getWidth() && mouseY < getY() + getHeight() - 1 && button == 0) {
            binding = !binding;
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (binding) {
            if (keyCode != 0 && keyCode != Keyboard.KEY_ESCAPE) {
                if (keyCode == Keyboard.KEY_DELETE) {
                    getButton().getModule().setKey(Keyboard.KEY_NONE);
                } else {
                    getButton().getModule().setKey(keyCode);
                }
            }
            binding = false;
        }
    }
}
