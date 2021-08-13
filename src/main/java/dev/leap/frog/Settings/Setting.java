package dev.leap.frog.Settings;

import dev.leap.frog.Module.Module;

import java.util.List;

public class Setting<T> {

    private String name;
    private Module module;
    private T value;
    private T min;
    private T max;

    public Setting(String name, Module module, T value) {
        this.name = name;
        this.module = module;
        this.value = value;
    }

    public Setting(String name, Module module, T value, T min, T max) {
        this.name = name;
        this.module = module;
        this.value = value;
        this.min = min;
        this.max = max;
    }

    //public Setting(String name, Module module, T value, List<String> values) {
    //    this.name = name;
    //    this.module = module;
    //    this.value = value;
    //    this.currentComboboxValue = value;
    //    this.combobox = values;
    //}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getMin() {
        return min;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public T getMax() {
        return max;
    }

    public void setMax(T max) {
        this.max = max;
    }
}
