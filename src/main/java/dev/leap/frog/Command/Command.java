package dev.leap.frog.Command;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Util.Listeners.Util;
import dev.leap.frog.Util.Render.Chatutil;
import net.minecraft.util.text.TextComponentString;

/*
    I don't know where the original command system is from but credits to Aurora client
 */
public class Command implements Util {

    protected String prefix = ".";

    private String[] args;
    private String syntax;

    public Command(String[] args, String syntax) {
        this.args = args;
        this.syntax = syntax;
    }

    protected void sendMessage(String message) {
        if(mc.player == null || mc.world == null) return;

        Chatutil.sendClientSideMessgage(message);
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String p) {
        prefix = p;
    }

    public String[] getArgs() { return this.args; }

    public String getSyntax() { return this.syntax; }

    public void exec(String command, String[] args) {}

    public Command() {}

    public static Command INSTANCE = new Command();
}
