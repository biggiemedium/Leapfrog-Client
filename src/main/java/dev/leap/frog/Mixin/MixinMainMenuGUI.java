package dev.leap.frog.Mixin;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class MixinMainMenuGUI {

    @Inject(method = "drawScreen", at = @At("TAIL"), cancellable = true)
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {

        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        ScaledResolution sc = new ScaledResolution(Wrapper.getMC());

        fr.drawStringWithShadow(ChatFormatting.GREEN + "LeapFrog Client ", sc.getScaledWidth() - Wrapper.getMC().fontRenderer.getStringWidth("LeapFrog Client") - 5, 2, 0xffffff);

    }


}
