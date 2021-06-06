package dev.leap.frog.Module.Chat;

import dev.leap.frog.Module.Module;

public class ToggleMessage extends Module {

    public ToggleMessage() {
        super("ToggleMessage", "ToggleMessage", Type.CHAT);
        setToggled(true);
    }

}
