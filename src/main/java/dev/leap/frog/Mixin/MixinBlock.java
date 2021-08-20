package dev.leap.frog.Mixin;

import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {

    @Inject(method = "setLightLevel", at = @At("HEAD"), cancellable = true)
    public void setLightLevel(float value, CallbackInfoReturnable<Block> cir) {

    }

}
