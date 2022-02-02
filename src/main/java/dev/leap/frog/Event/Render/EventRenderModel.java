package dev.leap.frog.Event.Render;

import dev.leap.frog.Event.LeapFrogEvent;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class EventRenderModel extends LeapFrogEvent {

    public ModelBase modelBase;
    public Entity entity;
    public float limbSwing;
    public float limbSwingAmount;
    public float ageInTicks;
    public float netHeadYaw;
    public float headPitch;
    public float scaleFactor;

    public EventRenderModel(ModelBase modelBaseIn, Entity entityIn, float limbSwingIn, float limbSwingAmountIn, float ageInTicksIn, float netHeadYawIn, float headPitchIn, float scaleFactorIn) {
        this.modelBase = modelBaseIn;
        this.entity = entityIn;
        this.limbSwing = limbSwingIn;
        this.limbSwingAmount = limbSwingAmountIn;
        this.ageInTicks = ageInTicksIn;
        this.netHeadYaw = netHeadYawIn;
        this.headPitch = headPitchIn;
        this.scaleFactor = scaleFactorIn;
    }

    public ModelBase getModelBase() {
        return modelBase;
    }

    public Entity getEntity() {
        return entity;
    }

    public float getLimbSwing() {
        return limbSwing;
    }

    public float getLimbSwingAmount() {
        return limbSwingAmount;
    }

    public float getAgeInTicks() {
        return ageInTicks;
    }

    public float getNetHeadYaw() {
        return netHeadYaw;
    }

    public float getHeadPitch() {
        return headPitch;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }
}
