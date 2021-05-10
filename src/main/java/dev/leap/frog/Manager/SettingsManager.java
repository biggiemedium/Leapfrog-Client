package dev.leap.frog.Manager;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;

import java.util.ArrayList;

public class SettingsManager {

    public ArrayList<Settings> array_setting;

    public SettingsManager() {
        this.array_setting = new ArrayList<>();
    }

    public void register(Settings setting) {
        this.array_setting.add(setting);
    }

    public ArrayList<Settings> get_array_settings() {
        return this.array_setting;
    }

    public Settings get_setting_with_tag(Module module, String tag) {
        Settings setting_requested = null;

        for (Settings settings : get_array_settings()) {
            if (settings.getMaster().equals(module) && settings.getTag().equalsIgnoreCase(tag)) {
                setting_requested = settings;
            }
        }

        return setting_requested;
    }

    public ArrayList<Settings> get_settings_with_hack(Module module) {
        ArrayList<Settings> setting_requesteds = new ArrayList<>();

        for (Settings settings : get_array_settings()) {
            if (settings.getMaster().equals(module)) {
                setting_requesteds.add(settings);
            }
        }

        return setting_requesteds;
    }

}
