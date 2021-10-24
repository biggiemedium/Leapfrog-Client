package dev.leap.frog.Manager;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Module.Combat.*;
import dev.leap.frog.Module.Exploit.*;
import dev.leap.frog.Module.Misc.*;
import dev.leap.frog.Module.Client.*;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Module.Movement.*;
import dev.leap.frog.Module.Movement.ElytraFly;
import dev.leap.frog.Module.Render.*;
import dev.leap.frog.Module.ui.ClickGUIModule;
import dev.leap.frog.Module.World.*;
import dev.leap.frog.Module.ui.HudEditorModule;
import dev.leap.frog.Util.Math.Mathutil;
import dev.leap.frog.Util.Render.RenderHelputil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {

        // Client
        Add(new AutoEZ());
        Add(new AutoReply());
        Add(new Suffix());
        Add(new Blur());
        Add(new ToggleMessage());

        //// Combat
        Add(new AutoTotem());
        Add(new AutoTrap());
        Add(new AutoWeb());
        Add(new AutoLog());
        Add(new AutoXP());
        Add(new BedAura());
        Add(new CrystalAura());
        Add(new Criticals());
        Add(new KillAura());
        Add(new OffHand());
        Add(new Velocity());


        ////GUI
        Add(new ClickGUIModule());
        Add(new HudEditorModule());

        // Misc
        Add(new AntiSound());
        Add(new Test());
        Add(new FastUse());
        Add(new MiddleClickFriends());
        Add(new MiddleClickPearl());
        Add(new TotemPopCounter());

        // Exploit
        Add(new XCarry());
        Add(new CoordExploit());
        Add(new LagBack());
        Add(new ElytraBypass());
        Add(new SpeedMine());

        // World
        Add(new StrengthDetect());
        Add(new LawnMower());
        Add(new FakePlayer());
        Add(new SkyColor());
        Add(new GOTO());
        Add(new OffhandSwing());

        // Render
        Add(new FullBright());
        Add(new NoRender());
        Add(new HoleESP());
        Add(new Capes());
        Add(new HandSize());
        Add(new Tracers());
        Add(new ESP());
        Add(new FreeCam());
        Add(new SuperHeroFX());
        Add(new XRay());

        //Movement
        Add(new Speed());
        Add(new Sprint());
        Add(new ElytraFly());
        Add(new NoSlow());
        Add(new NoRotate());
        Add(new ReverseStep());
    }

    public void Add(Module m) {
        modules.add(m);
    }

    public Module getModuleName(String name) {
        for(Module m : modules) {
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

        for(Module m : modules) {
            if(m.getType() == type) {
                modulestype.add(m);
            }
        }
        return modulestype;
    }

    public Module getModule(Class clazz) {

        for(Module m : getModules()) {
            if(m.getClass() == clazz) {
                return m;
            }
        }

        return null;
    }

    public static void onRender(RenderWorldLastEvent event) {

        Wrapper.getMC().mcProfiler.startSection("leapfrog");
        Wrapper.getMC().mcProfiler.startSection("setup");

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(1f);

        Vec3d pos = Mathutil.getInterpolatedPos(Wrapper.getPlayer(), event.getPartialTicks());
        RenderEvent renderEvent = new RenderEvent(RenderHelputil.INSTANCE, pos);
        renderEvent.resetTranslation();
        Wrapper.getMC().mcProfiler.endSection();

        for (Module modules : modules) {
            if (modules.isToggled()) {
                Wrapper.getMC().mcProfiler.startSection(modules.getName());
                modules.onRender(renderEvent);
                Wrapper.getMC().mcProfiler.endSection();
            }
        }

        Wrapper.getMC().mcProfiler.startSection("release");

        GlStateManager.glLineWidth(1f);
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();

        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();

        Wrapper.getMC().mcProfiler.endSection();
        Wrapper.getMC().mcProfiler.endSection();

    }

    public static void onUpdate() {
        for(Module m : modules) {
            if(m.isToggled()) {
                m.onUpdate();
            }
        }
    }
}
