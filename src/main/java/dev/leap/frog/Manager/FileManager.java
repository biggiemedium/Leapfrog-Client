package dev.leap.frog.Manager;

import dev.leap.frog.GUI.Click;
import dev.leap.frog.GUI.ClickGUI.Frame;
import dev.leap.frog.GUI.ClickGUI2.ClickGUI;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Friendutil;

import java.io.*;
import java.util.HashMap;
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
        //loadSettings();
        loadModules();
        loadBinds();
        loadGUI();
    }

    /*
    What works:
    Module toggle
    Binds
    Friends
    GUI
     */

    public void save() {
        saveFriends();
        //saveSettings();
        saveModules();
        saveBinds();
        saveGUI();
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
           for(Setting<?> s : LeapFrog.getSettingManager().getSettingsArrayList()) {
               if(s.isBoolean()) {
                   File f = new File(this.settingsPath.getAbsolutePath(), "Values.txt");
                   if(!f.exists()) {
                       f.createNewFile();
                   }

                   BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                   writer.write(s.getModule().getName() + ":" + s.getModule() + ":" + s.getValue());
                   writer.write("\r\n");
                   writer.close();
               }

           }
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
        if(UtilManager.nullCheck()) return;
        try {
            File file = new File(this.GUIPath, "GUI.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(Frame f : Click.INSTANCE.getFrame()) {
                String s = f.isOpen() ? "open" : "closed";
                writer.write(f.getName() + ":" + f.getX() + ":" + f.getY() + ":" + s);
                writer.write("\r\n");
            }
            writer.close();
        } catch (Exception ignored) {}
    }

    private void loadGUI() {
        try {
            File f = new File(this.GUIPath, "GUI.txt");
            FileInputStream inputStream = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(inputStream);
            BufferedReader writer = new BufferedReader(new InputStreamReader(dis));
            String line;
            int xesd = 0;
            while ((line = writer.readLine()) != null) {
                String cl = line.trim();
                String name = cl.split(":")[0];
                String x = cl.split(":")[1];
                String y = cl.split(":")[2];
                String open = cl.split(":")[3];

                if(Click.INSTANCE.getFramebyName(name) == null) { // || !f.exists()
                    continue;
                }

                boolean openTab = open.equalsIgnoreCase("open") ? true : false;
                Click.INSTANCE.getFramebyName(name).setX(Integer.parseInt(x));
                Click.INSTANCE.getFramebyName(name).setY(Integer.parseInt(y));
                Click.INSTANCE.getFramebyName(name).setOpen(openTab);
            }

        } catch (Exception ignored) {}
    }

    private <T> void writeNumberSetting(File f, T type) {
        if(type instanceof Integer) {
            if(!f.exists()) {
                try {f.createNewFile(); } catch (Exception ignored) {}


            }
        }
    }
}
