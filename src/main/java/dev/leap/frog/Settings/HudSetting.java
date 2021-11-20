package dev.leap.frog.Settings;

public class HudSetting<T> {

    private String name;

    private T value;
    private T min;
    private T max;

    public HudSetting(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public HudSetting(String name, T value, T min, T max) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
