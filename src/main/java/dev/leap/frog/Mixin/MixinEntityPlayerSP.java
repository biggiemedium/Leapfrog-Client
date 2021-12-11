package dev.leap.frog.Mixin;

import dev.leap.frog.Event.Movement.EventPlayerMove;
import dev.leap.frog.Event.Network.EventPacketUpdate;
import dev.leap.frog.LeapFrog;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    public void playerUpdate(MoverType type, double x, double y, double z, CallbackInfo ci) {

        EventPlayerMove packet = new EventPlayerMove(type, x, y, z);
        LeapFrog.EVENT_BUS.post(packet);

        if(packet.isCancelled()) {
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
}
