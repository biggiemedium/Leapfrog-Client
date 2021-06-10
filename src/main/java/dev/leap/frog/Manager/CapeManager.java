package dev.leap.frog.Manager;

import dev.leap.frog.Manager.UtilManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CapeManager extends UtilManager { // https://pastebin.com/bcq5U5Bp

    List<UUID> uuids = new ArrayList<>();

    public CapeManager() {
        loadCapes();
    }

    public void loadCapes() {
        try {
            URL capesList = new URL("https://pastebin.com/raw/JGTeQR2q");
            BufferedReader in = new BufferedReader(new InputStreamReader(capesList.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                uuids.add(UUID.fromString(inputLine));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasCape(UUID id) {
        return uuids.contains(id);
    }

}
