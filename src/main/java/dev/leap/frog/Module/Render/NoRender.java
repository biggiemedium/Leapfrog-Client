package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.*;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.MobEffects;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;

public class NoRender extends Module {

    public NoRender() {
        super("NoRender", "Prevents rendering of certain things", Type.RENDER);
    }

    Setting<Boolean> potions = create("Potions", true);
    Setting<Boolean> hurtCam = create("hurt", true);
    Setting<Boolean> fire = create("Fire", true);
    Setting<Boolean> armor = create("Armor",false);
    Setting<Boolean> pumpkin = create("pumpkin",true);
    Setting<Boolean> falling = create("Falling Animation", true); // TODO: add falling block animation removal
    Setting<Boolean> bossbar = create("BossBar", false);
    Setting<Boolean> weather = create("Weather", true);
    Setting<Boolean> nametags = create("NameTags", false);

    @Override
    public void onUpdate() {
        if(mc.world == null || mc.player == null)
            return;

        if(potions.getValue() && mc.player.isPotionActive(MobEffects.BLINDNESS)) {
            mc.player.removeActivePotionEffect(MobEffects.BLINDNESS);
        }
        if(potions.getValue() && mc.player.isPotionActive(MobEffects.NAUSEA)) {
            mc.player.removeActivePotionEffect(MobEffects.NAUSEA);
        }
    }

    @EventHandler
    private Listener<EventRenderHurtCam> info = new Listener<>(event -> {

        if(hurtCam.getValue()) {
            event.cancel();
        }

    });

    @EventHandler
    private Listener<RenderBlockOverlayEvent> blockListener = new Listener<>(event -> {
        if( fire.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE ) {
            event.setCanceled(true);
        }
    });

    @EventHandler
    private Listener<RenderBlockOverlayEvent> pumpkinListener = new Listener<>(event -> {

        if(pumpkin.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.BLOCK ) {
            event.setCanceled(true);
        }

    });

    @EventHandler
    private Listener<EventRenderBossBar> bossBarListener = new Listener<>(event -> {

        if(bossbar.getValue()) {
            event.cancel();
        }

    });

    @EventHandler
    private Listener<EventRenderRain> weatherListener = new Listener<>(event -> {

        if(weather.getValue()) {
            event.cancel();
        }

    });

    @EventHandler
    private Listener<EventRenderArmor> armorListener = new Listener<>(event -> {

        if(armor.getValue()) {
            event.cancel();
        }

    });

    @EventHandler
    private Listener<EventRenderBlockFall> blockFallListener = new Listener<>(event -> {

        if(falling.getValue()) {
            event.cancel();
        }

    });

    @EventHandler
    private Listener<EventRenderNameTag> tagListener = new Listener<>(event -> {

        if(nametags.getValue()) {
            event.cancel();
        }

    });

}
