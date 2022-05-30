package dev.leap.frog.GUI.ClickGUI.Components;

import dev.leap.frog.GUI.ClickGUI.ModuleButton;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.ui.ClickGUIModule;
import dev.leap.frog.Util.Render.Colorutil;
import dev.leap.frog.Util.Render.Renderutil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

import java.awt.*;
import java.io.IOException;

public class HiddenButton extends Component {

    public HiddenButton(int x, int y, ModuleButton button) {
        super(button, x, y, button.getWidth(), 13);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Renderutil.drawRect(getX(), getY(), getX(), getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Renderutil.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight() - 1, handleColor(mouseX, mouseY));
        Renderutil.drawRect(getX(), getY() + getHeight() - 1, getX() + getWidth(), getY() + getHeight(), new Color(10, 10, 10, 200).getRGB()); // seperator Line
        Wrapper.getMC().fontRenderer.drawStringWithShadow(getModuleButton().getModule().isHidden() ? "UnHide" : "Hide", getX() + 4, getY() + 2, -1);
    }

    private int handleColor(int mouseX, int mouseY){
        Color color = getModuleButton().getModule().isHidden() ? Colorutil.subComponentColor() : new Color(50, 50, 50, 200);
        return isHovered(mouseX, mouseY) ? (getModuleButton().getModule().isHidden() ? color.darker().darker().getRGB() : color.brighter().brighter().getRGB()) : color.getRGB();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(isHovered(mouseX, mouseY)) {
            if(button == 0) {
                getModuleButton().getModule().setHidden(!getModuleButton().getModule().isHidden());
            }

            if(ClickGUIModule.INSTANCE.clickSound.getValue() && LeapFrog.getModuleManager().getModule(ClickGUIModule.class).isToggled()) {
                mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            }

        }
    }
}
