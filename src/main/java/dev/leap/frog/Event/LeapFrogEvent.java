package dev.leap.frog.Event;

import dev.leap.frog.LeapFrog;
import me.zero.alpine.fork.event.type.Cancellable;
import me.zero.alpine.fork.listener.Listenable;
import net.minecraft.client.Minecraft;

public class LeapFrogEvent extends Cancellable implements Listenable {

    public static LeapFrogEvent INSTANCE;
    protected Minecraft mc = Minecraft.getMinecraft();
    private Era era = Era.PRE;
    private final float partialTicks;

    public LeapFrogEvent() {
        partialTicks = mc.getRenderPartialTicks();
    }

    public LeapFrogEvent(Era p_Era) {
        LeapFrog.EVENT_BUS.post(this);
        partialTicks = mc.getRenderPartialTicks();
        era = p_Era;
    }

    public Era getEra() {
        return era;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public enum Era {
        PRE,
        POST
    }

}
