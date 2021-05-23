package dev.leap.frog.Mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface SessionUtil {

    @Accessor("session")
    public void setSession(Session session);
}
