package dev.leap.frog.GUI.ClickGUI.Subbutton;

import dev.leap.frog.GUI.ClickGUI.ModuleButton;
import dev.leap.frog.Util.Listeners.IComponent;

import java.io.IOException;

public class WidgetHandler implements IComponent {

    private int x;
    private int y;
    private int width;
    private int height;
    private ModuleButton button; // parent (from old gui)

    public WidgetHandler() {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ModuleButton getButton() {
        return button;
    }

    public void setButton(ModuleButton button) {
        this.button = button;
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

    @Override
    public void render() {

    }
}
