package dev.leap.frog.GUI.ClickGUI.Components;

import dev.leap.frog.GUI.ClickGUI.ModuleButton;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Colorutil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.gui.Gui;

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

        Gui.drawRect(getX(), getY(), getX() + 2, getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Gui.drawRect(getX() + 2, getY(), getX() + 2 + getWidth(), getY() + getHeight() - 1, getColor(mouseX, mouseY));
        Gui.drawRect(getX() + 2, getY() + getHeight() - 1, getX() + 2 + getWidth(), getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Wrapper.getMC().fontRenderer.drawStringWithShadow(list.getName() + " " + list.getValue().name(), getX() + 4, getY() + 2, -1);
        Wrapper.getMC().fontRenderer.drawStringWithShadow(". . .", getX() + this.getWidth() - 12, getY() + 3, Colorutil.Rainbow(counter[0] * 300));
    }

    private int getColor(int mouseX, int mouseY){
        Color color = getModuleButton().getModule().isHidden() ? Colorutil.getToggledC() : new Color(50, 50, 50, 200);
        return isHovered(mouseX, mouseY) ? (getModuleButton().getModule().isHidden() ? color.darker().darker().getRGB() : color.brighter().brighter().getRGB()) : color.getRGB();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(isHovered(mouseX, mouseY)) {
            List<Enum> values = Arrays.asList(getValues().getEnumConstants());
            int in = values.indexOf(list.getValue());
            if(button == 0) {
                if(in + 1 < values.size()) {
                    list.setValue(values.get(in + 1));
                } else {
                    list.setValue(values.get(0));
                }

            }
        }
    }

    private Class<Enum> getValues() {
        return list.getValue().getDeclaringClass();
    }
}
