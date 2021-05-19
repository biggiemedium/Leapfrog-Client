package dev.leap.frog.Settings;

import dev.leap.frog.Module.Module;

import java.util.List;

public class Setting {

    private Module master;

    private String name;
    private String tag;

    private boolean button;

    private List<String> combobox;
    private      String  current;

    private String label;

    private double slider;
    private double min;
    private double max;

    private String type;

    public Setting(Module master, String name, String tag, boolean value) {
        this.master = master;
        this.name   = name;
        this.tag    = tag;
        this.button = value;
        this.type   = "button";
    }

    public Setting(Module master, String name, String tag, List<String> values, String value) {
        this.master   = master;
        this.name     = name;
        this.tag      = tag;
        this.combobox = values;
        this.current  = value;
        this.type     = "combobox";
    }

    public Setting(Module master, String name, String tag, String value) {
        this.master = master;
        this.name   = name;
        this.tag    = tag;
        this.label  = value;
        this.type   = "label";
    }

    public Setting(Module master, String name, String tag, double value, double min, double max) {
        this.master = master;
        this.name   = name;
        this.tag    = tag;
        this.slider = value;
        this.min    = min;
        this.max    = max;
        this.type   = "doubleslider";
    }

    public Setting(Module master, String name, String tag, int value, int min, int max) {
        this.master = master;
        this.name   = name;
        this.tag    = tag;
        this.slider = value;
        this.min    = min;
        this.max    = max;
        this.type   = "integerslider";
    }

    public Module getMaster() {
        return this.master;
    }

    public String getName() {
        return this.name;
    }

    public String getTag() {
        return this.tag;
    }

    public void setValue(boolean value) {
        this.button = value;
    }

    public void setCurrentValue(String value) {
        this.current = value;
    }

    public void setValue(String value) {
        this.label = value;
    }

    public void setValue(double value) {
        if (value >= getMax(value)) {
            this.slider = getMax(value);
        } else if (value <= getMin(value)) {
            this.slider = getMin(value);
        } else {
            this.slider = value;
        }
    }

    public void setValue(int value) {
        if (value >= this.getMax(value)) {
            this.slider = this.getMax(value);
        } else if (value <= this.getMin(value)) {
            this.slider = this.getMin(value);
        } else {
            this.slider = value;
        }
    }

    public boolean isInfo() {
        return this.name.equalsIgnoreCase("info");
    }

    public boolean in(String value) {
        return this.current.equalsIgnoreCase(value);
    }

    public boolean getValue(boolean type) {
        return this.button;
    }

    public List<String> get_values() {
        return this.combobox;
    }

    public String getCurrentValue() {
        return this.current;
    }

    public String getValue(String type) {
        return this.label;
    }

    public double getValue(double type) {
        return this.slider;
    }

    public int getValue(int type) {
        return ((int) Math.round(this.slider));
    }

    public double getMin(double type) {
        return this.min;
    }

    public double getMax(double type) {
        return this.max;
    }

    public int getMin(int type) {
        return ((int) this.min);
    }

    public int getMax(int type) {
        return ((int) this.max);
    }

    public String getType() {
        return this.type;
    }

}
