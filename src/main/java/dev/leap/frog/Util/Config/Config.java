package dev.leap.frog.Util.Config;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Util.Listeners.Util;

import java.io.File;

/*
This Config Package is completely experimental for the time being
*/

public class Config implements Util {

    private File mainFile;
    private File subFile;

    public Config(String path) {
        this.mainFile = new File(LeapFrog.MODID);
        if(!mainFile.exists()) { mainFile.mkdirs(); }

        this.subFile = new File(mainFile + File.separator + path);
        if(!subFile.exists()) { subFile.mkdirs(); }
    }

    public void saveFile() {

    }

    public void loadFile() {

    }

    public File getMainFile() {
        return this.mainFile;
    }

    public File getSubFile() {
        return this.subFile;
    }
}
