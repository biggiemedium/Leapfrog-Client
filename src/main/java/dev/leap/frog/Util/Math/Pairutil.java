package dev.leap.frog.Util.Math;

import dev.leap.frog.Manager.UtilManager;

public class PairUtil<T, E> extends UtilManager {

    private T value;
    private E key;

    public PairUtil(T value, E key) {
        this.value = value;
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public E getKey() {
        return key;
    }

    public void setKey(E key) {
        this.key = key;
    }
}
