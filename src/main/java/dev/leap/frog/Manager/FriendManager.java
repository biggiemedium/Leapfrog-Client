package dev.leap.frog.Manager;

import dev.leap.frog.Util.Entity.Friendutil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class FriendManager { // EDIT AFTER FILE SAVE IS MADE!!!

    public static List<Friendutil> friends;

    public FriendManager() {
        friends = new ArrayList<>();
    }

    public static List<String> getFriendName() {
        ArrayList<String> friendName = new ArrayList<>();
        friends.forEach(friend -> {
            friendName.add(friend.getFriend());
        });

        return friendName;
    }

    public static boolean isFriend(String alias) {
       boolean f = false;

       for(Friendutil pals : friends) {
           if(pals.getFriend().equalsIgnoreCase(alias)) {
               f = true;
               break;
           }
       }

       return f;
    }

    public static void addFriend(String alias) {

        friends.add(new Friendutil(alias));

        // add if save method
    }

    public static void removeFriend(String alias) {
        friends.remove(new Friendutil(alias));
    }

    public static void toggleFriends() {
        friends.clear();
    }

}
