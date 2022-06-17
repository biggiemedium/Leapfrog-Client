package dev.leap.frog.Command.Commands;

import dev.leap.frog.Command.Command;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraft.util.text.TextComponentString;

public class FriendCommand extends Command {

    public FriendCommand() {
        super(new String[] {"friend", "friends", "f"}, "prefix <add | del | list> <name>");
    }

    @Override
    public void exec(String command, String[] args) {
        if(args[0].equalsIgnoreCase("add")) {
            if(FriendManager.isFriend(args[0])) {
                sendMessage(args[0] + " is already your friend!");
                return;
            }
            if(!FriendManager.isFriend(args[0])){
                sendMessage("Added " + args[0] + " to your friends list!");
                LeapFrog.getFriendManager().addFriend(args[0]);
                LeapFrog.getFileManager().saveFriends();
                return;
            }
        }

        if(args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete")) {
            if(!FriendManager.isFriend(args[0])) {
                sendMessage(args[0] + " is not your friend!");
                return;
            }
            if(FriendManager.isFriend(args[0])) {
                sendMessage("Removed " + args[0] + " as your friend!");
                LeapFrog.getFriendManager().deleteFriend(args[0]);
                LeapFrog.getFileManager().saveFriends();
                return;
            }
        }

        if(args[0].equalsIgnoreCase("list")) {

            if(!FriendManager.getFriend().isEmpty()) {
                sendMessage("Your friends: ");
                FriendManager.getFriend().forEach(friend -> {
                    mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(friend.getName()));
                });
            } else {
                sendMessage("No friends added!");
            }
        }
    }
}
