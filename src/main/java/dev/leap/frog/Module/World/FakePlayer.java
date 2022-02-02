package dev.leap.frog.Module.World;

import com.mojang.authlib.GameProfile;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.GameType;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.UUID;

public class FakePlayer extends Module {
    public FakePlayer() {
        super("Fake player", "Summons fake player to world", Type.WORLD);
    }

    private EntityOtherPlayerMP player;

    Setting<Boolean> copyInventory = create("Copy Inventory", false);
    Setting<Boolean> notifyHit = create("Notify hit", true);

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
    public void onUpdate() {
        if(copyInventory.getValue()) {
            player.inventory.copyInventory(mc.player.inventory);
        } else {
            player.inventory.clear();
        }
    }

    @Override
    public void onDisable() {
        mc.world.removeEntity(player);
    }

    @EventHandler
    private Listener<FMLNetworkEvent.ClientDisconnectionFromServerEvent> disconnection = new Listener<>(event -> {
        toggle();
    });
}
