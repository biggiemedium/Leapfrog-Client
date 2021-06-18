package dev.leap.frog.Module.Render;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.Event.Render.EventRenderNameTag;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Render.Drawutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.common.Mod;
import org.apache.http.util.EntityUtils;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

public class NameTags extends Module {

    public NameTags() {
        super("NameTags", "NameTags", Type.RENDER);
    }

    Settings ping = create("Ping", "Ping", true);
    Settings gamemode = create("Gamemode", "Gamemode", true);
    Settings items = create("Items", "Items", true);
    Settings armor = create("Armor", "Armor", true);
    Settings self = create("Self", "Self", false);

    Settings scale = create("Scale", "Scale", 5, 1, 10);
    Settings distance = create("Distance", "Distance", 75, 1, 255); // chunk range shit idk

    Settings r = create("red", "red", 100, 0, 255);
    Settings g = create("green", "green", 100, 0, 255);
    Settings b = create("blue", "blue", 100, 0, 255);
    Settings a = create("alpha", "alpha", 255, 0, 255);

    private EntityPlayer player;

    @EventHandler
    private Listener<EventRenderNameTag> tagListener = new Listener<>(event -> {
       event.cancel();
    });

    @Override
    public void onRender(RenderEvent event) {

        for(EntityPlayer player : mc.world.playerEntities) {
            if(!(player == null) && mc.world != null) {
                if(!self.getValue(true) && !player.getName().equals(mc.player.getName())) {
                    if(!(player.getName().equals(mc.player.getName()))) {
                    if(!player.getName().equals(mc.player.getName()) && player.isEntityAlive() && mc.player.getDistance(player) < distance.getValue(1) || player.isInvisible()) {
                        final double x = this.interpolate(player.lastTickPosX, player.posX, event.getPartialTicks()) - mc.getRenderManager().renderPosX;
                        final double y = this.interpolate(player.lastTickPosY, player.posY, event.getPartialTicks()) - mc.getRenderManager().renderPosY;
                        final double z = this.interpolate(player.lastTickPosZ, player.posZ, event.getPartialTicks()) - mc.getRenderManager().renderPosZ;
                        // insert rendering method here
                        render(player, x, y, z, event.getPartialTicks());
                            }
                        }
                    }
                }
                if(self.getValue(true)) {
                    if(mc.player != null && !Objects.requireNonNull(player).isInvisible() || !Objects.requireNonNull(player).getName().equals(mc.player.getName()) && player.isEntityAlive() && mc.player.getDistance(player) < distance.getValue(1) || player.isInvisible()) {
                        final double x = this.interpolate(player.lastTickPosX, player.posX, event.getPartialTicks()) - mc.getRenderManager().renderPosX;
                        final double y = this.interpolate(player.lastTickPosY, player.posY, event.getPartialTicks()) - mc.getRenderManager().renderPosY;
                        final double z = this.interpolate(player.lastTickPosZ, player.posZ, event.getPartialTicks()) - mc.getRenderManager().renderPosZ;
                        // insert rendering method here
                        render(player, x, y, z, event.getPartialTicks());

                    }
                }
            }
    }

    private void render(EntityPlayer player, final double x, final double y, final double z, final float delta) {
        double tempY = y;
        tempY += (player.isSneaking() ? 0.5 : 0.7);
        final Entity camera = mc.getRenderViewEntity();
        assert camera != null;
        double originalPositionX = camera.posX;
        double originalPositionY = camera.posY;
        double originalPositionZ = camera.posZ;
        camera.posX = this.interpolate(camera.prevPosX, camera.posX, delta);
        camera.posY = this.interpolate(camera.prevPosY, camera.posY, delta);
        camera.posZ = this.interpolate(camera.prevPosZ, camera.posZ, delta);
        String displayTag = getNameTag(player);


        double distance = camera.getDistance(x + mc.getRenderManager().viewerPosX, y + mc.getRenderManager().viewerPosY, z + mc.getRenderManager().viewerPosZ);
        int width = mc.fontRenderer.getStringWidth(displayTag) / 2;
        double scale = (0.0018 + this.scale.getValue(1) * (distance * 0.3)) / 1000.0;
        if (distance <= 8.0) {
            scale = 0.0245;
        }

        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)tempY + 1.4f, (float)z);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(mc.getRenderManager().playerViewX, (mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.enableBlend();
        boolean is_friend = FriendManager.isFriend(player.getName());
        int red = r.getValue(1);
        int green = g.getValue(1);
        int blue = b.getValue(1);
        if (is_friend) {
            red = 157;
            green = 99;
            blue = 255;
        }
        Drawutil.drawRect((float)(-width - 2)-1, (float)(-(mc.fontRenderer.FONT_HEIGHT + 1))-1, width + 3f, 2.5f, red, green, blue, (float) a.getValue(1));
        Drawutil.drawRect((float)(-width - 2), (float)(-(mc.fontRenderer.FONT_HEIGHT + 1)), width + 2.0f, 1.5f, 1426063360);
        GlStateManager.disableBlend();
        final ItemStack renderMainHand = player.getHeldItemMainhand().copy();
        if (renderMainHand.hasEffect() && (renderMainHand.getItem() instanceof ItemTool || renderMainHand.getItem() instanceof ItemArmor)) {
            renderMainHand.stackSize = 1;
        }
        if (!renderMainHand.isEmpty && renderMainHand.getItem() != Items.AIR) {
            final String stackName = renderMainHand.getDisplayName();
            final int stackNameWidth = mc.fontRenderer.getStringWidth(stackName) / 2;
            GL11.glPushMatrix();
            GL11.glScalef(0.75f, 0.75f, 0.0f);
            GL11.glScalef(1.5f, 1.5f, 1.0f);
            GL11.glPopMatrix();
        }
            mc.fontRenderer.drawStringWithShadow(displayTag, (float) (-width), (float) (-(mc.fontRenderer.FONT_HEIGHT - 1)), getDisplayColour(player));
            camera.posX = originalPositionX;
            camera.posY = originalPositionY;
            camera.posZ = originalPositionZ;
            GlStateManager.enableDepth();
            GlStateManager.disableBlend();
            GlStateManager.disablePolygonOffset();
            GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
            GlStateManager.popMatrix();
        }



    private String getNameTag(EntityPlayer player) {
        String name = player.getName();

        if(name.contains(mc.player.getName())) { // lunar skid!!!11111!!!!?????????////????
            name = "You";
        }

        String playerPing = "";

        if(ping.getValue(true)) {
            try {
                int ping = Objects.requireNonNull(Objects.requireNonNull(mc.getConnection()).getPlayerInfo(player.getName())).getResponseTime();
                playerPing = "ms " + ping;
            } catch (Exception ignored) {
                LeapFrog.log.info("error getting ping in tags class");
            }
        }
        String playerGamemode = "";

        if(gamemode.getValue(true)) {

            if(player.isCreative()) {
                playerGamemode = "[C]";
            } else if(player.isSpectator()) {
                playerGamemode = "[I]";
            } else {
                playerGamemode = "[S]";
            }

        }


        int playerHealth = (int) Math.floor(player.getHealth() + player.getAbsorptionAmount());
        
        return playerGamemode + " " +name + " " + playerHealth + " " + playerPing;
    }

    private double interpolate(final double previous, final double current, final float delta) {
        return previous + (current - previous) * delta;
    }

        private int getDisplayColour(EntityPlayer player) {
            int colour = -5592406;
            if (FriendManager.isFriend(player.getName())) {
                return -11157267;
            }
            return colour;
        }

}
