package dev.leap.frog.Mixin;

import dev.leap.frog.Event.World.EventAttackEnderCrystal;
import dev.leap.frog.LeapFrog;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityEnderCrystal.class)
public class MixinEnderCrystal {

    @Inject(method = "attackEntityFrom", at = @At("HEAD"), cancellable = true)
    public void onEntityAttacked(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {

        EventAttackEnderCrystal packet = new EventAttackEnderCrystal(source);
        LeapFrog.EVENT_BUS.post(packet);

        if(packet.isCancelled()) {
            cir.cancel();
        }

    }
}
