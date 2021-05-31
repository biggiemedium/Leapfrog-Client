package dev.leap.frog.Manager;

import dev.leap.frog.Module.Combat.Velocity;
import dev.leap.frog.Module.Movement.ElytraBypass;
import dev.leap.frog.Module.Exploit.XCarry;
import dev.leap.frog.Module.Misc.Test;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Module.Render.FullBright;
import dev.leap.frog.Module.ui.ClickGUIModule;
import dev.leap.frog.Module.ui.FakePlayer;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {

        // Combat
        Add(new Velocity());

        //GUI
        Add(new ClickGUIModule());
        Add(new FakePlayer());

        // Misc
        Add(new Test());

        // Exploit
        Add(new XCarry());

        // Render
        Add(new FullBright());
        //Movement
        Add(new ElytraBypass());
    }

    public void Add(Module m) {
        modules.add(m);
    }

    public Module getModuleName(String name) {
        for(Module m : this.modules) {
            if(m.getName().equals(name)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public List<Module> getModuleByType(Module.Type type) {
        List<Module> modulestype = new ArrayList<>();

        for(Module m : this.modules) {
            if(m.getType() == type) {
                modulestype.add(m);
            }
        }
        return modulestype;
    }

    public static void onUpdate() {
        for(Module m : modules) {
            if(m.isToggled()) {
                m.onUpdate();
            }
        }
    }

}
