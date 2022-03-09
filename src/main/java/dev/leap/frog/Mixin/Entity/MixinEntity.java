package dev.leap.frog.Mixin.Entity;

import dev.leap.frog.Event.Movement.EventEntityCollision;
import dev.leap.frog.LeapFrog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntity {


    @Shadow
    public void move(MoverType type, double x, double y, double z)
    {

    }

    @Shadow
    public double motionX;

    @Shadow
    public double motionY;

    @Shadow
    public double motionZ;

    @Inject(method = "applyEntityCollision", at = @At("HEAD"), cancellable = true)
    public void onCollision(Entity entityIn, CallbackInfo ci) {
        EventEntityCollision event = new EventEntityCollision();
        LeapFrog.EVENT_BUS.post(event);

        if(event.isCancelled()) {
            ci.cancel();
        }
    }

    @Redirect(method = "applyEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
    public void removeSlow(Entity entity, double x, double y, double z) {
        if(!LeapFrog.getModuleManager().getModuleName("NoSlow").isToggled()) {
            entity.motionX += x;
            entity.motionY += y;
            entity.motionZ += z;
            entity.isAirBorne = true;
        }
    }

}
