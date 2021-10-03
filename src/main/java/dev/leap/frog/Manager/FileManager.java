package dev.leap.frog.Manager;


import dev.leap.frog.GUI.Click;
import dev.leap.frog.GUI.ClickGUI.Frame;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Module.ui.ClickGUIModule;
import dev.leap.frog.Util.Entity.Friendutil;
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

    public boolean doesDirectoryExist() {
        File leapfrog = new File("Leapfrog/");
        return leapfrog.exists();
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

    public void saveModule() throws IOException {
        for(Module m : LeapFrog.getModuleManager().getModules()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getDirectoryModule() + m.getName() + ".txt")); // change to json in future maybe?
            writer.write("Module:" + m.getName() + "\r\n");
            writer.write("Toggled:" + m.isToggled() + "\r\n");
            writer.write("Bind:" + m.getKey() + "\r\n");
            writer.write("Hidden:" + m.isHidden() + "\r\n");
            writer.close();
        }
    }

    public void loadModule() throws IOException {
        for(Module m : LeapFrog.getModuleManager().getModules()) {
            BufferedReader reader = new BufferedReader(new FileReader(getDirectoryModule() + m.getName() + ".txt"));
            String line;
            Module mod = null;
            while ((line = reader.readLine()) != null) {
                String[] regex = line.split(":");
                switch(regex[0]) {
                    case "Module":
                        LeapFrog.getModuleManager().getModuleName(regex[1]);
                        break;
                    case "Toggled":
                        if(mod != null && Boolean.parseBoolean(regex[1])) {
                            mod.setToggled(true);
                        }
                    case "Bind":
                        assert mod != null;
                            mod.setKey(Keyboard.getKeyIndex(regex[1]));
                        break;
                    case "Hidden":
                        if(mod != null && Boolean.parseBoolean(regex[1])) {
                            mod.setHidden(true);
                        }
                }
            }
            reader.close();
        }
    }

    public void saveOpen() throws IOException {
        for(Frame f : Click.INSTANCE.getFrame()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getDirectoryGUI() + f.getName() + ".txt"));
            writer.write("Open: " + f.isOpen());
            writer.close();
        }
    }

    public void loadOpen() throws IOException {
        for(Frame f : Click.INSTANCE.getFrame()) {
            BufferedReader reader = new BufferedReader(new FileReader(getDirectoryGUI() + f.getName() + ".txt"));
            String line;
            while((line = reader.readLine()) != null) {
                if(line.toLowerCase().startsWith("open") && line.toLowerCase().contains("true")) {
                    f.setOpen(true);
                } else {
                    f.setOpen(false);
                }
            }
            reader.close();
        }
    }

    public void load() {
        try {
            loadFriend();
            loadModule();
            saveOpen();
        } catch (Exception e) {
            verifyDirectory();
        }
    }

    public void save() {
        try {
            saveModule();
            saveFriends();
            saveOpen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveFriendsOnly() {
        try {
            saveFriends();
        } catch (Exception e) {

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
