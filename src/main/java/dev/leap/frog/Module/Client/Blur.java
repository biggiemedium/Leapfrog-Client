package dev.leap.frog.Module.Client;

import dev.leap.frog.GUI.Click;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class Blur extends Module {

    public Blur() {
        super("Blur", "Blurs the background of things", Type.CLIENT);
    }

    Setting<Boolean> clickGUIOnly = create("ClickGUIOnly", false);

    private ResourceLocation shaders = new ResourceLocation("minecraft", "shaders/post/blur" + ".json");

    @Override
    public void onUpdate() {
        if(mc.world != null) {
            if(OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
                if(mc.entityRenderer.shaderGroup != null) {
                    mc.entityRenderer.shaderGroup.deleteShaderGroup();
                }

                mc.entityRenderer.loadShader(shaders);

            }
            if(mc.currentScreen == null) {
                mc.entityRenderer.shaderGroup.deleteShaderGroup();
            }

            if(clickGUIOnly.getValue() && !(mc.currentScreen instanceof Click)) {
                mc.entityRenderer.shaderGroup.deleteShaderGroup();
            }
        }
    }

    @Override
    public void onDisable() {
        if(mc.world != null) {
            mc.entityRenderer.shaderGroup.deleteShaderGroup();
        }
    }
}
