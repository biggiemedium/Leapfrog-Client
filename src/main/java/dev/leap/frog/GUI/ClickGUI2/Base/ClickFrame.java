package dev.leap.frog.GUI.ClickGUI2.Base;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Listeners.IComponent;

import java.io.IOException;

public class ClickFrame implements IComponent {

    private int x, y, width, height;
    private Module.Type type;
    private boolean open;
    private boolean dragging;
    private double dragX, dragY;

    public ClickFrame() {
        this.open = false;
        this.dragging = false;
    }

    @Override
    public void draw(int mouseX, int mouseY) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {

    }
}
