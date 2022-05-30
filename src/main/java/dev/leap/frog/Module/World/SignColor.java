package dev.leap.frog.Module.World;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Module.Module;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraftforge.client.event.GuiScreenEvent;

public class SignColor extends Module {

    public SignColor() {
        super("Sign Color", "Changes colors on signs", Type.WORLD);
    }



     class ChangeSignColor extends GuiEditSign {
        public ChangeSignColor(TileEntitySign teSign) {
            super(teSign);
        }


    }
}
