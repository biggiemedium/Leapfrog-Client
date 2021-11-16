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

/*
    The concept for this file reader was taken from aurora and prestigeBase

 */

public class FileManager {

    private String directoryleapfrog = "Leapfrog/";
    private String directoryModule = "/Module/";
    private String directoryGUI = "/GUI/";
    private String directoryFriends = "/Friends/";
    private String directoryBinds = "/Binds/";

    private File path;
    private String splitter = "\r\n";

    public FileManager() {
        path = new File(Wrapper.getMC().mcDataDir + File.separator + LeapFrog.MODID);
        if(!path.exists())
            path.mkdir();

        save();
    }

    public void saveFriends() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + File.separator + "friends.json"));
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
            File catogoryPath = new File(path + File.separator + m.getType().getName()); // name instead of to string

            if(!catogoryPath.exists()) {
                catogoryPath.mkdir();
            }
            File mod = new File(catogoryPath.getAbsolutePath(), m.getName() + ".txt");
            if(!mod.exists() && catogoryPath.exists()) {
                try {mod.createNewFile();} catch(Exception ignored) {}
            }
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(mod));
                String toggled = m.isToggled() ? "Enabled" : "Disabled";
                out.write("Toggled:" + toggled);
                out.write(splitter);
                out.write("Key:" + m.getKey());
                out.close();
            } catch(Exception ignored) {}
        }
    }

    public void save() {
        try {
            saveFriends();
            saveModule();
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
