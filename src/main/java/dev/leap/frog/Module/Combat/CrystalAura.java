package dev.leap.frog.Module.Combat;

import dev.leap.frog.Event.LeapFrogEvent;
import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Event.Render.RenderEvent;
import dev.leap.frog.Event.World.EventEntityRemoved;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Module.Movement.NoRotate;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Entity.Entityutil;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Timer;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;

public class CrystalAura extends Module {
    
    public CrystalAura() {
        super("CrystalAura", "Places Crystals To Kill Players", Type.COMBAT);
    }


}
