package dev.leap.frog.Event;

import dev.leap.frog.LeapFrog;
import me.zero.alpine.fork.event.type.Cancellable;
import me.zero.alpine.fork.listener.Listenable;
import net.minecraft.client.Minecraft;

public class LeapFrogEvent extends Cancellable implements Listenable {

    public static LeapFrogEvent INSTANCE;
    protected Minecraft mc = Minecraft.getMinecraft();
    private Era era = Era.PRE;
    private float partialTicks;

    public LeapFrogEvent() {
        partialTicks = mc.getRenderPartialTicks();
    }

    public LeapFrogEvent(Era era) {
        LeapFrog.EVENT_BUS.post(this);
        partialTicks = mc.getRenderPartialTicks();
        this.era = era;
    }

    public Era getEra() {
        return era;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public boolean isPre() {
        if(era == null) {
            return false;
        }

        return era == Era.PRE;
    }

    public boolean isPost() {
        if(era == null) {
            return false;
        }

        return era == Era.POST;
    }

    public enum Era {
        PRE,
        POST
    }

    static {
        INSTANCE = new LeapFrogEvent();
    }
}
