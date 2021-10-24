package dev.leap.frog.Mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * This class is used so that we can log into one of our alts while were testing out the client
 *
 * @see dev.leap.frog.Util.Network.Sessionutil
 */

@Mixin(Minecraft.class)
public interface SessionUtil {

    @Accessor("session")
    public void setSession(Session session);
}
