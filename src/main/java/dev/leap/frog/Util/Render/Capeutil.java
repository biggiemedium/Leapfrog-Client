package dev.leap.frog.Util.Render;

import dev.leap.frog.Manager.UtilManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Capeutil extends UtilManager {

    final static ArrayList<String> UUIDList = getUuids();

    public static ArrayList<String> getUuids() {
        try {
            URL url = new URL(""); // inset names of who gets cape here (in pastebin) by id
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            final ArrayList<String> uuid_list = new ArrayList<>();

            String s;

            while ((s = reader.readLine()) != null) {
                uuid_list.add(s);
            }

            return uuid_list;
        } catch (Exception ignored){
            return null;
        }
    }

    public static boolean isUuidValid(UUID uuid) {
        for (String u : Objects.requireNonNull(UUIDList)) {
            if (u.equals(uuid.toString())) {
                return true;
            }
        }
        return false;
    }

}
