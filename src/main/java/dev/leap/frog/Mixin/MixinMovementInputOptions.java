package dev.leap.frog.Mixin;

import dev.leap.frog.Event.Movement.EventPLayerMoveState;
import dev.leap.frog.LeapFrog;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MovementInputFromOptions.class)
public abstract class MixinMovementInputOptions extends MovementInput {

    @Inject(method = "updatePlayerMoveState", at = @At("HEAD"), cancellable = true)
    public void moveState(CallbackInfo ci) {

        EventPLayerMoveState packet = new EventPLayerMoveState();
        LeapFrog.EVENT_BUS.post(packet);

        if(packet.isCancelled()) {
            ci.cancel();
        }
    }
}
