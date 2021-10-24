package dev.leap.frog.Module.Misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Entity.Friendutil;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class MiddleClickFriends extends Module {

    public MiddleClickFriends() {
        super("MiddleClickFriend", "Lets you add friends", Type.MISC);
    }

    private boolean clicked = false;

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
            RayTraceResult result = mc.objectMouseOver;
            if (result == null || result.typeOfHit != RayTraceResult.Type.ENTITY) {
                return;
            }

            if (!(result.entityHit instanceof EntityPlayer)) return;

            Entity player = result.entityHit;

            if (FriendManager.isFriend(player.getName())) {

                Friendutil f = FriendManager.friend.stream().filter(friend -> friend.getName().equalsIgnoreCase(player.getName())).findFirst().get();
                LeapFrog.getFriendManager().removeFriend(f);
                Chatutil.sendClientSideMessgage("Player " + player.getName() + " is no longer your" + ChatFormatting.RED + " " + "friend");
                try {
                    LeapFrog.getFileManager().saveFriends();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                LeapFrog.getFriendManager().addFriend(mc.objectMouseOver.entityHit.getName());
                Chatutil.sendClientSideMessgage("Player " + player.getName() + " is now your" + ChatFormatting.GREEN + " " +  "friend");
                try {
                    LeapFrog.getFileManager().saveFriends();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
