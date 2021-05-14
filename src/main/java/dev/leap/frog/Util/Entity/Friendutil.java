package dev.leap.frog.Util.Entity;

import dev.leap.frog.Manager.UtilManager;

public class Friendutil extends UtilManager {

    private String name;

    public Friendutil(String name) {
        this.name = name;
    }

    public String getFriend() {
        return this.name;
    }

}
