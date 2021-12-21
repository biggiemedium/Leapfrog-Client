package dev.leap.frog.Module.World;

import dev.leap.frog.Event.Movement.EventPlayerMove;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.client.event.ClientChatEvent;

public class AntiSuicide extends Module {

    public AntiSuicide() {
        super("Anti Suicide", "Trys to stop your from dying", Type.WORLD);
    }

    Setting<Boolean> kill = create("/kill", true);
    Setting<Boolean> fall = create("Fall Damage", true);


    @EventHandler
    private Listener<ClientChatEvent> chatEventListener = new Listener<>(event -> {
        String message = event.getMessage();
        if(kill.getValue()) {
            if (message.equals("/kill") || message.equals("/kill " + mc.player.getName())) {
                event.setCanceled(true);
            }
        }
    });

    @EventHandler
    private Listener<EventPlayerMove> moveListener = new Listener<>(event -> {
        if(fall.getValue() && !mc.player.isSneaking()) {
            if(mc.player.fallDistance > 5) {

            }
        }
    });

}
