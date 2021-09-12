package dev.leap.frog.Event.World;

import dev.leap.frog.Event.LeapFrogEvent;
import net.minecraft.util.DamageSource;

public class EventAttackEnderCrystal extends LeapFrogEvent {

    private DamageSource source;

    public EventAttackEnderCrystal(DamageSource source) {
        this.source = source;
    }

    public DamageSource getSource() {
        return source;
    }

    public void setSource(DamageSource source) {
        this.source = source;
    }
}
