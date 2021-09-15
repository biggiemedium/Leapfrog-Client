package dev.leap.frog.GUI.ClickGUI.Components.Types;

import dev.leap.frog.GUI.ClickGUI.Components.Component;
import dev.leap.frog.GUI.ClickGUI.ModuleButton;
import dev.leap.frog.Settings.Setting;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class FloatSlider extends Component {

    Setting<Float> value;

    public FloatSlider(Setting<Float> val ,int x, int y, ModuleButton button) {
        super(button, x, y, button.getWidth() - 2, 13);
        this.value = val;
    }

    private boolean dragging = false;

    @Override
    public void draw(int mouseX, int mouseY) {
        String state;

    }

    private double handleMath(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(isHovered(mouseX, mouseY)) {
            if(button == 0) {


                dragging = true;
                return;
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
    }
}
