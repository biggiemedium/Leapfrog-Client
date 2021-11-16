package dev.leap.frog.GUI.HUD;

import dev.leap.frog.Settings.HudSetting;
import dev.leap.frog.Util.Listeners.Util;

import java.util.ArrayList;

public class Item implements Util {

    private ArrayList<HudSetting> hudSettings = new ArrayList<>();

    private String name;
    private int x;
    private int y;
    private int width;
    private int height;

    public Item(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    protected void register() {

    }

}
