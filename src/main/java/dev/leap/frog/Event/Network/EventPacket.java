package dev.leap.frog.Event.Network;

import dev.leap.frog.Event.LeapFrogEvent;
import net.minecraft.network.Packet;

public class EventPacket extends LeapFrogEvent {

    private final Packet packet;

    public EventPacket(Packet packet) {
        super();

        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public static class SendPacket extends  EventPacket{

        public SendPacket(Packet packet) {
            super(packet);
        }

    }

    public static class ReceivePacket extends  EventPacket {
        public ReceivePacket(Packet packet) {
            super(packet);
        }
    }

}
