package dev.leap.frog.Mixin;

import dev.leap.frog.Event.Render.EventRenderRain;
import dev.leap.frog.LeapFrog;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class MixinWorld {

    @Inject(method = "getRainStrength", at = @At("HEAD"), cancellable = true)
    public void eventCancelRain(float delta, CallbackInfoReturnable<Float> cir) {

        EventRenderRain packet = new EventRenderRain();
        LeapFrog.EVENT_BUS.post(packet);

        if(packet.isCancelled()) {
            cir.cancel();
            cir.setReturnValue(0.0f);
        }

    }

}
