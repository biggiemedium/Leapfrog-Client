package dev.leap.frog.Module.Movement;

import dev.leap.frog.Event.Movement.EventPlayerMove;
import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Module.Module;

import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Math.Mathutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Speed extends Module {
    public Speed() {
        super("Speed", "Allows you to edit your speed", Type.MOVEMENT);
    }


    Setting<Boolean> jump = create("Jump", true);
    Setting<SpeedMode> mode = create("Mode", SpeedMode.NCP);
    Setting<Boolean> backwards = create("Backwards", false);

    private int delay;

    private enum SpeedMode {
        Strafe,
        NCP
    }

    @Override
    public void onUpdate() {
        if(mc.player == null || mc.world == null || mc.player.isInWeb || mc.player.isInWater() || mc.player.isInLava() || mc.player.isRiding()) {
            return;
        }

        if(mode.getValue() == SpeedMode.Strafe) {

        }
    }

    @EventHandler
    public Listener<EventPlayerMove> MotionListener = new Listener<>(event -> {

        if(mode.getValue() == SpeedMode.NCP) {
            if(mc.player.isInLava() || mc.player.isRiding() || mc.player.isOnLadder() || mc.player.isInWeb || mc.player.isInWater()) {
                return;
            }


        }
    });

    @Override
    public String getArrayDetails() {
        return mode.getName();
    }

    private void NCP(EventPlayerMove event) {
        
    }
}
