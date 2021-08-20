package dev.leap.frog.Util.Listeners;

/*
we are using the same thing for old GUI except swapping abstract class with interface
also rewriting this shit
 */

import net.minecraft.client.Minecraft;

import java.io.IOException;

public interface IComponent {


    void draw(int mouseX, int mouseY);

    void mouseClicked(int mouseX, int mouseY, int button) throws IOException;

    void mouseReleased(int mouseX, int mouseY, int state);

    void keyTyped(char typedChar, int keyCode) throws IOException;

    void render();
}
