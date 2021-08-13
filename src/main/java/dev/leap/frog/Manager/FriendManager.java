package dev.leap.frog.Manager;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.util.UUIDTypeAdapter;
import dev.leap.frog.Util.Entity.Friendutil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class FriendManager {

    public static ArrayList<Friendutil> friend;

    public FriendManager() {
        friend = new ArrayList<>();
    }

    public static boolean isFriend(String name) {
        return friend.stream().anyMatch(e -> e.getName().equalsIgnoreCase(name));
    }

    public Friendutil getFriendbyName(String name) {
        Friendutil friend = null;
        for(Friendutil f : getFriend()) {
            if(f.getName().equalsIgnoreCase(name)) {
                friend = f;
            }
        }
        return friend;
    }

    public void addFriend(String name) {
        friend.add(new Friendutil(name));
    }

    public void deleteFriend(String name) {
        friend.remove(getFriendbyName(name));
    }

    public void removeFriend(Friendutil friend) {
        getFriend().remove(friend);
    }

    public static ArrayList<Friendutil> getFriend() {
        return friend;
    }

}
