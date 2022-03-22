package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.EventRenderNameTag;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Entity.Friendutil;
import dev.leap.frog.Util.Math.Pairutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/*
 *  Code taken from memezzz, Elementars, Ace, Ionar
 */

public class NameTags extends Module {

    public NameTags() {
        super("NameTags", "Shows information about the player above their heads", Type.RENDER);
    }

    Setting<Float> scale = create("Scale", 0.5f, 0.1f, 0.9f);

    Setting<Integer> red = create("Red", 255, 0, 255);
    Setting<Integer> green = create("Green", 255, 0, 255);
    Setting<Integer> blue = create("Blue", 255, 0, 255);

    Setting<Double> range = create("Range", 75, 1, 255);

    Setting<Boolean> gameMode = create("GameMode", true);
    Setting<Boolean> ping = create("Ping", true);
    Setting<Boolean> gameID = create("GameID", false);
    Setting<Boolean> armour = create("Armour", true);
    Setting<Boolean> items = create("Items", true);

    private Map<EntityPlayer, Color> playerColor;

    @Override
    public void onEnable() {
        this.playerColor = new HashMap<>();
    }

    @SuppressWarnings("unused")
    @EventHandler
    private Listener<EventRenderNameTag> tagListener = new Listener<>(event -> {
        event.cancel();
    });

    @Override
    public void onUpdate() {
        Color enemyColor = new Color(red.getValue(), green.getValue(), blue.getValue());
        mc.world.playerEntities.forEach(player -> {
            if(FriendManager.isFriend(player.getName())) {
                this.playerColor.put(player, new Color(0x00C3EE));
            } else {
                this.playerColor.put(player, enemyColor);
            }
        });
    }

    private String getNameTag() {
        return "";
    }
}
