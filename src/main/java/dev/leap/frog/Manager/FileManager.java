package dev.leap.frog.Manager;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Friendutil;

import java.io.*;
import java.util.Iterator;

public class FileManager extends UtilManager {

    public File path;
    private File settingsPath;
    private File modulePath;
    private File GUIPath;

    public FileManager() {
        path = new File(mc.mcDataDir + File.separator + LeapFrog.MODID);

        if(!path.exists()) {
            path.mkdirs();
        }

        settingsPath = new File(path + File.separator + "Settings");
        if(!settingsPath.exists()) {
            settingsPath.mkdirs();
        }

        modulePath = new File(path + File.separator + "Module");
        if(!modulePath.exists()) {
            modulePath.mkdirs();
        }

        GUIPath = new File(path + File.separator + "GUI");
        if(!GUIPath.exists()) {
            GUIPath.mkdirs();
        }

        loadFriend();
        loadSettings();
        loadModules();
        loadBinds();
    }

    public void save() {
        saveFriends();
        saveSettings();
        saveModules();
        saveBinds();
    }

    public void saveFriends() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.path + File.separator + "friends.json"));
            Iterator iterator = FriendManager.getFriend().iterator();
            while (iterator.hasNext()) {
                Friendutil f = (Friendutil) iterator.next();
                writer.write(f.getName());
                writer.write("\r\n");
            }
            writer.close();
        } catch (Exception ignored) {}
    }

    public void loadFriend() {
        try {
            FileInputStream fstream = new FileInputStream(this.path + "friends.json");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            FriendManager.getFriend().clear();
            String line;
            while ((line = br.readLine()) != null) {
                LeapFrog.getFriendManager().addFriend(line);
            }
            br.close();
        } catch (Exception ignored) {}
    }

    private void saveModules() {
        try {
            File file = new File(this.modulePath, "Modules.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (Module module : LeapFrog.getModuleManager().getModules()) {
                try {
                    if (module.isToggled() && !module.getName().matches("null") && !module.getName().equals("Freecam") && !module.getName().equals("Blink")) {
                        out.write(module.getName());
                        out.write("\r\n");
                    }
                } catch(Exception e) {}
            }
            out.close();
        }
        catch (Exception ignored) {}
    }

    public void loadModules() {
        try {
            File file = new File(this.modulePath, "Modules.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                for (Module m : LeapFrog.getModuleManager().getModules()) {
                    try {
                        if (m.getName().equals(line)) {
                            m.setToggled(true);
                        }
                    } catch(Exception e) {}
                }
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            saveModules();
        }
    }

    public void saveBinds() {
        try {
            File file = new File(this.path, "Binds.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (Module module : LeapFrog.getModuleManager().getModules()) {
                try {
                    out.write(module.getName() + ":" + module.getKey());
                    out.write("\r\n");
                } catch(Exception e) {}
            }
            out.close();
        }
        catch (Exception ignored) {}
    }

    public void loadBinds() {
        try {
            File file = new File(this.path, "Binds.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String curLine = line.trim();
                    String name = curLine.split(":")[0];
                    String bind = curLine.split(":")[1];
                    int b = Integer.parseInt(bind);
                    Module m = LeapFrog.getModuleManager().getModuleName(name);
                    if (m != null) {
                        m.setKey(b);
                    }
                } catch(Exception e) {}
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            saveBinds();
        }
    }

    private void saveSettings() {
        try {
           
        } catch (Exception ignored) {}
    }

    private void loadSettings() {
        try {
            File f = new File(settingsPath.getAbsolutePath(), "Settings.txt");
            BufferedReader reader = new BufferedReader(new FileReader(f));

            String line;
            while((line = reader.readLine()) != null) {

            }
        } catch (Exception ignored) {}
    }

    private void saveGUI() {
        try {

        } catch (Exception ignored) {}
    }


}
