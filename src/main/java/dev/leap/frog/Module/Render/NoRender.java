package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.EventRenderArmor;
import dev.leap.frog.Event.Render.EventRenderBossBar;
import dev.leap.frog.Event.Render.EventRenderHurtCam;
import dev.leap.frog.Event.Render.EventRenderRain;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.MobEffects;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;

public class NoRender extends Module {

    public NoRender() {
        super("NoRender", "Prevents rendering of certain things", Type.RENDER);
    }

    Settings potions = create("Potions", "Potions", true);
    Settings hurtCam = create("hurt", "hurt", false);
    Settings fire = create("Fire", "Fire", true);
    Settings armor = create("Armor", "Armor", true);
    Settings pumpkin = create("pumpkin", "pumpkin", true);
    Settings bossbar = create("BossBar", "BossBar", true);
    Settings weather = create("Weather", "Weather", true);

    @Override
    public void onUpdate() {
        if(mc.world == null || mc.player == null)
            return;

        if(potions.getValue(true) && mc.player.isPotionActive(MobEffects.BLINDNESS)) {
            mc.player.removeActivePotionEffect(MobEffects.BLINDNESS);
        }
        if(potions.getValue(true) && mc.player.isPotionActive(MobEffects.NAUSEA)) {
            mc.player.removeActivePotionEffect(MobEffects.NAUSEA);
        }
    }

    @EventHandler
    private Listener<EventRenderHurtCam> info = new Listener<>(event -> {

        if(hurtCam.getValue(true)) {
            event.cancel();
        }

    });

    @EventHandler
    private Listener<RenderBlockOverlayEvent> blockListener = new Listener<>(event -> {
        if( fire.getValue(true) && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE ) {
            event.setCanceled(true);
        }
    });

    @EventHandler
    private Listener<RenderBlockOverlayEvent> pumpkinListener = new Listener<>(event -> {

        if(pumpkin.getValue(true) && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.BLOCK ) {
            event.setCanceled(true);
        }

    });

    @EventHandler
    private Listener<EventRenderBossBar> bossBarListener = new Listener<>(event -> {

        if(bossbar.getValue(true)) {
            event.cancel();
        }

    });

    @EventHandler
    private Listener<EventRenderRain> weatherListener = new Listener<>(event -> {

        if(weather.getValue(true)) {
            event.cancel();
        }

    });

    @EventHandler
    private Listener<EventRenderArmor> armorListener = new Listener<>(event -> {

        if(armor.getValue(true)) {
            event.cancel();
        }

    });

}
