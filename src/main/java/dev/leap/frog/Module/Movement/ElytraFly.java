package dev.leap.frog.Module.Movement;

import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Event.Movement.EventPlayerTravel;
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

    private enum Mode {
        Control,
        Packet
    }

    @Override
    public void onUpdate() {
        getArrayDetails();
    }

    @EventHandler
   public Listener<EventPlayerMotionUpdate> MotionListener = new Listener<>(event-> {

       if(mc.player.isElytraFlying()){
           mc.player.motionY = 0;
           mc.player.moveForward = 1.32F;
           if (Playerutil.isMoving(mc.player)) {



           }
       }
   });

    @EventHandler
    private Listener<EventPlayerTravel> travelListener = new Listener<>(event -> {

        if(mc.player == null || !mc.player.isElytraFlying() || mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() != Items.ELYTRA || mc.player.isDead) return;




    });

    @Override
    public String getDescription() {
        return mode.getName();
    }
}
