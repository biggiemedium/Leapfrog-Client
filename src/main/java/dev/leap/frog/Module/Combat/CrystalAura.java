package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.Movement.PlayerMotionUpdateEvent;
import dev.leap.frog.Module.Module;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.EntityLivingBase;

import java.util.List;
import java.util.stream.Collectors;

public class CrystalAura extends Module {
    public CrystalAura() {
        super("CrystalAura", "Places Crystals To Kill Players", Type.COMBAT);

    }
    public void onEnable(){

    }

    @EventHandler
    public Listener<PlayerMotionUpdateEvent> MotionListener = new Listener<>(event -> {

    });
}
