package dev.leap.frog.Module.Misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;

public class MiddleClickFriends extends Module {

    public MiddleClickFriends() {
        super("MiddleClickFriend", "Lets you add friends", Type.MISC);
    }

    private boolean clicked = false;

    public static ChatFormatting red = ChatFormatting.RED;
    public static ChatFormatting green = ChatFormatting.GREEN;
    public static ChatFormatting bold = ChatFormatting.BOLD;
    public static ChatFormatting reset = ChatFormatting.RESET;


    @Override
    public void onUpdate() {

        if (!(mc.currentScreen == null)) {
            return;
        }

        if (!Mouse.isButtonDown(2)) {
            clicked = false;
            return;
        }

        if (!clicked) {

            clicked = true;

            final RayTraceResult result = mc.objectMouseOver;

            if (result == null || result.typeOfHit != RayTraceResult.Type.ENTITY) {
                return;
            }

            if (!(result.entityHit instanceof EntityPlayer)) return;

            Entity player = result.entityHit;

            if (FriendManager.isFriend(player.getName())) {

                FriendManager.Friend f = FriendManager.friends.stream().filter(friend -> friend.getUsername().equalsIgnoreCase(player.getName())).findFirst().get();
                FriendManager.friends.remove(f);
                Chatutil.ClientSideMessgage("Player " + bold + player.getName() + reset + " is no longer you're" + red + "friend");

            } else {
                FriendManager.Friend f = FriendManager.getFriendObject(player.getName());
                FriendManager.friends.add(f);
                Chatutil.ClientSideMessgage("Player " + bold + player.getName() + reset + " is now you're" + green +  "friend");
            }

        }

    }

}
