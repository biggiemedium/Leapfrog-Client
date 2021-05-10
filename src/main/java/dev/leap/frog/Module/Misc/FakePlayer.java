package dev.leap.frog.Module.Misc;

import com.mojang.authlib.GameProfile;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import org.lwjgl.input.Keyboard;

import java.util.UUID;

public class FakePlayer extends Module {

    public FakePlayer() {
        super("Fake Player", "Fake player", Type.MISC);
        setKey(Keyboard.KEY_H);
    }

    private EntityOtherPlayerMP player;

    @Override
    public void onEnable() {
        player = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("70ee432d-0a96-4137-a2c0-37cc9df67f03"), "LeapFrog tester"));
        player.copyLocationAndAnglesFrom(mc.player);
        Chatutil.SendLeapFrogMessage("Fake player has been summoned");
        player.rotationYaw = mc.player.rotationYaw;
        mc.world.addEntityToWorld(-100, player);
    }

    @Override
    public void onDisable() {
        mc.world.removeEntity(player);
    }
}
