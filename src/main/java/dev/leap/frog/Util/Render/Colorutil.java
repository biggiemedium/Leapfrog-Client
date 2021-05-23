package dev.leap.frog.Util.Render;

import dev.leap.frog.Manager.UtilManager;

import java.awt.*;

public class Colorutil extends UtilManager {

    public static int Rainbow(int delay) { // example Rainbow(counter[0] * 300)
        double RainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        RainbowState %= 360;
        return Color.getHSBColor((float) (RainbowState / 360.0f), 0.5f, 1.0f).getRGB();
    }

    public static int getToggledColor() {
        return new Color(0.0f, 1, 0.0f, 0.5f).getRGB();
    }

    public static int getOffColor() {
        return new Color(0.5f, 0.5f, 0.5f, 0.5f).getRGB();
    }

}
