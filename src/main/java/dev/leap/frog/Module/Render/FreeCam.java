package dev.leap.frog.Module.Render;

import com.mojang.authlib.GameProfile;
import dev.leap.frog.Event.LeapFrogEvent;
import dev.leap.frog.Event.Movement.EventPlayerMove;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Math.Mathutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.*;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class FreeCam extends Module {

    public FreeCam() {
        super("FreeCam", "FreeCam", Type.RENDER);
    }

    Setting<Float> speed = create("speed", 1.0f, 0.0f, 10.0f);

    private EntityOtherPlayerMP player;
    private Entity riding;

    private float yaw;
    private float pitch;

    private double x;
    private double y;
    private double z;

    private double yOffset = speed.getValue();

    @Override
    public void onEnable() {

        if(mc.world == null)

            return;

        if(mc.player.isRiding()) {
            riding = mc.player.getRidingEntity();
        }

        mc.player.onGround = true;
        mc.player.motionY = 0f;

        yaw = mc.player.rotationYaw;
        pitch = mc.player.rotationPitch;

        x = mc.player.posX;
        y = mc.player.posY;
        z = mc.player.posZ;

        player = new EntityOtherPlayerMP(mc.world, new GameProfile(mc.player.getGameProfile().getId(), mc.player.getName()));
        player.inventory.copyInventory(mc.player.inventory);
        player.copyLocationAndAnglesFrom(mc.player);
        player.prevRotationYaw = mc.player.rotationYaw;
        player.prevRotationPitch = mc.player.rotationPitch;
        mc.world.addEntityToWorld(-100, player);

        if(mc.player.getRidingEntity() != null) {
            mc.player.dismountRidingEntity();
        }

    }

    @Override
    public void onUpdate() {
        mc.player.noClip = true;

        mc.player.setVelocity(0, 0, 0);

        double[] fast = Mathutil.directionSpeed(speed.getValue());
        if(Playerutil.isMoving(mc.player)) {
            mc.player.motionX = fast[0];
            mc.player.motionZ = fast[1];
        }

        if(mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY += yOffset;
        } else if(mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY -= yOffset;
        }

    }

    @Override
    public void onDisable() {

        //mc.player.startRiding(riding, true);
        mc.player.rotationYaw = yaw;
        mc.player.rotationPitch = pitch;

        if(player != null) {
            mc.world.removeEntity(player);
        }

        player = null;

        mc.player.noClip = false;
        mc.player.setPosition(x, y, z);
        mc.setRenderViewEntity(mc.player);

        mc.player.setVelocity(0, 0, 0);

    }

    @EventHandler
    private Listener<EntityJoinWorldEvent> eventListener = new Listener<>(event -> {

        if(event.getEntity() == mc.player) {
            toggle();
        }

    });

    @EventHandler
    private Listener<EventPlayerMove> moveListener = new Listener<>(event -> {

        mc.player.noClip = true;

    });

    @EventHandler
    private Listener<EventPacket.SendPacket> sendPacketListener = new Listener<>(event -> {

        if(event.getPacket() instanceof CPacketPlayer
                || event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock
                || event.getPacket() instanceof CPacketPlayerTryUseItem
                || event.getPacket() instanceof CPacketVehicleMove
                || event.getPacket() instanceof CPacketChatMessage
                || event.getPacket() instanceof CPacketInput) {
            event.cancel();
        }

    });

}