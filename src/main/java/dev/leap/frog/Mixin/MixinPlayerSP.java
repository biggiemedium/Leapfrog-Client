package dev.leap.frog.Mixin;

import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.LeapFrog;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayerSP.class)
public class MixinPlayerSP {

    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
    public void preWalkingPlayer(CallbackInfo ci) {
        EventPlayerMotionUpdate event = new EventPlayerMotionUpdate(0);
        LeapFrog.EVENT_BUS.post(event);
        if(event.isCancelled()) {
            ci.cancel();
        }
    }



}
