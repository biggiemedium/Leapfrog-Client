package dev.leap.frog.Manager;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SettingsManager {

    public ArrayList<Setting> array_setting;

    public SettingsManager() {
        this.array_setting = new ArrayList<>();
    }

    public void register(Setting setting) {
        this.array_setting.add(setting);
    }

    public ArrayList<Setting> get_array_settings() {
        return this.array_setting;
    }

    public Setting get_setting_with_tag(Module module, String tag) {
        Setting setting_requested = null;

        for (Setting settings : get_array_settings()) {
            if (settings.getMaster().equals(module) && settings.getTag().equalsIgnoreCase(tag)) {
                setting_requested = settings;
            }
        }

        return setting_requested;
    }

    public ArrayList<Setting> get_settings_with_hack(Module module) {
        ArrayList<Setting> setting_requesteds = new ArrayList<>();

        for (Setting settings : get_array_settings()) {
            if (settings.getMaster().equals(module)) {
                setting_requesteds.add(settings);
            }
        }

        return setting_requesteds;
    }


}
