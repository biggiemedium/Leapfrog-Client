package dev.leap.frog.Module;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Settings.Settings;
import me.zero.alpine.fork.listener.Listenable;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Module implements Listenable {

    public String name;
    public String description;
    public Type type;
    public int key;
    public boolean toggled;
    public List<Settings> settingsList = new ArrayList<>();
    protected Minecraft mc = Minecraft.getMinecraft();

    public boolean widgetUsed;

    public Module(String name, String description, Type type) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.key = -1;
        this.toggled = false;
        this.widgetUsed = false;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;

        if(this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }

    }

    public void toggle() {
        this.toggled = !this.toggled;

        if(this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }

    }

    public boolean widgetUsed() {
        return this.widgetUsed;
    }

    public void eventWidget() {

    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
        LeapFrog.EVENT_BUS.subscribe(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
        LeapFrog.EVENT_BUS.unsubscribe(this);
    }

    public boolean isToggled() {
        return toggled;
    }


    public List<Settings> getSettingsList() {
        return settingsList;
    }

    public Settings create(String name, String tag, int value, int min, int max) {
        LeapFrog.getSettingsManager().register(new Settings(this, name, tag, value, min, max));

        return LeapFrog.getSettingsManager().getSettingsbyTag(this, tag);
    }

    public Settings create(String name, String tag, double value, double min, double max) {
        LeapFrog.getSettingsManager().register(new Settings(this, name, tag, value, min, max));

        return LeapFrog.getSettingsManager().getSettingsbyTag(this, tag);
    }

    public Settings create(String name, String tag, boolean value) {
        LeapFrog.getSettingsManager().register(new Settings(this, name, tag, value));

        return LeapFrog.getSettingsManager().getSettingsbyTag(this, tag);
    }

    public Settings create(String name, String tag, String value) {
        LeapFrog.getSettingsManager().register(new Settings(this, name, tag, value));

        return LeapFrog.getSettingsManager().getSettingsbyTag(this, tag);
    }

    public Settings create(String name, String tag, String value, List<String> values) {
        LeapFrog.getSettingsManager().register(new Settings(this, name, tag, values, value));

        return LeapFrog.getSettingsManager().getSettingsbyTag(this, tag);
    }

    public List<String> combobox(String... item) {

        return new ArrayList<>(Arrays.asList(item));
    }


    public void onUpdate() {} // On tick
    public void onRender() {} // add game render in events
    public void onRender(RenderEvent event) {}

    public String keyToString(String info) {
        String bind = "null";

        if(getKey() < -1 || getKey() < 0) {
            bind = "NONE";
        }

        if(!bind.equals("NONE")) {
            String key = Keyboard.getKeyName(getKey());

            bind = Character.toUpperCase(key.charAt(0)) + (key.length() != 1 ? key.substring(1).toLowerCase() : "");
        } else {
            bind = "NONE";
        }

        return bind;

    }


    public enum Type {
        CHAT("Chat", false),
        COMBAT("Combat",false),
        MOVEMENT("Movement",false),
        MISC("Misc",false),
        EXPLOIT("Exploit",false),
        RENDER("Render",false),
        GUI("Ui",false),
        WORLD("World", false);

        String name;
        boolean isHidden;

        Type(String name, boolean isHidden) {
            this.name = name;
            this.isHidden = isHidden;
        }

        public String getName() {
            return name;
        }

        public boolean isHidden() {
            return isHidden;
        }

    }

}
