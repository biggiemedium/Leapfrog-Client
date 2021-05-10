package dev.leap.frog.Mixin;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class MixinMainMenuGUI {

    @Inject(method = "drawScreen", at = @At("TAIL"), cancellable = true)
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {

        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

        fr.drawStringWithShadow(ChatFormatting.GREEN + "LeapFrog Client ", 1, 2, 0xffffff);

    // + ChatFormatting.GOLD + "Boncorde " +
        //                ChatFormatting.GREEN + "and" + ChatFormatting.LIGHT_PURPLE + " PX"

    }


}
