package dev.leap.frog.Util.Network;

import dev.leap.frog.Manager.UtilManager;
import net.minecraft.entity.Entity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class Packetutil extends UtilManager {

    public static boolean canConnectToInternet() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }


    public static int getPing(Entity entity) {
        if(entity == null) {
            return -1;
        }
        try {
            return Objects.requireNonNull(mc.getConnection().getPlayerInfo(entity.getName()).getResponseTime());
        } catch (Exception ignored) {}
        return -1;
    }
}
