package dev.leap.frog.Manager;


import dev.leap.frog.GUI.Click;
import dev.leap.frog.GUI.ClickGUI.Frame;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
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

    public void saveModule() throws IOException {

        if(!doesDirectoryExist()) {
            verifyDirectory();
        }

        for(Module m : LeapFrog.getModuleManager().getModules()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getDirectoryModule() + m.getName() + ".txt"));

            String toggled = m.isToggled() ? "on" : "off";
            writer.write(toggled);
            writer.close();
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

    public void loadModule() throws IOException {

        ArrayList<String> lines = new ArrayList<>();
        for(Module m : LeapFrog.getModuleManager().getModules()) {
            BufferedReader reader = new BufferedReader(new FileReader(getDirectoryModule() + m.getName() + ".txt"));

            String line = reader.readLine();

            while(line != null) {
                lines.add(line);
                line = reader.readLine();
            }

            reader.close();

            for(String s : lines) {
                if(s.equalsIgnoreCase("on")) {
                    m.setToggled(true);
                } else {
                    m.setToggled(false);
                }
            }

        }
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

    public void saveHidden() throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(getDirectoryModule() + "hidden.json"));

        for(Module m : LeapFrog.getModuleManager().getModules()) {
            String isHidden = m.isHidden() ? "hidden" : "visible";

            writer.write(m.getName() + ":" + isHidden + "\n");

        }
        writer.close();
    }

    public void loadHidden() throws IOException {
        BufferedReader writer = new BufferedReader(new FileReader(getDirectoryModule() + "hidden.json"));
        ArrayList<String> arrayLine = new ArrayList<>();

        for(Module m : LeapFrog.getModuleManager().getModules()) {
            String line = writer.readLine();

            while(line != null) {
                arrayLine.add(line);
                line = writer.readLine();
            }
            writer.close();

            for(String s : arrayLine) {
                if(s.toLowerCase().contains("hidden")) {
                    m.setHidden(true);
                } else if(s.toLowerCase().contains("shown")) {
                    m.setHidden(false);
                }
            }

        }
    }

    public void load() {
        try {
            loadModule();
            loadFriend();

            loadHidden();
        } catch (Exception e) {
            verifyDirectory();
        }
    }

    public void save() {
        try {
            verifyDirectory();
            saveModule();
            saveFriends();
            saveHidden();
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

    public String convertBind(String key, Module m) {

        if(m.getKey() < 0) {
            key = "NONE";
        }

        key = Keyboard.getKeyName(m.getKey());

        return key;
    }

}
