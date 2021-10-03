package dev.leap.frog.Module.World;

import dev.leap.frog.Event.World.EventSetDayTime;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.client.event.EntityViewRenderEvent;

public class SkyColor extends Module {

    public SkyColor() {
        super("SkyColor", "Changes the color of the sky", Type.WORLD);
    }

    Setting<Boolean> setTime = create("Set time", false);
    Setting<Integer> time = create("Time", 0, 1600, 1800, v -> setTime.getValue());
    Setting<Boolean> setColor = create("SetColor", false);
    public Setting<Integer> red = create("Red", 255, 0, 255, v -> setColor.getValue());
    public Setting<Integer> green = create("green", 255, 0, 255, v -> setColor.getValue());
    public Setting<Integer> blue = create("blue", 255, 0, 255, v -> setColor.getValue());

    @Override
    public void onRender() {

    }

    @EventHandler
    private Listener<EntityViewRenderEvent.FogColors> fogColorsListener = new Listener<>(event -> {

        if(setColor.getValue()) {
            event.setBlue(blue.getValue() / 255f);
            event.setRed(red.getValue() / 255f);
            event.setGreen(green.getValue() / 255f);
        }

    });

    @EventHandler
    private Listener<EntityViewRenderEvent.FogDensity> fogDensityListener = new Listener<>(event -> {

        if(setColor.getValue()) {
            event.setDensity(0.0f);
            event.setCanceled(true);
        }
    });

    @EventHandler
    private Listener<EventSetDayTime> timeListener = new Listener<>(event -> {

        if(setTime.getValue()) {
            event.setTime((long) time.getValue());
        }

    });

}
