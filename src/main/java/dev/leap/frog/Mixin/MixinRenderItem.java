package dev.leap.frog.Mixin;

import dev.leap.frog.Event.Render.EventRenderArmor;
import dev.leap.frog.LeapFrog;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerArmorBase.class)
public class MixinRenderItem {

    @Inject(method = "renderArmorLayer", at = @At("HEAD"), cancellable = true)
    public void cancelArmorRender(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn, CallbackInfo ci) {

        EventRenderArmor packet = new EventRenderArmor();
        LeapFrog.EVENT_BUS.post(packet);

        if(packet.isCancelled()) {
            ci.cancel();
        }

    }

}
