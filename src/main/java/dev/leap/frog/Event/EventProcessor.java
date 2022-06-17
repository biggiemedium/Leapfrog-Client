package dev.leap.frog.Event;


import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.Command.Command;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Manager.ModuleManager;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventProcessor {

    private final Minecraft mc = Minecraft.getMinecraft();
    private String lastChat = "";

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
    public void onPlayerLeaveEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        LeapFrog.EVENT_BUS.post(event);
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

        this.lastChat = event.getMessage().getUnformattedText();
    }

    @SubscribeEvent
    public void onChatSend(ClientChatEvent event) {
        LeapFrog.EVENT_BUS.post(event);

        String prefix = Command.INSTANCE.getPrefix();
        if(prefix == null) {
            Chatutil.sendClientSideMessgage("PREFIX IS NULL");
            return;
        }
        if(event.getMessage().startsWith(prefix)) {
            event.setCanceled(true);

            try {
                mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                LeapFrog.getCommandManager().callCommand(event.getMessage().substring(1));
            }
            catch (Exception e) {
                e.printStackTrace();
                mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(ChatFormatting.DARK_RED + "Error: " + e.getMessage()));
            }
        }
    }

    @SubscribeEvent
    public void onJump(LivingEvent event) {
        LeapFrog.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onLivingEntityUseEvent(LivingEntityUseItemEvent event) {
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

    @SubscribeEvent
    public void hurtEvent(LivingHurtEvent event) {
        LeapFrog.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void guiScreenEvent(GuiScreenEvent event) {
        LeapFrog.EVENT_BUS.post(event);
    }

    public String getLastChat() {
        return this.lastChat;
    }

    public static <T> T postEvent(T event) {
        LeapFrog.EVENT_BUS.post(event);
        return event;
    }
}
