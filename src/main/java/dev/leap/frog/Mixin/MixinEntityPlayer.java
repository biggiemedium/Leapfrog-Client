package dev.leap.frog.Mixin;

import dev.leap.frog.Event.Movement.EventPlayerTravel;
import dev.leap.frog.LeapFrog;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer {

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    public void onPlayerTravel(float strafe, float vertical, float forward, CallbackInfo ci) {
        EventPlayerTravel packet = new EventPlayerTravel();
        LeapFrog.EVENT_BUS.post(packet);

        if(packet.isCancelled()) {
            ci.cancel();
        }
    }
}
