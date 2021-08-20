package dev.leap.frog.Module.World;

import com.mojang.authlib.GameProfile;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.GameType;

import java.util.UUID;

public class FakePlayer extends Module {
    public FakePlayer() {
        super("Fake player", "Summons fake player to world", Type.WORLD);
    }

    private EntityOtherPlayerMP player;

    Setting<Boolean> copyInventory = create("Copy Inventory", false);
    Setting<Boolean> stacked = create("Stacked", false);

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
        if(copyInventory.getValue() && !stacked.getValue()) {
            player.inventory.copyInventory(mc.player.inventory);
        } else {
            player.inventory.clear();
        }

        if(!copyInventory.getValue() && stacked.getValue()) {

            ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
            ItemStack chest = new ItemStack(Items.DIAMOND_CHESTPLATE);
            ItemStack pants = new ItemStack(Items.DIAMOND_LEGGINGS);
            ItemStack boots = new ItemStack(Items.DIAMOND_BOOTS);

            helmet.addEnchantment(Enchantments.PROTECTION, 4);
            helmet.addEnchantment(Enchantments.UNBREAKING, 4);
            player.inventory.armorInventory.add(helmet);

            chest.addEnchantment(Enchantments.PROTECTION, 4);
            chest.addEnchantment(Enchantments.UNBREAKING, 4);
            player.inventory.armorInventory.add(chest);

            pants.addEnchantment(Enchantments.BLAST_PROTECTION, 4);
            pants.addEnchantment(Enchantments.UNBREAKING, 4);
            player.inventory.armorInventory.add(pants);

            boots.addEnchantment(Enchantments.PROTECTION, 4);
            boots.addEnchantment(Enchantments.UNBREAKING, 4);
            player.inventory.armorInventory.add(boots);

        } else {
            player.inventory.clear();
        }

    }

    @Override
    public void onDisable() {
        mc.world.removeEntity(player);
    }
}
