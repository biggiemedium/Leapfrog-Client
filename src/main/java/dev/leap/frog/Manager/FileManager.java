package dev.leap.frog.Manager;


import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

   private Minecraft mc = Minecraft.getMinecraft();

   public File dir = new File(mc.mcDataDir, "Leapfrog");
   public File modulesname = new File(dir, "/Module/");
   public File friends = new File(dir, "/Friends/");
   public File settings = new File(dir, "/Settings/");

   public FileManager() {
       createDirectory();

       try {
           saveBinds();
           saveSettings();

           loadSettings();
       } catch (IOException e) {
           LeapFrog.log.info("Error saving dir");
       }
   }

   public void createDirectory() {
       if(!dir.exists()) {
           dir.mkdir();
       }

       if(!modulesname.exists()) {
           modulesname.mkdir();
       }

       if(!friends.exists()) {
           friends.mkdir();
       }

       if(!settings.exists()) {
           settings.mkdir();
       }

   }

   public void saveSettings() throws IOException {
       createDirectory();

       for(Module m : LeapFrog.moduleManager().getModules()) {
           String name = modulesname.toString() + m.getName() + ".txt";
           Path p = Paths.get(name);

            deleteFile(name);
            verifyFile(p);

            File f = new File(name);
            BufferedWriter br = new BufferedWriter(new FileWriter(f));

            for(Settings s : LeapFrog.getSettingsManager().get_settings_with_hack(m)) {
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

   public void saveBinds() throws IOException {
       String pathname = "Binds.txt";
       Path filename = Paths.get(pathname);

       deleteFile(pathname);
       verifyFile(filename);

       final File file = new File(String.valueOf(filename));
       final BufferedWriter br = new BufferedWriter(new FileWriter(file));
       for (final Module m : LeapFrog.moduleManager().getModules()) {
           br.write(m.getName() + ":" + m.getKey() + ":" + m.isToggled() + "\r\n");
       }
       br.close();

   }

   public void loadSettings() throws IOException {
       for (Module m : LeapFrog.moduleManager().getModules()) {

           final String name = modulesname.toString() + m.getName() + ".txt";
           final File f = new File(name);
           FileInputStream inputStream = new FileInputStream(f.getAbsolutePath());
           DataInputStream dataInputStream = new DataInputStream(inputStream);
           BufferedReader br = new BufferedReader(new InputStreamReader(dataInputStream));

           List<String> buggylines = new ArrayList<>();

           String line;

           while ((line = br.readLine()) != null) {
               try {
                   final String colune = line.trim();
                   final String tag = colune.split(":")[0];
                   final String value = colune.split(":")[1];

                   Settings setting = LeapFrog.getSettingsManager().get_setting_with_tag(m, tag);

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
                       buggylines.add(colune);
                       LeapFrog.log.info("Error loading '" + value + "' to setting '" + tag + "'");
                       break;
                   }

               } catch (Exception e) {
               }
           }
           br.close();
       }
   }

    public boolean deleteFile(final String path) throws IOException {
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
