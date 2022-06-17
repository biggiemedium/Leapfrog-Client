package dev.leap.frog.Util.Entity;

import java.util.concurrent.TimeUnit;

public class PlayTimeutil {

    private long timePlayed = 0;
    private long loginTimes = 0;

    public PlayTimeutil() {
        this.timePlayed = System.nanoTime();
    }

    public String getDisplayTime() {
        String displayTime = "";



        return displayTime;
    }

    public long getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(long timePlayed) {
        this.timePlayed = timePlayed;
    }

    public long getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(long loginTimes) {
        this.loginTimes = loginTimes;
    }

    public static PlayTimeutil INSTANCE = new PlayTimeutil();
}
