package dev.leap.frog.Mixin.Render;

import dev.leap.frog.Util.Listeners.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*
DO NOT implment Util class, It seems that when you call on any of the objects everything crashes
 */

@Mixin(GuiChat.class)
public class MixinGuiChat {

    @Shadow
    protected GuiTextField inputField;

    @Inject(method = "keyTyped", at = @At("RETURN"))
    public void keyTyped(char typedChar, int keyCode, CallbackInfo ci) {
        if(!(Minecraft.getMinecraft().currentScreen instanceof GuiChat)) return;

        // forgot I havent wrote a command system in this client yet LMAO
    }

}
