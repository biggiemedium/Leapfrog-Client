package dev.leap.frog.Mixin.Render;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Combat.KillAura;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderLiving.class)
public class MixinRenderLiving<T extends Entity> {

    //@Inject(method = { "doRender" }, at = { @At("HEAD") })
    //private void injectChamsPre(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
    //    if (LeapFrog.getModuleManager().getModule(KillAura.class).isToggled() && KillAura.INSTANCE.renderTarget.getValue()) {
    //        if(Wrapper.getMC().player.getDistance(entity) < KillAura.INSTANCE.distance.getValue()) {
    //            GL11.glEnable(32823);
    //            GL11.glPolygonOffset(1.0f, -1000000.0f);
    //        }
    //    }
    //}

}
