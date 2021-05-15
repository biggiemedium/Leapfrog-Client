package dev.leap.frog.Settings;

import dev.leap.frog.Module.Module;

import java.util.List;

public class Setting {

    private final String name;
    private final String configname;
    private final Module parent;
    private final Module.Type category;
    private final Type type;

    public Setting(final String name, final String configname, final Module parent, final Module.Type category, final Type type) {
        this.name = name;
        this.configname = configname;
        this.parent = parent;
        this.type = type;
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public String getConfigName() {
        return this.configname;
    }

    public Module getParent() {
        return this.parent;
    }

    public Type getType() {
        return this.type;
    }

    public Module.Type getCategory() {
        return this.category;
    }

    public enum Type {
        I,
        D,
        B,
        STRING,
        M
    }

    public static class i extends Setting {
        private final int min;
        private final int max;
        private int value;

        public i(final String name, final String configname, final Module parent, final Module.Type category, final int value, final int min, final int max) {
            super(name, configname, parent, category, Type.I);
            this.value = value;
            this.min = min;
            this.max = max;
        }

        public int getValue() {
            return this.value;
        }

        public void setValue(final int value) {
            this.value = value;
        }

        public int getMin() {
            return this.min;
        }

        public int getMax() {
            return this.max;
        }
    }

    public static class d extends Setting {
        private final double min;
        private final double max;
        private double value;

        public d(final String name, final String configname, final Module parent, final Module.Type category, final double value, final double min, final double max) {
            super(name, configname, parent, category, Type.D);
            this.value = value;
            this.min = min;
            this.max = max;
        }

        public double getValue() {
            return this.value;
        }

        public void setValue(final double value) {
            this.value = value;
        }

        public double getMin() {
            return this.min;
        }

        public double getMax() {
            return this.max;
        }
    }

    public static class b extends Setting {
        private boolean value;

        public b(final String name, final String configname, final Module parent, final Module.Type category, final boolean value) {
            super(name, configname, parent, category, Type.B);
            this.value = value;
        }

        public boolean getValue() {
            return this.value;
        }

        public void setValue(final boolean value) {
            this.value = value;
        }
    }

    public static class mode extends Setting {
        private final java.util.List<String> modes;
        private String value;

        public mode(final String name, final String configname, final Module parent, final Module.Type category, final java.util.List<String> modes, final String value) {
            super(name, configname, parent, category, Type.M);
            this.value = value;
            this.modes = modes;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(final String value) {
            this.value = value;
        }

        public List<String> getModes() {
            return this.modes;
        }
    }

}
