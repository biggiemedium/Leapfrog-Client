package dev.leap.frog.Event;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

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
    public void onEntityJoinWorldEvent(EntityJoinWorldEvent entityJoinWorldEvent)
    {
        LeapFrog.EVENT_BUS.post(entityJoinWorldEvent);
    }

}
