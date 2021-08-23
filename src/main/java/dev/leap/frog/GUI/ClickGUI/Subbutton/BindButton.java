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

    public BindButton(int x, int y, ModuleButton moduleButton) {

    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Gui.drawRect(getX(), getY(), getX() + 2, getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Gui.drawRect(getX() + 2, getY(), getX() + 2 + getWidth(), getY() + getHeight() - 1, getColor(mouseX, mouseY));
        Gui.drawRect(getX() + 2, getY() + getHeight() - 1, getX() + 2 + getWidth(), getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Wrapper.getMC().fontRenderer.drawStringWithShadow("Bind", getX() + 4, getY() + 2, -1);
        String val = "" + ChatFormatting.GRAY + (binding ? "Listening" : Keyboard.getKeyName(getButton().getModule().getKey()));
        Wrapper.getMC().fontRenderer.drawStringWithShadow(val, getX() + getWidth() - Wrapper.getMC().fontRenderer.getStringWidth(val), getY() + 2, -1);

    }

    private int getColor(int mouseX, int mouseY){
        Color color = new Color(50, 50, 50, 200);
        boolean hovered = mouseX > getX() + 2 && mouseY > getY() && mouseX < getX() + 2 + getWidth() && mouseY < getY() + getHeight() - 1;
        return hovered ? color.brighter().brighter().getRGB() : color.getRGB();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        super.mouseClicked(mouseX, mouseY, button);
        if(mouseX > getX() + 2 && mouseY > getY() && mouseX < getX() + 2 + getWidth() && mouseY < getY() + getHeight() - 1 && button == 0){
            binding = !binding;
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if(binding) {
            if(keyCode != 0 && keyCode != Keyboard.KEY_ESCAPE) {
                if(keyCode == Keyboard.KEY_DELETE) {
                    getButton().getModule().setKey(Keyboard.KEY_NONE);
                } else {
                    getButton().getModule().setKey(keyCode);
                }
            }
            binding = false;
        }
    }
}
