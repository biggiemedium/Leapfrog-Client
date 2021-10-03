package dev.leap.frog.GUI.ClickGUI.Components;

import dev.leap.frog.GUI.ClickGUI.ModuleButton;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.ui.ClickGUIModule;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Colorutil;
import dev.leap.frog.Util.Render.Renderutil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EnumButton extends Component {

    public EnumButton(Setting<Enum> value, int x, int y, ModuleButton button) {
        super(button, x, y, button.getWidth(), 13);
        this.list = value;
    }

    private Setting<Enum> list;

    @Override
    public void draw(int mouseX, int mouseY) {

        int counter[] = {1};

        Renderutil.drawRect(getX(), getY(), getX() + 2, getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Renderutil.drawRect(getX() + 2, getY(), getX() + 2 + getWidth(), getY() + getHeight() - 1, handleColor(mouseX, mouseY));
        Renderutil.drawRect(getX() + 2, getY() + getHeight() - 1, getX() + 2 + getWidth(), getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Wrapper.getMC().fontRenderer.drawStringWithShadow(list.getName() + " " + list.getValue().name(), getX() + 4, getY() + 2, -1);
        Wrapper.getMC().fontRenderer.drawStringWithShadow(". . .", getX() + this.getWidth() - 12, getY() + 3, Colorutil.Rainbow(counter[0] * 300));
    }

    private int handleColor(int mouseX, int mouseY){
        Color color = getModuleButton().getModule().isHidden() ? Colorutil.subComponentColor() : new Color(50, 50, 50, 200);
        return isHovered(mouseX, mouseY) ? (getModuleButton().getModule().isHidden() ? color.darker().darker().getRGB() : color.brighter().brighter().getRGB()) : color.getRGB();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(isHovered(mouseX, mouseY)) {
            List<Enum> values = Arrays.asList(getValues().getEnumConstants());
            int in = values.indexOf(list.getValue());
            if(button == 0) {
                list.setValue(in+1<values.size()?values.get(in+1) : values.get(0));
            }
            if(ClickGUIModule.INSTANCE.clickSound.getValue() && LeapFrog.getModuleManager().getModule(ClickGUIModule.class).isToggled()) {
                mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            }
        }
    }

    private Class<Enum> getValues() {
        return list.getValue().getDeclaringClass();
    }
}
