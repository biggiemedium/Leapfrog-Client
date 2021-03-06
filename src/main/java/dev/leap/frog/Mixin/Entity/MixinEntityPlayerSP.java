package dev.leap.frog.Mixin.Entity;

import com.mojang.authlib.GameProfile;
import dev.leap.frog.Event.Movement.EventPlayerJump;
import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Event.Movement.EventPlayerMove;
import dev.leap.frog.Event.Network.EventPacketUpdate;
import dev.leap.frog.LeapFrog;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {

    public MixinEntityPlayerSP(World worldIn, GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    public void playerUpdate(MoverType type, double x, double y, double z, CallbackInfo ci) {
        EventPlayerMove packet = new EventPlayerMove(type, x, y, z);
        LeapFrog.EVENT_BUS.post(packet);

        if(packet.isCancelled()) {
            super.move(type, packet.getX(), packet.getY(), packet.getZ());
            ci.cancel();
        }
    }

    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    public void updateEvent(CallbackInfo ci) {
        EventPacketUpdate packet = new EventPacketUpdate();
        LeapFrog.EVENT_BUS.post(packet);

        if(packet.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
    public void onMotionUpdatePre(CallbackInfo ci) {
        EventPlayerMotionUpdate packet = new EventPlayerMotionUpdate(0);
        LeapFrog.EVENT_BUS.post(packet);
        if(packet.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("RETURN"), cancellable = true)
    public void onMotionUpdatePost(CallbackInfo ci) {
        EventPlayerMotionUpdate packet = new EventPlayerMotionUpdate(1);
        LeapFrog.EVENT_BUS.post(packet);
        if(packet.isCancelled()) {
            ci.cancel();
        }
    }

    @Override
    public void jump() {
        try {
            EventPlayerJump packet = new EventPlayerJump(motionX, motionZ);
            LeapFrog.EVENT_BUS.post(packet);

            if(!packet.isCancelled()) {
                super.jump();
            }
        } catch (Exception ignore) {}
    }
}
