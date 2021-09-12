package dev.leap.frog.GUI.ClickGUI.Components.Types;

import dev.leap.frog.GUI.ClickGUI.Components.Component;
import dev.leap.frog.GUI.ClickGUI.ModuleButton;
import dev.leap.frog.Settings.Setting;

public class FloatSlider extends Component {

    Setting<Float> value;

    public FloatSlider(Setting<Float> val ,int x, int y, ModuleButton button) {
        super(button, x, y, button.getWidth() - 2, 13);
        this.value = val;
    }

    private boolean dragging = false;


}
