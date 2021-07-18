package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Movement.EventPlayerMove;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Settings.Settings;
import dev.leap.frog.Util.Math.Mathutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;

public class FreeCam extends Module {

    public FreeCam() {
        super("FreeCam", "FreeCam", Type.RENDER);
    }

    Setting<Mode> mode = create("Mode", Mode.Normal);
    Setting<Float> speed = create("speed", 1.0f, 0.0f, 10.0f);

    private enum Mode {
        Normal,
        Packet
    }

    double x;
    double y;
    double z;

    float pitch;
    float yaw;

    private EntityOtherPlayerMP camera;
    private Entity ridingEntity;

    boolean isRiding;

    @Override
    public void onEnable() {

        if(mc.player == null || mc.world == null) return;

        camera = new EntityOtherPlayerMP(mc.world, mc.session.getProfile());
        camera.copyLocationAndAnglesFrom(mc.player);
        camera.rotationYawHead = mc.player.rotationYawHead;
        camera.rotationPitch = mc.player.rotationPitch;
        camera.inventory.copyInventory(mc.player.inventory);
        mc.world.addEntityToWorld(-100, camera);

        this.x = mc.player.posX;
        this.y = mc.player.posY;
        this.z = mc.player.posZ;

        this.pitch = mc.player.rotationPitch;
        this.yaw = mc.player.rotationYaw;
        mc.player.noClip = true;

        if(mc.player.isRiding()) {
            isRiding = true;
            ridingEntity = mc.player.getRidingEntity();
            mc.player.dismountRidingEntity();

            this.x = mc.player.posX;
            this.y = mc.player.posY;
            this.z = mc.player.posZ;
        }

    }

    @Override
    public void onUpdate() {

        if(mc.player == null || mc.world == null)
            return;

        mc.player.noClip = true;

        mc.player.setVelocity(0, 0, 0);

        mc.player.jumpMovementFactor = speed.getValue();
        mc.player.setSprinting(false);

        double movementFactorInputSpeed[] = Mathutil.directionSpeed(speed.getValue() / 2);

        if(mc.player.moveStrafing != 0 || mc.player.movementInput.moveForward != 0) {

            mc.player.motionX = movementFactorInputSpeed[0];
            mc.player.motionZ = movementFactorInputSpeed[1];

        } else {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
        }

        if(mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY += speed.getValue();
        }

        if(mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY -= speed.getValue();
        }

    }

    @Override
    public void onDisable() {
        mc.player.noClip = false;
        mc.player.setPositionAndRotation(x, y, z, yaw, pitch);

        mc.player.motionX = mc.player.motionY = mc.player.motionZ = 0;

        //camera = null;
        assert camera != null;
        mc.world.removeEntityFromWorld(-100);

        camera  = null;
        x     = 0;
        y     = 0;
        z     = 0;
        yaw   = 0;
        pitch = 0;

        if(isRiding) {
            mc.player.startRiding(ridingEntity, true);
        }
    }

    @EventHandler
    private Listener<EventPlayerMove> moveListener = new Listener<>(event -> {

        mc.player.noClip = true;

    });

    @EventHandler
    private Listener<PlayerSPPushOutOfBlocksEvent> spPushOutOfBlocksEventListener = new Listener<>(event -> {

        event.setCanceled(true);

    });

    @EventHandler
    private Listener<EventPacket.SendPacket> sendPacketListener = new Listener<>(event -> {

        if(mode.getValue() == Mode.Normal && event.getPacket() instanceof CPacketPlayer || event.getPacket() instanceof CPacketInput) {
            event.cancel();
        }

        if(mode.getValue() == Mode.Packet && event.getPacket() instanceof CPacketPlayer
                || event.getPacket() instanceof CPacketInput
                || event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock
                || event.getPacket() instanceof CPacketPlayerTryUseItem) {
            event.cancel();
        }

    });

}
/*
 * Old code below
 * for some reason the actual mc player would fly around not the fake playermp
 */
/*
    private ICamera camera = new Frustum();
    private EntityOtherPlayerMP fakePlayer;

    private float yaw;
    private float pitch;

    private Vec3d position;
    private Entity ridingEntity;

    private float defaultFlySpeed; // fixed fly speed on disable

    private int x;
    private int y;
    private int z;

    @Override
    public void onEnable() {
        if(mc.player == null || mc.world == null) return;

        fakePlayer = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.prevRotationYaw = mc.player.rotationYawHead;
        fakePlayer.inventory.copyInventory(mc.player.inventory);
        mc.world.addEntityToWorld(-100, fakePlayer);
        mc.player.noClip = true;
        position = mc.player.getPositionVector();
        yaw = mc.player.rotationYaw;
        pitch = mc.player.rotationPitch;

        if(ridingEntity != null) {
            mc.player.dismountRidingEntity();
        }
        mc.player.posZ = z;
        mc.player.posY = y;
        mc.player.posX = x;
        mc.player.noClip = true;
    }

    @Override
    public void onUpdate() {

        if(mc.player == null || mc.world == null)
            return;

        mc.player.noClip = true;
        mc.player.onGround = false;
        mc.player.fallDistance = 0;
        mc.player.setVelocity(0, 0, 0);
        mc.player.jumpMovementFactor = speed.getValue(1);

        final double[] dir = Mathutil.directionSpeed(this.speed.getValue(1) / 100f);
        if (mc.player.movementInput.moveStrafe != 0.0f || mc.player.movementInput.moveForward != 0.0f) {
            mc.player.motionX = dir[0];
            mc.player.motionZ = dir[1];
        }
        else {
            mc.player.motionX = 0.0;
            mc.player.motionZ = 0.0;
        }

        mc.player.setSprinting(false);

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY += this.speed.getValue(1);
        }

        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY -= this.speed.getValue(1);
        }

    }

    @EventHandler
    private Listener<EventPacket.SendPacket> listener = new Listener<>(event -> {

        if(event.getPacket() instanceof CPacketInput || event.getPacket() instanceof CPacketPlayer) {
            event.cancel();
        }

        if(mode.in("Packet") && event.getPacket() instanceof CPacketPlayerTryUseItem
                ||event.getPacket() instanceof CPacketInput
                || event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock
                || event.getPacket() instanceof CPacketPlayer) {
            event.cancel();
        }

    });
    // fixes on join issues

    @EventHandler
    private Listener<EntityJoinWorldEvent> joinWorldEventListener = new Listener<>(event -> {

        if(event.getEntity() == mc.player) {
            toggle();
            return;
        }

    });

    @EventHandler
    private Listener<EventPlayerMove> moveListener = new Listener<>(event -> {
        mc.player.noClip = true;
    });

    @EventHandler
    private Listener<PlayerSPPushOutOfBlocksEvent> pushlistener = new Listener<>(event -> {

        event.setCanceled(true);

    });

    @Override
    public void onDisable() {
        mc.player.setPosition(position.x, position.y, position.z);
        if(fakePlayer != null) {
            //mc.player.setPositionAndRotation(x, y, z, yaw, pitch);
            mc.world.removeEntity(fakePlayer);
            fakePlayer = null;
        }
        mc.setRenderViewEntity(mc.player);
        mc.player.setVelocity(0, 0, 0);
        mc.player.rotationYaw = yaw;
        mc.player.rotationPitch = pitch;
        mc.player.noClip = false;
    }
 */
