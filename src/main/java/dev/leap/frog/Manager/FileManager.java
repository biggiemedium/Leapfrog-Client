package dev.leap.frog.Manager;


import dev.leap.frog.GUI.Click;
import dev.leap.frog.GUI.ClickGUI.Frame;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Module.ui.ClickGUIModule;
import dev.leap.frog.Util.Entity.Friendutil;
import dev.leap.frog.Util.Wrapper;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class FileManager {

    private String directoryleapfrog = "Leapfrog/";
    private String directoryModule = "/Module/";
    private String directoryGUI = "/GUI/";
    private String directoryFriends = "/Friends/";
    private String directoryBinds = "/Binds/";

    public void verifyDirectory() {
        File leapfrog = new File("Leapfrog/");
        File module = new File(directoryleapfrog + directoryModule);
        File gui = new File(directoryleapfrog + directoryGUI);
        File friends = new File(directoryleapfrog + directoryFriends);
        File binds = new File(directoryleapfrog + directoryBinds);

        if(!leapfrog.exists()) {
            leapfrog.mkdirs();
        }

        if(!module.exists()) {
            module.mkdirs();
        }

        if(!gui.exists()) {
            gui.mkdirs();
        }

        if(!friends.exists()) {
            friends.mkdirs();
        }

        if(!binds.exists()) {
            binds.mkdirs();
        }
    }

    public void saveFriends() throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(getDirectoryFriends() + "friends.json"));

        Iterator iterator = FriendManager.getFriend().iterator();
        while (iterator.hasNext()) {
            Friendutil f = (Friendutil) iterator.next();
            writer.write(f.getName());
            writer.write("\r\n");
        }
        writer.close();
    }

    public void loadFriend() throws IOException {
        FileInputStream fstream = new FileInputStream(getDirectoryFriends() + "friends.json");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        FriendManager.getFriend().clear();
        String line;
        while ((line = br.readLine()) != null) {
            LeapFrog.getFriendManager().addFriend(line);
        }
        br.close();
    }

    public void saveModule() {
        for(Module m : LeapFrog.getModuleManager().getModules()) {

        }
    }

    public void save() {
        try {
            saveFriends();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDirectoryleapfrog() {
        return directoryleapfrog;
    }

    public String getDirectoryModule() {
        return directoryleapfrog + directoryModule;
    }

    public String getDirectoryGUI() {
        return directoryleapfrog + directoryGUI;
    }

    public String getDirectoryFriends() {
        return directoryleapfrog + directoryFriends;
    }

    public String getDirectoryBinds() {
        return directoryleapfrog + directoryBinds;
    }
}
