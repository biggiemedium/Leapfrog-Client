package dev.leap.frog.GUI.ClickGUI.Components;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.GUI.ClickGUI.ModuleButton;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Util.Render.Colorutil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.gui.Gui;
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

    }

    private int getColor(int mouseX, int mouseY) {
        Color color = listening ? Colorutil.getToggledC() : new Color(50, 50, 50, 200);
        return mouseX > getX() + 2 && mouseY > getY() && mouseX < getX() + 2 + getWidth() && mouseY < getY() + getHeight() - 1 ? (listening ? color.darker().darker().getRGB() : color.brighter().brighter().getRGB()) : color.getRGB();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(isHovered(mouseX, mouseY)) {
            if(button == 0) {
                listening = !listening;
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if(listening) {
            if(keyCode == 211) {
                this.getModuleButton().getModule().setKey(0);
            } else {
                this.getModuleButton().getModule().setKey(keyCode);
            }
            listening = false;
        }
    }
}
