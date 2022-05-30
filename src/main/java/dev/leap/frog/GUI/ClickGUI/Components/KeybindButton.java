package dev.leap.frog.GUI.ClickGUI.Components;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.GUI.ClickGUI.ModuleButton;
import dev.leap.frog.Util.Render.Colorutil;
import dev.leap.frog.Util.Render.Renderutil;
import dev.leap.frog.Util.Wrapper;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

public class KeybindButton extends Component {

    public KeybindButton(int x, int y, ModuleButton button) {
        super(button, x, y, button.getWidth(), 13);
    }

    private boolean listening = false;

    @Override
    public void draw(int mouseX, int mouseY) {
        Renderutil.drawRect(getX(), getY(), getX(), getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Renderutil.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight() - 1, handleColor(mouseX, mouseY));
        Renderutil.drawRect(getX(), getY() + getHeight() - 1, getX() + getWidth(), getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());  // seperator Line
        if(this.getModuleButton().getModule().getKey() > 0) {
            Wrapper.getMC().fontRenderer.drawStringWithShadow(listening ? "Press a key..." : ("Key: " + ChatFormatting.GRAY + Keyboard.getKeyName(this.getModuleButton().getModule().getKey())), getX() + 4, getY() + 2, -1);
        } else {
            Wrapper.getMC().fontRenderer.drawStringWithShadow(listening ? "Press a key..." : ("Key: " + ChatFormatting.GRAY + "NONE"), getX() + 4, getY() + 2, -1);
        }
    }

    private int handleColor(int mouseX, int mouseY) {
        Color color = listening ? Colorutil.subComponentColor() : new Color(50, 50, 50, 200);
        return isHovered(mouseX, mouseY) ? (listening ? color.darker().darker().getRGB() : color.brighter().brighter().getRGB()) : color.getRGB();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(isHovered(mouseX, mouseY)) {
            if(button == 0 && this.getModuleButton().isOpened()) {
                if(!listening) {
                    listening = true;
                } else if(listening) {
                    listening = false;
                }
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if(listening){
            if(keyCode > 0 && keyCode != Keyboard.KEY_ESCAPE){
                if(keyCode == Keyboard.KEY_BACK || keyCode == 211) {
                    getModuleButton().getModule().setKey(Keyboard.KEY_NONE);
                }
                else {
                    getModuleButton().getModule().setKey(keyCode);
                }
            }
            listening = false;
        }
    }
}
