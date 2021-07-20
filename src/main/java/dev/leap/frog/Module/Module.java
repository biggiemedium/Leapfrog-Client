package dev.leap.frog.Module;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Settings.Settings;
import dev.leap.frog.Util.Render.Chatutil;
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

    public Module(String name, String description, Type type) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.key = -1;
        this.toggled = false;
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

    public Setting create(String name, Object value) {
        return LeapFrog.getSettingManager().Build(new Setting<>(name, this, value)); // keep <> here because parameter
    }

    public Setting create(String name, Object value, Object min, Object max) {
        return LeapFrog.getSettingManager().Build(new Setting<>(name, this, value, min, max));
    }



    public void onUpdate() {} // On tick
    public void onRender() {} // add game render in events
    public void onRender(RenderEvent event) {}


    public enum Type {
        CLIENT("Client", false),
        COMBAT("Combat",false),
        MOVEMENT("Movement",false),
        MISC("Misc",false),
        EXPLOIT("Exploit",false),
        RENDER("Render",false),
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
