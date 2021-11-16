package dev.leap.frog.Settings;

public class ValueChange<T> {

    private T newValue;
    private T oldValue;

    public ValueChange(T newValue, T oldValue) {
        this.newValue = newValue;
        this.oldValue = oldValue;
    }

    public T getNewValue() {
        return newValue;
    }

    public void setNewValue(T newValue) {
        this.newValue = newValue;
    }

    public T getOldValue() {
        return oldValue;
    }

    public void setOldValue(T oldValue) {
        this.oldValue = oldValue;
    }
}
