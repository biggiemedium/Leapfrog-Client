package dev.leap.frog.Manager;


import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private String directoryleapfrog = "Leapfrog/";
    private String directoryModule = "/Module/";
    private String directoryGUI = "/GUI/";
    private String directoryFriends = "/Friends/";
    private String directoryBinds = "/Binds/";

    private String doubledirModule = directoryleapfrog + directoryModule;
    private String doubledirGUI = directoryleapfrog + directoryGUI;
    private String doubledirBinds = directoryleapfrog + directoryBinds;
    private String doubledFriends = directoryleapfrog + directoryFriends;

    private String friendsFile = "friends.json";
    private String bindsFile = "binds.txt";

    private Path pathleapfrog = Paths.get(directoryleapfrog);
    private Path pathModule = Paths.get(directoryleapfrog + directoryModule);
    private Path pathGUI = Paths.get(directoryleapfrog + directoryGUI);
    private Path pathFriends = Paths.get(directoryleapfrog + directoryFriends);
    private Path pathBinds = Paths.get(directoryleapfrog + directoryBinds);


    private void saveModule() throws IOException {
        for (Module m : LeapFrog.getModuleManager().getModules()) {

            final String file_name = doubledirModule + m.getName() + ".txt";
            final Path file_path = Paths.get(file_name);
            deleteFile(file_name);
            verifyFile(file_path);

            final File file = new File(file_name);
            final BufferedWriter br = new BufferedWriter(new FileWriter(file));

            for (Settings s : LeapFrog.getSettingsManager().getSettingByModule(m)) {
                switch (s.getType()) {
                    case "button":
                        br.write(s.getTag() + ":" + s.getValue(true) + "\r\n");
                        break;
                    case "combobox":
                        br.write(s.getTag() + ":" + s.getCurrentValue() + "\r\n");
                        break;
                    case "label":
                        br.write(s.getTag() + ":" + s.getValue("") + "\r\n");
                        break;
                    case "doubleslider":
                        br.write(s.getTag() + ":" + s.getValue(1.0) + "\r\n");
                        break;
                    case "integerslider":
                        br.write(s.getTag() + ":" + s.getValue(1) + "\r\n");
                        break;
                }
            }
            br.close();
        }
    }

    private void loadModule() throws IOException {
        for (Module m : LeapFrog.getModuleManager().getModules()) {

            final String file_name = doubledirModule + m.getName() + ".txt";
            final File file = new File(file_name);
            final FileInputStream fi_stream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream di_stream = new DataInputStream(fi_stream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(di_stream));

            final List<String> bugged_lines = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null) {

                try {
                    final String colune = line.trim();
                    final String tag = colune.split(":")[0];
                    final String value = colune.split(":")[1];

                    Settings setting = LeapFrog.getSettingsManager().getSettingsbyTag(m, tag);

                    // send_minecraft_log("Attempting to assign value '" + value + "' to setting '" + tag + "'");

                    try {
                        switch (setting.getType()) {
                            case "button":
                                setting.setValue(Boolean.parseBoolean(value));
                                break;
                            case "label":
                                setting.setValue(value);
                                break;
                            case "doubleslider":
                                setting.setValue(Double.parseDouble(value));
                                break;
                            case "integerslider":
                                setting.setValue(Integer.parseInt(value));
                                break;
                            case "combobox":
                                setting.setCurrentValue(value);
                                break;
                        }
                    } catch (Exception e) {
                        bugged_lines.add(colune);
                        LeapFrog.log.info("Error loading '" + value + "' to setting '" + tag + "'");
                        break;
                    }
                } catch (Exception ignored) {}
            }
            br.close();
        }
    }

    private void saveBinds() throws IOException {
        final String file_name = doubledirBinds + "BINDS.txt";
        final Path file_path = Paths.get(file_name);

        this.deleteFile(file_name);
        this.verifyFile(file_path);
        final File file = new File(file_name);
        final BufferedWriter br = new BufferedWriter(new FileWriter(file));
        for (final Module m : LeapFrog.getModuleManager().getModules()) {
            br.write(m.getName() + ":" + m.getKey() + ":" + m.isToggled() + "\r\n");
        }
        br.close();
    }

    private void loadBinds() throws IOException {
        final String file_name = doubledirBinds + "BINDS.txt";
        final File file = new File(file_name);
        final FileInputStream fi_stream = new FileInputStream(file.getAbsolutePath());
        final DataInputStream di_stream = new DataInputStream(fi_stream);
        final BufferedReader br = new BufferedReader(new InputStreamReader(di_stream));
        String line;
        while ((line = br.readLine()) != null) {
            try {
                final String colune = line.trim();
                final String tag = colune.split(":")[0];
                final String bind = colune.split(":")[1];
                final String active = colune.split(":")[2];
                final Module module = LeapFrog.getModuleManager().getModuleName(tag);
                module.setKey(Integer.parseInt(bind));
                module.setToggled(Boolean.parseBoolean(active));
            } catch (Exception ignored) {}
        }
        br.close();
    }



    public void saveConfig() {
        try {
            verifyDir(pathleapfrog);
            verifyDir(pathModule);
            verifyDir(pathGUI);
            verifyDir(pathFriends);
            verifyDir(pathBinds);

            saveModule();
            saveBinds();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadConfig() {
        try {
            loadModule();
            loadBinds();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // utils

    public boolean deleteFile(final String path) {
        final File f = new File(path);
        return f.delete();
    }

    public void verifyFile(final Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    public void verifyDir(final Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
    }

}
