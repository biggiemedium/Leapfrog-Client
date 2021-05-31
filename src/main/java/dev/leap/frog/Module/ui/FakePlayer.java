package dev.leap.frog.Module.ui;

import dev.leap.frog.Module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class FakePlayer extends Module {
    public FakePlayer() {
        super("Fake player", "Summons fake player to world", Type.GUI);
    }

    private EntityOtherPlayerMP player;

    @Override
    public void onEnable() {
        player.copyLocationAndAnglesFrom(mc.player);

    }
}
