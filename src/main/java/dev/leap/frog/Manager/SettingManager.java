package dev.leap.frog.Manager;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;

import java.util.ArrayList;
import java.util.List;

public class SettingManager {

    public ArrayList<Setting> settings;

    public SettingManager() { // way to initalize in main class
        this.settings = new ArrayList<>();
    }

    public List<Setting> getSettingsForMod(Module module){

        List<Setting> settings = new ArrayList<>();
            for(Setting s : this.settings){
            if(s.getModule() == module){
                settings.add(s);
            }
        }
        return settings;
    }

    public Setting getSetting(Module module) {
        for(Setting setting : this.settings) { // we are taking concept from old setting manager
            if(setting.getModule().equals(module)) {
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
