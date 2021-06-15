package dev.leap.frog.Event;

import com.google.common.collect.Maps;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.ModuleManager;
import dev.leap.frog.Util.Wrapper;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.io.IOUtils;
import scala.util.parsing.json.JSONArray;
import scala.util.parsing.json.JSONObject;

import java.net.URL;
import java.util.Map;

public class EventProcessor {

    private Minecraft mc = Minecraft.getMinecraft();

    public EventProcessor()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.player == null)
            return;
        ModuleManager.onUpdate();
    }

    @SubscribeEvent
    public void onEntityJoinWorldEvent(EntityJoinWorldEvent entityJoinWorldEvent) {
        LeapFrog.EVENT_BUS.post(entityJoinWorldEvent);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {

        if(event.isCanceled()) return;

        ModuleManager.onRender(event);
    }

    @SubscribeEvent
    public void onRenderBlockOverlay(RenderBlockOverlayEvent event) {
        LeapFrog.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void InputEvent(InputUpdateEvent event) {
        LeapFrog.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event){
        LeapFrog.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onJump(LivingEvent event) {
        LeapFrog.EVENT_BUS.post(event);
    }

}
