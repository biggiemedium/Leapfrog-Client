package dev.leap.frog.Module.Render;

import dev.leap.frog.Event.Render.EventRenderNameTag;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
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

    private ICamera camera = new Frustum();

    private ConcurrentHashMap<EntityPlayer, Color> colorMap = new ConcurrentHashMap<>();

    @SuppressWarnings("unused")
    @EventHandler
    private Listener<EventRenderNameTag> tagListener = new Listener<>(event -> {
        event.cancel();
    });

    @Override
    public void onUpdate() {
        for(EntityPlayer player : mc.world.playerEntities) {
            if(FriendManager.isFriend(player.getName())) {
                colorMap.put(player, new Color(0x00C3EE));
            } else {
                colorMap.put(player, new Color(red.getValue(), green.getValue(), blue.getValue()));
            }
        }
    }

    @Override
    public void onRender(RenderEvent event) {

        if(UtilManager.nullCheck()) return;

        double x,y,z;
        x = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * event.getPartialTicks();
        y = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * event.getPartialTicks();
        z = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * event.getPartialTicks();
        this.camera.setPosition(x, y, z);
        mc.world.playerEntities.stream()
                .filter(entity -> mc.player.getDistance(entity.posX, entity.posY, entity.posZ) <= range.getValue())
                .filter(entity -> entity != null)
                .filter(Entityutil::isLiving)
                .filter(entity -> !Entityutil.isFakeLocalPlayer(entity))
                .filter(entity -> entity instanceof EntityPlayer)
                .filter(entity -> camera.isBoundingBoxInFrustum(entity.getEntityBoundingBox()))
                .filter(entity -> entity != mc.player).forEach(player -> {
                    render(event, player);
        });
    }

    private void render(RenderEvent event, EntityPlayer player) {
        GlStateManager.pushMatrix();
    }

    private void renderItem(ItemStack stack, int x, int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().zLevel = -100.0F;
        GlStateManager.scale(1, 1, 0.01f);
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, (y / 2) - 12);
        mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, x, (y / 2) - 12);
        mc.getRenderItem().zLevel = 0.0F;
        GlStateManager.scale(1, 1, 1);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.disableDepth();
        renderEnchantText(stack, x, y - 18);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GL11.glPopMatrix();
    }

    private void renderEnchantText(ItemStack stack, int x, int y) {
        int encY = y - 24;
        int yCount = encY - -5;
        if (stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemTool) {
            float green = ((float) stack.getMaxDamage() - (float) stack.getItemDamage()) / (float) stack.getMaxDamage();
            float red = 1 - green;
            int dmg = 100 - (int) (red * 100);
            mc.fontRenderer.drawStringWithShadow(dmg + "%", x * 2 + 8, y + 26, new Color((int) (red * 255), (int) (green * 255), 0).getRGB());
        }

        NBTTagList enchants = stack.getEnchantmentTagList();
        for (int index = 0; index < enchants.tagCount(); ++index) {
            short id = enchants.getCompoundTagAt(index).getShort("id");
            short level = enchants.getCompoundTagAt(index).getShort("lvl");
            Enchantment enc = Enchantment.getEnchantmentByID(id);
            if (enc != null) {
                String encName = enc.isCurse()
                        ? TextFormatting.WHITE
                        + enc.getTranslatedName(level).substring(11).substring(0, 1).toLowerCase()
                        : enc.getTranslatedName(level).substring(0, 1).toLowerCase();
                encName = encName + level;
                GL11.glPushMatrix();
                GL11.glScalef(0.9f, 0.9f, 0);
                mc.fontRenderer.drawStringWithShadow(encName, x * 2 + 13, yCount, -1);
                GL11.glScalef(2f, 2f, 2);
                GL11.glPopMatrix();
                encY += 8;
                yCount -= 10;
            }
        }
    }

}
