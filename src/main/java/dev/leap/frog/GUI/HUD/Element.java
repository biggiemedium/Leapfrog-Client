package dev.leap.frog.GUI.HUD;

import dev.leap.frog.Settings.HudSetting;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class Element {

    private String name;
    private String description;

    private int x;
    private int y;

    private int height;
    private int width;

    protected List<HudSetting> settings;
    protected Minecraft mc = Minecraft.getMinecraft();

    public Element(String name) {
        this.name = name;
        this.settings = new ArrayList<>();
    }

    protected <T> HudSetting create(HudSetting<T> setting) {
        this.settings.add(setting);
        return setting;
    }

    public List<HudSetting> getSettings() {
        return this.settings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
