package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.EventRenderNameTag;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Module.Module;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;

public class NameTags extends Module {

    public NameTags() {
        super("NameTags", "Shows information about the player above their heads", Type.RENDER);
    }

    @SuppressWarnings("unused")
    @EventHandler
    private Listener<EventRenderNameTag> tagListener = new Listener<>(event -> {
        event.cancel();
    });

    @Override
    public void onRender(RenderEvent event) {
        double x,y,z;
        x = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * event.getPartialTicks();
        y = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * event.getPartialTicks();
        z = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * event.getPartialTicks();

        mc.world.playerEntities.forEach(player -> {
            
        });
    }
}
