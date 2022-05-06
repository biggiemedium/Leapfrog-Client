package dev.leap.frog.Module.World;

import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Network.Packetutil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class MobOwner extends Module {

    public MobOwner() {
        super("MobOwner", "Shows you who owns any tamed entity", Type.WORLD);
    }

    private Map<Entity, UUID> mobMap;

    @Override
    public void onEnable() {
        mobMap = new HashMap<>();
    }

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck()) return;
        if(mobMap == null) {
            mobMap = new HashMap<>();
        }

        for(Entity e : mc.world.loadedEntityList) {
            if(e instanceof AbstractHorse) {
                AbstractHorse horse = (AbstractHorse) e;

                if(this.mobMap.containsKey(horse)) {
                    continue;
                }

                this.mobMap.put(horse, horse.getOwnerUniqueId());

                this.mobMap.forEach((mob, name) -> {
                    if(((AbstractHorse) mob).getOwnerUniqueId() == null) {
                        mob.setCustomNameTag("Owner: None");
                    } else {
                        String tag = name.toString().replace("-", "");
                        mob.setCustomNameTag("Owner: ");
                    }
                });
            }
        }
    }

    @Override
    public void onDisable() {
    }

    private String getMobOwnerName(UUID uuid) {
        try {
            String site = "https://api.mojang.com/user/profiles/" + uuid + "/names";
            URL url = new URL(site);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String res = convertStreamToString(in);
            in.close();
            conn.disconnect();
            return res;
        } catch (Exception ignored) {
            return null;
        }
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        String r = s.hasNext() ? s.next() : "/";
        return r;
    }
}
