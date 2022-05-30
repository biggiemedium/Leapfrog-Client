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
import dev.leap.frog.Module.ui.NewGUI;
import dev.leap.frog.Util.Math.Mathutil;
import dev.leap.frog.Util.Render.RenderTessellatorutil;
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
        Add(new ArmorNotify()); // done
        Add(new AutoEZ());
        Add(new AutoReply()); // done
        Add(new Blur()); // done
        Add(new ChatLogger());
        Add(new NewGUI());
        Add(new Suffix()); // done
        Add(new ToggleMessage()); // null error in module class

        //// Combat
        Add(new AutoTotem()); // done
        Add(new AutoWeb()); // done
        Add(new AutoLog()); // done
        Add(new AutoXP()); // done
        Add(new BedAura());
        Add(new CrystalAura());
        Add(new Criticals()); // done
        Add(new KillAura()); // done
        Add(new OffHand()); // done
        Add(new Trigger()); // done
        Add(new Velocity()); // done


        ////GUI
        Add(new ClickGUIModule()); // done
        Add(new HudEditorModule());

        // Misc
        Add(new AntiSound()); // done
        Add(new Blink()); // done
        Add(new Test());
        Add(new FastUse()); // done
        Add(new MiddleClickFriends()); // done
        Add(new MiddleClickPearl()); // done
        Add(new TotemPopCounter()); // done

        // Exploit
        Add(new AntiLog4J()); // done
        Add(new Crash()); // done
        Add(new ElytraBypass()); // Finish
        Add(new LagBack()); // done
        Add(new OffhandSwing()); // done
        Add(new Reach()); // done
        Add(new SpeedMine()); // Finish
        Add(new TimerModule()); // done
        Add(new XCarry()); // done

        // World
        Add(new AntiSuicide()); // add more
        Add(new AutoTool());
        Add(new Copy()); // done
        Add(new FakePlayer()); // done
        Add(new MobOwner());
        Add(new Quiver()); // Fix null errors
        Add(new SignColor());
        Add(new StrengthDetect()); // havent checked if done, I think it spams chat

        // Render
        Add(new BlockHighlight()); // done
        Add(new BreadCrums()); // done
        Add(new BreakESP()); // done
        Add(new Capes()); // done
        Add(new ESP()); // Finish modes
        Add(new FullBright()); // done
        Add(new HandSize()); // done
        Add(new HoleESP()); // Finsih Render
        Add(new NameTags());
        Add(new NoRender()); // done
        Add(new OldAnimation()); // done
        Add(new SuperHeroFX());
        Add(new ViewClip()); // done

        //Movement
        Add(new AntiAim());
        Add(new Speed());
        Add(new FastSwim());
        Add(new FastWeb()); // done
        Add(new Sprint()); // done
        Add(new ElytraFly());
        Add(new Nofall()); // done
        Add(new NoSlow()); // Finish Packet
        Add(new NoRotate()); // done
        Add(new ReverseStep()); // done
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

    public ArrayList<Module> getEnabledModules() {
        ArrayList<Module> modulez = new ArrayList<>();
        for(Module m : this.getModules()) {
            if(m.isToggled()) {
                modulez.add(m);
            }
        }
        return modulez;
    }

    public ArrayList<Module> getDisbaledModules() {
        ArrayList<Module> modulez = new ArrayList<>();
        for(Module m : this.getModules()) {
            if(!m.isToggled()) {
                modulez.add(m);
            }
        }
        return modulez;
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
        RenderEvent renderEvent = new RenderEvent(RenderTessellatorutil.INSTANCE, pos);
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
