package dev.leap.frog.Mixin;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Util.Render.Capeutil;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer {

    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo getPlayerInfo();

    @Inject(method = "getLocationCape", at = @At(value = "RETURN"), cancellable = true)
    public void drawCapes(CallbackInfoReturnable<ResourceLocation> cir) {

        if(LeapFrog.getModuleManager().getModuleName("Capes").isToggled()) {

            NetworkPlayerInfo info = this.getPlayerInfo();
            assert info != null;

            if(!Capeutil.isUuidValid(info.getGameProfile().getId())) {
                return;
            }
        }
    }

}
