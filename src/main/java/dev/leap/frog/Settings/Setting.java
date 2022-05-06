package dev.leap.frog.Settings;

import dev.leap.frog.Module.Module;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Setting<T> {

    private String name;
    private String description;
    private Module module;
    private T value;
    private T min;
    private T max;

    private List<String> combobox;
    private Predicate<T> visible;

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

    public Setting(String name, Module module, T value, T min, T max, Predicate<T> visible) {
        this.name = name;
        this.module = module;
        this.value = value;
        this.min = min;
        this.max = max;
        this.visible = visible;
    }

    public Setting(String name, Module module, T value, Predicate<T> visible) {
        this.name = name;
        this.module = module;
        this.value = value;
        this.visible = visible;
    }

    public Setting(String name, Module module, T value, Predicate<T> visible, String description) {
        this.name = name;
        this.module = module;
        this.value = value;
        this.visible = visible;
        this.description = description;
    }

    public Setting(String name, Module module, T value, List<String> modes) {
        this.name = name;
        this.module = module;
        this.value = value;
        this.combobox = modes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Module getModule() {
        return module;
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

    public List<String> getCombobox() {
        return this.combobox;
    }

    public void setCombobox(ArrayList<String> combobox) {
        this.combobox = combobox;
    }

    public int getEnum(String input) {
        for (int i = 0; i < this.value.getClass().getEnumConstants().length; ++i) {
            final Enum e = (Enum)this.value.getClass().getEnumConstants()[i];
            if (e.name().equalsIgnoreCase(input)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEnum() {
        return this.value instanceof Enum;
    }

    public boolean isBoolean() {
        return this.value instanceof Boolean;
    }

    public boolean isInteger() {
        return this.value instanceof Integer;
    }

    public boolean isFloat() {
        return this.value instanceof Float;
    }

    public boolean isDouble() {
        return this.value instanceof Double;
    }

    public boolean isColor() {
        return this.value instanceof Color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Predicate<T> getVisible() {
        return visible;
    }

    public void setVisible(Predicate<T> visible) {
        this.visible = visible;
    }

    public boolean isVisible(Predicate<T> visible) {
        return visible == null || visible.test(getValue());
    }

    public boolean isVisible() {
        if (this.visible == null) {
            return true;
        }
        return this.visible.test(this.getValue());
    }

    public boolean is(String mode) {
        if(this.value instanceof Enum) {
            Enum<?> e = (Enum<?>) this.value;
            if(e.name().equalsIgnoreCase(mode)) {
                return true;
            }
        }
        return false;
    }

    public boolean in(String val) {
        return val.equalsIgnoreCase((String) value);
    }

    public boolean isShown() {
        if(visible == null) {
            return true;
        }
        return visible.test(getValue());
    }

}
