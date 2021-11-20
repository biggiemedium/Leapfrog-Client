package dev.leap.frog.Manager;


import dev.leap.frog.GUI.Click;
import dev.leap.frog.GUI.ClickGUI.Frame;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Module.ui.ClickGUIModule;
import dev.leap.frog.Settings.Setting;
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
    private String directorySettings = "/Setting/";

    private File path;
    private String splitter = "\r\n";

    public FileManager() {
        path = new File(Wrapper.getMC().mcDataDir + File.separator + LeapFrog.MODID);
        if(!path.exists())
            path.mkdir();

        load();
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
            File f = new File(path + File.separator + m.getType().getName()); // name instead of to string

            if(!f.exists()) {
                f.mkdir();
            }
            File mod = new File(f.getAbsolutePath(), m.getName() + ".txt");
            if(!mod.exists() && f.exists()) {
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

    public void loadModule() {
        for(Module m : LeapFrog.getModuleManager().getModules()) {
            try {
                File f = new File(path.getAbsolutePath() + File.separator + m.getType().toString());
                if(!f.exists()) continue;
                File mod = new File(f.getAbsolutePath(), m.getName() + ".txt");
                if(!mod.exists()) continue;
                FileInputStream fileInputStream = new FileInputStream(mod.getAbsolutePath());
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                BufferedReader reader = new BufferedReader(new InputStreamReader(dataInputStream));
                reader.lines().forEach(line -> {
                    String s = line.split(":")[0];
                    String on = s.split(":")[1];
                    if(s.equals("Toggled")) {
                        if(on.equalsIgnoreCase("Enabled")) {
                            m.setToggled(true);
                        }
                    }
                });
            } catch (Exception ignored) {}
        }
    }

    public void loadBind() {
        for(Module m : LeapFrog.getModuleManager().getModules()) {
            try {
                File f = new File(path.getAbsolutePath() + File.separator + m.getType().toString());
                if(!f.exists()) continue;
                File mod = new File(f.getAbsolutePath(), m.getName() + ".txt");
                if(!mod.exists()) continue;
                FileInputStream fileInputStream = new FileInputStream(mod.getAbsolutePath());
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                BufferedReader reader = new BufferedReader(new InputStreamReader(dataInputStream));
                reader.lines().forEach(line -> {
                    String s = line.split(":")[0];
                    String on = s.split(":")[1];
                    if(s.equalsIgnoreCase("Key")) {
                        if(on.equalsIgnoreCase("-1") || on.equalsIgnoreCase("0")) {
                            return;
                        }
                        m.setKey(Integer.parseInt(on));
                    }
                });
            } catch (Exception ignored) {}
        }
    }

    public void saveSettings() {
            File f = new File(path.getAbsolutePath() + File.separator + "Settings");
            if(!f.exists()) {
                f.mkdir();
            }

            for(Module m : LeapFrog.getModuleManager().getModules()) {

            }
    }

    public void save() {
        try {
            saveFriends();
            saveModule();
            saveSettings();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
         //   loadModule();
            loadFriend();
       //     loadBind();
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
