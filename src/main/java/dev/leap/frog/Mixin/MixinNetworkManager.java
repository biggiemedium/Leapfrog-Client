package dev.leap.frog.Mixin;

import dev.leap.frog.Event.LeapFrogEvent;
import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.LeapFrog;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void receive(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callback) {
        EventPacket eventPacket = new EventPacket.ReceivePacket(packet);

        LeapFrog.EVENT_BUS.post(eventPacket);

        if (eventPacket.isCancelled()) {
            callback.cancel();
        }
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void send(Packet<?> packet, CallbackInfo callback) {
        EventPacket eventPacket = new EventPacket.SendPacket(packet);

        LeapFrog.EVENT_BUS.post(eventPacket);

        if (eventPacket.isCancelled()) {
            callback.cancel();
        }
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("RETURN"), cancellable = true)
    private void sendPost(Packet<?> packet, CallbackInfo callback) {
        EventPacket.SendPacketPost eventPacket = new EventPacket.SendPacketPost(packet);
        LeapFrog.EVENT_BUS.post(eventPacket);

        if (eventPacket.isCancelled()) {
            callback.cancel();
        }
    }

    @Inject(method = "exceptionCaught", at = @At("HEAD"), cancellable = true)
    private void exception(ChannelHandlerContext exc, Throwable exc_, CallbackInfo callback) {
        if (exc_ instanceof Exception) {
            callback.cancel();
        }
    }

}
