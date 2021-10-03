package dev.leap.frog.GUI.Effects;

import dev.leap.frog.Util.Render.Renderutil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

/*
    Credits to Ionar
 */

public class Falling {

    private static final Random random = new Random();
    private final int x;
    private int y;
    private int fallingSpeed = random.nextInt(2) + 1;
    private float size = (float) Math.random();
    private int age = random.nextInt(100) + 1;

    public Falling(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render(ScaledResolution res) {
        if (y > res.getScaledHeight() + 10 || y < -10) {
            y = -10;

            fallingSpeed = random.nextInt(10) + 1;
            size = (float) Math.random() + 1;

            return;
        }

        age++;
        float xOffset = MathHelper.sin(age / 16.0f) * 32;

        float result = x + xOffset;

        Renderutil.drawCircle(result, (float)y, size+1, 0xFF920707);
        Renderutil.drawCircle(result, (float)y, size, 0xFFFFDC00);

        y += fallingSpeed;
    }

}
