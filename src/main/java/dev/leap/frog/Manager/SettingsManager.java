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

    public ArrayList<Settings> getArraySettings() {
        return this.array_setting;
    }

    public Settings getSettingsbyTag(Module module, String tag) {
        Settings setting_requested = null;

        for (Settings settings : getArraySettings()) {
            if (settings.getMaster().equals(module) && settings.getTag().equalsIgnoreCase(tag)) {
                setting_requested = settings;
            }
        }

        return setting_requested;
    }

    public Settings getSettingWithTag(String tag, String tag_) {
        Settings setting_requested = null;

        for (Settings settings : getArraySettings()) {
            if (settings.getMaster().getName().equalsIgnoreCase(tag) && settings.getTag().equalsIgnoreCase(tag_)) {
                setting_requested = settings;
                break;
            }
        }

        return setting_requested;
    }

    public ArrayList<Settings> getSettingByModule(Module module) {
        ArrayList<Settings> setting_requesteds = new ArrayList<>();

        for (Settings settings : getArraySettings()) {
            if (settings.getMaster().equals(module)) {
                setting_requesteds.add(settings);
            }
        }
        return setting_requesteds;
    }


}
