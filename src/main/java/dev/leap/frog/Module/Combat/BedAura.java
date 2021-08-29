package dev.leap.frog.Module.Combat;

import dev.leap.frog.Manager.FriendManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BedAura extends Module {

    public BedAura() {
        super("BedAura", "Testing to see if it works", Type.COMBAT);
    }

    Setting<Boolean> autoSwitch = create("Auto switch", true);
    Setting<Integer> range = create("Range", 5, 0, 6);
    Setting<Integer> minimumDamage = create("Minimum", 0, 4, 20);
    Setting<Integer> maxSelfDamage = create("Max self Damage", 0, 4, 20);
    Setting<Boolean> predict = create("Predict", true);
    Setting<Integer> predictionTicks = create("Prediction Ticks", 0, 0, 20);

    public boolean shouldPlace = false;
    public BlockPos blockPos = null;
    public EnumFacing direction = null;
    EntityPlayer renderEnt = null;

    @Override
    public void onUpdate() {
        if(mc.player == null) return;

        if(!(mc.world.getWorldType().getId() == 0)) {
            Chatutil.sendClientSideMessgage("Can't use bedaura in overworld");
            toggle();
            return;
        }

        List<EntityPlayer> entityPlayers = new ArrayList<>(mc.world.playerEntities.stream().filter(e -> !FriendManager.isFriend(e.getName()) && !(e.getHealth() > 0) && !(e == mc.player) && !e.isDead).collect(Collectors.toList()));


    }
}
