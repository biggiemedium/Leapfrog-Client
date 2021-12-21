package dev.leap.frog.Event;


import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventProcessor {

    private final Minecraft mc = Minecraft.getMinecraft();

    public EventProcessor() {
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
    public void onMouseClick(InputEvent.MouseInputEvent event) {
        LeapFrog.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event){
        LeapFrog.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onChatSend(ClientChatEvent event) {
        LeapFrog.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onJump(LivingEvent event) {
        LeapFrog.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onJoin(EntityJoinWorldEvent event) {
        LeapFrog.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        LeapFrog.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {
        LeapFrog.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void setSky(EntityViewRenderEvent.FogColors event) {
        LeapFrog.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void pushEvent(PlayerSPPushOutOfBlocksEvent event) {
        LeapFrog.EVENT_BUS.post(event);
    }

}
