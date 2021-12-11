package dev.leap.frog.Module.Movement;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import net.minecraft.network.play.client.CPacketEntityAction;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", "Automatically runs for you", Type.MOVEMENT);
    }

    Setting<Mode> mode = create("Mode", Mode.Legit);

    private enum Mode {
        Legit,
        Packet,
        Rage
    }

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck()) return;
        if(mode.getValue() == Mode.Legit && mc.player.moveForward > 0 || mc.player.moveStrafing > 0 && mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() && !mc.player.isSneaking()) {
            mc.player.setSprinting(true);
        }

        if(mode.getValue() == Mode.Packet) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));
        }

        if(mode.getValue() == Mode.Rage && Playerutil.isMoving(mc.player) || mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
            mc.player.setSprinting(true);
        }
    }

    @Override
    public void onDisable() {
        mc.player.setSprinting(false);
    }

    @Override
    public String getArrayDetails() {
        return mode.getValue().name();
    }
}
