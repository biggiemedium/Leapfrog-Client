package dev.leap.frog.Module.Client;

import dev.leap.frog.GUI.Click;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class Effects extends Module {

    public Effects() {
        super("GUI Effects", "Displays different effects while click gui is active", Type.CLIENT);
        INSTANCE = this;
    }

    public static Effects INSTANCE;

    public Setting<Boolean> descriptionn = create("Description", true);
    public Setting<Boolean> clickSound = create("ClickSound", false);
    public Setting<Boolean> watermark = create("WaterMark", true);
    public Setting<Boolean> falling = create("Falling", false);
    public Setting<Boolean> sideProfile = create("SideBars", true);

    private ResourceLocation waterMark = new ResourceLocation("minecraft","Leapfrog.PNG");

    @Override
    public void onUpdate() {
        descriptionn.getValue();
    }

    @Override
    public void onRender() {

        if(watermark.getValue()) {
            ScaledResolution sr = new ScaledResolution(mc);
            mc.renderEngine.bindTexture(waterMark);
            Gui.drawScaledCustomSizeModalRect(0, sr.getScaledHeight() - 80, 0, 0, 80, 80, 80, 80, 80, 80);
        }

    }
}
