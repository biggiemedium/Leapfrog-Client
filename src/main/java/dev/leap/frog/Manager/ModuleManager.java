package dev.leap.frog.Manager;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Module.Combat.*;
import dev.leap.frog.Module.Chat.Announcer;
import dev.leap.frog.Module.Chat.AutoReply;
import dev.leap.frog.Module.Exploit.Pingspoof;
import dev.leap.frog.Module.Misc.FastUse;
import dev.leap.frog.Module.Misc.MiddleClickFriends;
import dev.leap.frog.Module.Movement.ElytraBypass;
import dev.leap.frog.Module.Exploit.XCarry;
import dev.leap.frog.Module.Misc.Test;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Module.Movement.NoSlow;
import dev.leap.frog.Module.Movement.Speed;
import dev.leap.frog.Module.Render.Capes;
import dev.leap.frog.Module.Render.FullBright;
import dev.leap.frog.Module.Render.HoleESP;
import dev.leap.frog.Module.Render.NoRender;
import dev.leap.frog.Module.ui.ClickGUIModule;
import dev.leap.frog.Module.World.FakePlayer;
import dev.leap.frog.Util.Mathutil;
import dev.leap.frog.Util.Wrapper;
import dev.px.turok.draw.RenderHelp;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {

        // Chat
        Add(new AutoReply());
        Add(new Announcer());

        // Combat
        Add(new Velocity());
        Add(new CrystalAura());
        Add(new AutoTotem());
        Add(new OffHand());
        Add(new AutoXP());

        //GUI
        Add(new ClickGUIModule());
        Add(new FakePlayer());

        // Misc
        Add(new Test());
        Add(new FastUse());

        Add(new MiddleClickFriends());

        // Exploit
        Add(new Pingspoof());
        Add(new XCarry());

        // Render
        Add(new FullBright());
        Add(new NoRender());
        Add(new HoleESP());
        Add(new Capes());

        //Movement
        Add(new Speed());
        Add(new ElytraBypass());
        Add(new NoSlow());
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

    public static void onRender(RenderWorldLastEvent event) {

        Wrapper.GetMC().mcProfiler.startSection("wurstplus");
        Wrapper.GetMC().mcProfiler.startSection("setup");

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableDepth();

        GlStateManager.glLineWidth(1f);

        Vec3d pos = Mathutil.getInterpolatedPos(Wrapper.GetPlayer(), event.getPartialTicks());

        RenderEvent event_render = new RenderEvent(RenderHelp.INSTANCE, pos);

        event_render.reset_translation();

        Wrapper.GetMC().mcProfiler.endSection();

        for (Module modules : modules) {
            if (modules.isToggled()) {
                Wrapper.GetMC().mcProfiler.startSection(modules.getName());

                modules.onRender(event_render);

                Wrapper.GetMC().mcProfiler.endSection();
            }
        }

        Wrapper.GetMC().mcProfiler.startSection("release");

        GlStateManager.glLineWidth(1f);

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();

        RenderHelp.release_gl();

        Wrapper.GetMC().mcProfiler.endSection();
        Wrapper.GetMC().mcProfiler.endSection();

    }

    public static void onUpdate() {
        for(Module m : modules) {
            if(m.isToggled()) {
                m.onUpdate();
            }
        }
    }

}
