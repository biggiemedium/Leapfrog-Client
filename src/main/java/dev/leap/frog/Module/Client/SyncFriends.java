package dev.leap.frog.Module.Client;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Friendutil;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SyncFriends extends Module {

    public SyncFriends() {
        super("Sync Friends", "Adds friends from other clients to your current friend list", Type.CLIENT);
    }

    Setting<Boolean> xulu = create("Xulu", true);
    Setting<Boolean> salhack = create("Salhack", true);
    Setting<Boolean> kami = create("Kami", true);
    Setting<Boolean> phobos = create("Phobos", true);
    Setting<Boolean> wp2 = create("wp2", true);

    private ArrayList<String> clients;
    private ArrayList<String> otherClientFriends;

    private File salhackDirectory = new File(mc.mcDataDir.getAbsolutePath(), "SalHack");
    private File xuluDirectory = new File(mc.mcDataDir.getAbsolutePath(), "Xulu");

    @Override
    public void onEnable() {
        this.clients = new ArrayList<>();
        this.otherClientFriends = new ArrayList<>();

        update();
        addFriends();

        for(int i = 0; i < clients.size(); i++) {
            Chatutil.sendClientSideMessgage("Clients in use: " + this.clients);
        }

        for(int i = 0; i < otherClientFriends.size(); i++) {
            Chatutil.sendClientSideMessgage("Other people: " + this.otherClientFriends);
        }
    }

    @Override
    public void onUpdate() {
        if(mc.player == null || mc.world == null) return;

        update();
    }

    @EventHandler
    private Listener<InputEvent.MouseInputEvent> eventListener = new Listener<>(event -> {
        if(Mouse.isButtonDown(2)) {

        }
    });

    private void update() {
        if(salhackDirectory.exists()) {
            this.clients.add("SalHack");
        }
        if(xuluDirectory.exists()) {
            this.clients.add("Xulu");
        }

    }

    private void addFriends() {

        if(salhackDirectory.exists()) {
            File friends = new File("SalHack/FriendList.json");
            LinkedTreeMap<String, Friendutil> SalhackFriend = new LinkedTreeMap<>();
            if (!friends.exists())
                return;

            try {

                Gson gson = new Gson();
                Reader reader = Files.newBufferedReader(Paths.get("SalHack/" + "FriendList" + ".json"));
                SalhackFriend = gson.fromJson(reader, new TypeToken<LinkedTreeMap<String, Friendutil>>() {}.getType());

                reader.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if(xuluDirectory.exists()) {
            File friends = new File(xuluDirectory.getAbsolutePath(), "Friends.txt");

            try {
                BufferedReader reader = new BufferedReader(new FileReader(friends));

                String line;
                while ((line = reader.readLine()) != null) {
                    for(Friendutil f : FriendManager.getFriend()) {
                        if(!f.getName().equals(line)) {
                            this.otherClientFriends.add(line);
                        }
                    }
                }
                reader.close();
            } catch (Exception ignored) {}
        }

    }
}
