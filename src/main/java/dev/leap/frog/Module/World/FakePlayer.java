package dev.leap.frog.Module.World;

import com.mojang.authlib.GameProfile;
import dev.leap.frog.Module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

public class FakePlayer extends Module {
    public FakePlayer() {
        super("Fake player", "Summons fake player to world", Type.WORLD);
    }

    private EntityOtherPlayerMP player;

    @Override
    public void onEnable() {

        if(mc.world == null) {
            toggle();
            return;
        }

        player = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5"), "Leapfrog tester"));
        player.copyLocationAndAnglesFrom(mc.player);
        player.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(-100, player);
    }

    @Override
    public void onDisable() {
        mc.world.removeEntity(player);
    }
}
