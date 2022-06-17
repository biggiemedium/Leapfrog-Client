package dev.leap.frog.Module.Misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.text.TextComponentString;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerRange extends Module {

    public PlayerRange() {
        super("Player Range", "Tells you who is in your visual range", Type.MISC);
    }

    Setting<Boolean> notification = create("Notification", true);
    Setting<Boolean> sound = create("Sound Notification", true);
    Setting<Boolean> friends = create("Friends", true);
    Setting<Boolean> leaving = create("Leaving", false);
    Setting<Boolean> annoy = create("Annoy player", false);

    private ArrayList<String> player;

    @Override
    public void onEnable() {
        this.player = new ArrayList<>();
        this.player.clear();
    }

    @Override
    public void onUpdate() {
        if(mc.player == null || mc.world == null || mc.player.ticksExisted < 20) return;

        ArrayList<EntityPlayer> removeList = new ArrayList<>();
        for(EntityPlayer validPlayers : mc.world.playerEntities) {
            if(validPlayers == mc.player) continue;
            if(Entityutil.isFakeLocalPlayer(validPlayers)) continue;
            if(!friends.getValue() && FriendManager.isFriend(validPlayers.getName())) continue;
            if(!this.player.contains(validPlayers.getName())) {
                player.add(validPlayers.getName());
                removeList.add(validPlayers);
                sendNotification(validPlayers);
                if (annoy.getValue()) {
                    messagePlayer(validPlayers);
                }
            }
        }

        for(EntityPlayer p : removeList) {
            if(!mc.world.playerEntities.contains(p)) {
                player.remove(p.getName());
                if(leaving.getValue()) {
                    mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString("Player " + p.getName() + " Has left your visual range"));
                }
            }
        }
    }

    private void sendNotification(Entity target) {
        if(notification.getValue()) {
            if(FriendManager.isFriend(target.getName())) {
                mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString("Player " + ChatFormatting.BLUE + target.getName() + ChatFormatting.RESET + " Has entered your visual range"));
            } else {
                mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString("Player " + target.getName() + " Has entered your visual range"));
            }

        }

        if(sound.getValue()) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f));
        }
    }

    private void messagePlayer(EntityPlayer target) {
        if(!FriendManager.isFriend(target.getName()))
        mc.player.connection.sendPacket(new CPacketChatMessage("/w " + target.getName() + " " + "Hello uwu"));
    }
}
