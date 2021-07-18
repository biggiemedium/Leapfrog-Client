package dev.leap.frog.Manager;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;

import java.util.ArrayList;

public class SettingManager {

    private ArrayList<Setting> settings;

    public SettingManager() { // way to initalize in main class
        this.settings = new ArrayList<>();
    }

    public Setting getSetting(String name, Module module) {

        for(Setting setting : this.settings) { // we are taking concept from old setting manager
            if(setting.getName().equalsIgnoreCase(name) && setting.getModule().equals(module)) {
                return setting;
            }
        }
        return null;
    }

    public Setting Build(Setting setting) { // could not find a way to register so made this
        this.settings.add(setting);

        return setting;
    }

}
