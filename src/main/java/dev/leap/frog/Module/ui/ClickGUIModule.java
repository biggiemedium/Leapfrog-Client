package dev.leap.frog.Module.ui;

import dev.leap.frog.GUI.Click;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ClickGUIModule extends Module {

    public ClickGUIModule() {
        super("ClickGUI", "CGUI", Type.RENDER);
        setKey(Keyboard.KEY_RSHIFT);
        INSTANCE = this;
    }


    public static ClickGUIModule INSTANCE;

    public Setting<Boolean> descriptionn = create("Description", true);
    public Setting<Boolean> clickSound = create("ClickSound", false);
    public Setting<Boolean> watermark = create("WaterMark", true);
    public Setting<Boolean> falling = create("Falling", false);
    public Setting<Boolean> sideProfile = create("SideBars", true);
    public Setting<Boolean> FOVAdjustments = create("FOV Change", false);
    public Setting<Float> FOV = create("FOV", 60.0f, 0.0f, 180.0f);

    public Setting<Boolean> GUIColors = create("GUIColors", true);
    public Setting<Boolean> rainbow = create("Rainbow", false, v -> GUIColors.getValue());

    public Setting<Integer> red = create("Red", 29, 0, 255, v -> GUIColors.getValue() && !rainbow.getValue());
    public Setting<Integer> green = create("green", 37, 0, 255, v -> GUIColors.getValue() && !rainbow.getValue());
    public Setting<Integer> blue = create("blue", 48, 0, 255, v -> GUIColors.getValue() && !rainbow.getValue());
    public Setting<Integer> alpha = create("Alpha", 255, 0, 255, v -> GUIColors.getValue() && !rainbow.getValue());

    public Setting<Float> buttonred = create("Button Red", 0.0f,0f, 1.0f, v -> GUIColors.getValue() && !rainbow.getValue());
    public Setting<Float> buttongreen = create("Button green", 1.0f, 0.0f, 1.0f, v -> GUIColors.getValue() && !rainbow.getValue());
    public Setting<Float> buttonblue = create("Button blue", 0.0f, 0.0f, 1.0f, v -> GUIColors.getValue() && !rainbow.getValue());
    public Setting<Float> buttonalpha = create(" Button Alpha", 0.5f, 0.1f, 1.0f, v -> GUIColors.getValue() && !rainbow.getValue()); // 0.0f, 1, 0.0f, 0.5f

    private ResourceLocation waterMark = new ResourceLocation("minecraft","Leapfrog.png");

    @Override
    public void onUpdate() {
        if(watermark.getValue()) {
            ScaledResolution sr = new ScaledResolution(mc);
            mc.renderEngine.bindTexture(waterMark);
            Gui.drawScaledCustomSizeModalRect(0, sr.getScaledHeight() - 80, 0, 0, 80, 80, 80, 80, 80, 80);
        }
        if(FOVAdjustments.getValue()) {
            mc.gameSettings.fovSetting = FOV.getValue();
        }
    }


    @Override
    public void onEnable() {
        mc.displayGuiScreen(Click.INSTANCE);
    }

    public Color subComponentColors() {
        return new Color(buttonred.getValue(), buttongreen.getValue(), buttonblue.getValue(), buttonalpha.getValue());
    }
}
