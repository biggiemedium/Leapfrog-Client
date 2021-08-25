package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.EventRenderNameTag;
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

}
