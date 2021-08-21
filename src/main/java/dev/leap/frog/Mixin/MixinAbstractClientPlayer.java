package dev.leap.frog.Mixin;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.CapeManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer {

    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo getPlayerInfo();

    @Inject(method = "getLocationCape", at = @At(value = "HEAD"), cancellable = true)
    public void drawCapes(CallbackInfoReturnable<ResourceLocation> cir) {
        UUID uuid = Objects.requireNonNull(getPlayerInfo()).getGameProfile().getId();
        if(LeapFrog.getModuleManager().getModuleName("Capes").isToggled() && LeapFrog.getCapeManager().hasCape(uuid)) {

            cir.setReturnValue(new ResourceLocation("cocked.png"));

        } else if(LeapFrog.getCapeManager().hasDevCape(uuid)) {
            cir.setReturnValue(new ResourceLocation("cocked.png"));
        }else{
            cir.setReturnValue(new ResourceLocation("cocked.png"));
        }

    }
}


