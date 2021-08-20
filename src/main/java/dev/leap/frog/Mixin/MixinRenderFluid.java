package dev.leap.frog.Mixin;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Render.XRay;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockFluidRenderer.class)
public class MixinRenderFluid {


    @Inject(method = "renderFluid", at = @At("HEAD"), cancellable = true)
    public void onFluidRender(IBlockAccess blockAccess, IBlockState blockStateIn, BlockPos blockPosIn, BufferBuilder bufferBuilderIn, CallbackInfoReturnable<Boolean> cir) {
        if(XRay.INSTANCE.isToggled() && XRay.INSTANCE.isLiquidVisible()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
