package dev.leap.frog.Manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CapeManager extends UtilManager { // https://pastebin.com/bcq5U5Bp

    List<UUID> uuids = new ArrayList<>();
    List<UUID> devUUID = new ArrayList<>();

    public CapeManager() {
        userCapes();
        devCapes();
    }

    public void userCapes() {
        try {
            URL capesList = new URL("https://pastebin.com/raw/dg8qiLY8");
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

    public void devCapes() {
        try {
            URL devList = new URL("https://pastebin.com/raw/5MSQnnMW");
            BufferedReader in = new BufferedReader(new InputStreamReader(devList.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                devUUID.add(UUID.fromString(inputLine));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasCape(UUID id) {
        return uuids.contains(id);
    }

    public boolean hasDevCape(UUID id) {
        return devUUID.contains(id);
    }

}
