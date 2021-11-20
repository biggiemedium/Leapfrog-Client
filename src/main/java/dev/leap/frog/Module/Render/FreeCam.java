package dev.leap.frog.Module.Render;

import com.mojang.authlib.GameProfile;
import dev.leap.frog.Event.Movement.EventPlayerMove;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Math.Mathutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.*;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import org.apache.http.util.EntityUtils;

import java.util.UUID;

public class FreeCam extends Module {

    public FreeCam() {
        super("FreeCam", "FreeCam", Type.RENDER);
    }

    Setting<Float> speed = create("speed", 1.0f, 0.0f, 10.0f);
    Setting<Boolean> renderBody = create("Render Body", false);

    private EntityOtherPlayerMP fakePlayer;
    private float yaw;
    private float pitch;
    private boolean canFly;
    private double x;
    private double y;
    private double z;
    private float creativeSpeed;

    @Override
    public void onEnable() {
        if(mc.world == null)
            return;

        if(mc.player.capabilities.isCreativeMode || mc.player.isSpectator()) {
            canFly = true;
        }

        this.creativeSpeed = mc.player.capabilities.getFlySpeed();
        this.x = mc.player.posX;
        this.y = mc.player.posY;
        this.z = mc.player.posZ;
        this.yaw = mc.player.rotationYaw;
        this.pitch = mc.player.rotationPitch;
        mc.player.motionY = 0.0;
        mc.player.setVelocity(0, 0, 0);
        mc.player.capabilities.allowFlying = true;

        fakePlayer = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
        mc.world.addEntityToWorld(-100, fakePlayer);
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        mc.player.noClip = true;
        fakePlayer.inventory.copyInventory(mc.player.inventory);
    }

    @Override
    public void onUpdate() {
        mc.player.noClip = true;
        mc.player.capabilities.allowFlying = true;
        mc.player.setVelocity(0, 0, 0);
        mc.player.jumpMovementFactor = speed.getValue();
        mc.player.capabilities.setFlySpeed(speed.getValue() / 10.0f);

        if(mc.gameSettings.keyBindJump.isKeyDown()) {
            EntityPlayerSP me = mc.player;
            me.motionY += speed.getValue();
        }
        if(mc.gameSettings.keyBindSneak.isKeyDown()) {
            EntityPlayerSP me = mc.player;
            me.motionY -= speed.getValue();
        }

        if(!renderBody.getValue()) {
            mc.entityRenderer.renderHand = false;
        } else {
            mc.entityRenderer.renderHand = true;
        }
    }

    @Override
    public void onDisable() {
        mc.player.noClip = false;
        mc.world.removeEntity(fakePlayer);

        mc.player.setPositionAndRotation(x, y, z,  yaw, pitch);
        mc.player.capabilities.setFlySpeed(creativeSpeed);
        mc.player.capabilities.allowFlying = canFly ? true : false;
    }

    @EventHandler
    private Listener<EntityJoinWorldEvent> eventListener = new Listener<>(event -> {
        if(event.getEntity() == mc.player) {
            toggle();
        }
    });

    @EventHandler
    private Listener<EventPacket.SendPacket> sendPacketListener = new Listener<>(event -> {

        if(event.getPacket() instanceof CPacketPlayer
                || event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock
                || event.getPacket() instanceof CPacketPlayerTryUseItem
                || event.getPacket() instanceof CPacketVehicleMove
                || event.getPacket() instanceof CPacketInput
                || event.getPacket() instanceof CPacketPlayer.Position
                || event.getPacket() instanceof CPacketPlayer.Rotation
                || event.getPacket() instanceof CPacketPlayer.PositionRotation) {
            event.cancel();
        }

    });

}