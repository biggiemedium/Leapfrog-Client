package dev.leap.frog.Module.Movement;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.InputUpdateEvent;

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow", "Stops you from slowing down", Type.MOVEMENT);
    }

    Settings items = create("Items", "Items", false);


    @Override
    public void onUpdate() {

        if(mc.player == null) return;

/*
        if(items.getValue(true) && mc.player.isHandActive() && !mc.player.isRiding()) {
                mc.player.movementInput.moveStrafe *= 5;
                mc.player.movementInput.moveForward *= 5;
        }

 */
    }

    @EventHandler
    private Listener<InputUpdateEvent> inputUpdateEventListener = new Listener<>(event-> {

        if(items.getValue(true)) {
            if (mc.player.isHandActive() && !mc.player.isRiding() && !mc.player.isElytraFlying()) {
                event.getMovementInput().moveStrafe *= 5;
                event.getMovementInput().moveForward *= 5;
            }
        }

    });



}
