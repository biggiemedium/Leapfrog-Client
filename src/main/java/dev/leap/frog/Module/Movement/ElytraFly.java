package dev.leap.frog.Module.Movement;

import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Event.Movement.EventPlayerMove;
import dev.leap.frog.Event.Movement.EventPlayerTravel;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Exploit.TimerModule;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.lwjgl.input.Keyboard;

public class ElytraFly extends Module {

    public ElytraFly() {
        super("ElytraFly", "Allows you to fly in 2b!", Type.MOVEMENT);
        setKey(Keyboard.KEY_X);
    }

    Setting<Mode> mode = create("Mode", Mode.Control);
    Setting<Boolean> timerTakeoff = create("Timer Takeoff", true);

    private enum Mode {
        Control,
        Packet
    }

    @Override
    public void onUpdate() {
    }

    @EventHandler
   public Listener<EventPlayerMove> moveListener = new Listener<>(event-> {
       if(this.mode.getValue() == Mode.Control) {
           
       }
   });

    @EventHandler
    private Listener<EventPlayerTravel> travelListener = new Listener<>(event -> {

        if(UtilManager.nullCheck()) return;
        if(!hasElytra()) return;

    });

    private boolean hasElytra() {
        return mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA;
    }

    @Override
    public String getArrayDetails() {
        return mode.getName();
    }
}
