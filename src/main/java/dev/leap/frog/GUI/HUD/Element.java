package dev.leap.frog.GUI.HUD;

import dev.leap.frog.Settings.Setting;

import java.util.ArrayList;

public class Element {

    private ArrayList<Setting<?>> hudSettings = new ArrayList<>();

    private String name;

    private int x;
    private int y;
    private int width;
    private int height;

    private boolean dragging;
    private boolean visible;

    public Element(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
