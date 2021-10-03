package dev.leap.frog.Util.Listeners;

/*
we are using the same thing for old GUI except swapping abstract class with interface
also rewriting this shit
-Boncorde: this wont work your not gonna be able to render these things lol
-PX: do you even know how an interface works?
 */

import net.minecraft.client.Minecraft;

import java.io.IOException;

public interface IComponent {


    void draw(int mouseX, int mouseY);

    void mouseClicked(int mouseX, int mouseY, int button) throws IOException;

    void mouseReleased(int mouseX, int mouseY, int state);

    void keyTyped(char typedChar, int keyCode) throws IOException;
}
