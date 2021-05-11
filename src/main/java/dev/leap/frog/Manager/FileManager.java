package dev.leap.frog.Manager;


import dev.leap.frog.LeapFrog;

import java.io.File;
import java.io.IOException;

public class FileManager {

    public static final String LeapFrogDirectory = "LeapFrog";

    public void createDir() {
        try {
            CreateDirectory("LeapFrog");
            CreateDirectory("LeapFrog/Modules");
        } catch (IOException e) {
            LeapFrog.log.info("Error loading files");
        }
    }

    public void CreateDirectory(String path) throws IOException {
        new File(path).mkdirs();
    }

    public String GetCurrentDirectory() throws IOException {
        return new java.io.File(".").getCanonicalPath();
    }

}
