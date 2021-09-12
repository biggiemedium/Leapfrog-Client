package dev.leap.frog.Module.ui;

import dev.leap.frog.GUI.Click;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;
import org.lwjgl.input.Keyboard;

public class ClickGUIModule extends Module {

    public ClickGUIModule() {
        super("ClickGUI", "CGUI", Type.RENDER);
        setKey(Keyboard.KEY_RSHIFT);
    }

    public Setting<Boolean> descriptionn = create("Description", true);
    public Setting<Boolean> blur = create("Blur", true);

    @Override
    public void onUpdate() {

    }


    @Override
    public void onEnable() {
        mc.displayGuiScreen(Click.INSTANCE);
        toggle();
    }

    public static ClickGUIModule INSTANCE = new ClickGUIModule();
}
