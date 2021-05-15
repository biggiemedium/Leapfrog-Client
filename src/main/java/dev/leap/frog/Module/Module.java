package dev.leap.frog.Module;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Settings.Setting;
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


    protected Setting.i registerI(final String name, final String configname, final int value, final int min, final int max) {
        final Setting.i s = new Setting.i(name, configname, this, getType(), value, min, max);
        LeapFrog.getSettingsManager().addSetting(s);
        return s;
    }

    protected Setting.d registerD(final String name, final String configname, final double value, final double min, final double max) {
        final Setting.d s = new Setting.d(name, configname, this, getType(), value, min, max);
        LeapFrog.getSettingsManager().addSetting(s);
        return s;
    }

    protected Setting.b registerB(final String name, final String configname, final boolean value) {
        final Setting.b s = new Setting.b(name, configname, this, getType(), value);
        LeapFrog.getSettingsManager().addSetting(s);
        return s;
    }

    protected Setting.mode registerMode(final String name, final String configname, final List<String> modes, final String value) {
        final Setting.mode s = new Setting.mode(name, configname, this, getType(), modes, value);
        LeapFrog.getSettingsManager().addSetting(s);
        return s;
    }

    public String getHudInfo() {
        return "";
    }


    public void onUpdate() {} // On tick
    public void onRender() {} // add game render in events
    public void createSetting() {} // to dump settings in

    public String keyToString() {
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
        COMBAT("Combat",false),
        MOVEMENT("Movement",false),
        MISC("Misc",false),
        EXPLOIT("Exploit",false),
        RENDER("Render",false),
        GUI("Ui",false);

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
