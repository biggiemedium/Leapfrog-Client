package dev.leap.frog.Manager;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;

import java.util.ArrayList;
import java.util.List;

public class SettingManager {

    public ArrayList<Setting> settings;
    private Module module;

    public SettingManager() {
        this.settings = new ArrayList<>();
    }

    public List<Setting> getSettingsForMod(Module module){
        List<Setting> setting = new ArrayList<>();
            for(Setting s : this.settings){
            if(s.getModule() == module){
                setting.add(s);
            }
        }
        return setting;
    }

    public Setting getSetting(String moduleName, String name) {
        for(Setting s : getSettingsArrayList()) {
            if(s.getModule().getName().equalsIgnoreCase(moduleName) && s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    public Setting getSetting(String name, Module module) {
        for(Setting setting : this.settings) { // we are taking concept from old setting manager
            if(setting.getModule().equals(module) && setting.getName().equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }

    public Setting Build(Setting setting) { // could not find a way to register so made this
        this.settings.add(setting);

        return setting;
    }

    public ArrayList<Setting> getSettingsArrayList() {
        return settings;
    }
}
