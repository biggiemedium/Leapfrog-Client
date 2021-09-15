package dev.leap.frog.GUI.ClickGUI.Components.Types;

import dev.leap.frog.GUI.ClickGUI.Components.Component;
import dev.leap.frog.GUI.ClickGUI.ModuleButton;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Colorutil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class IntSlider extends Component {

    Setting<Integer> value;

    public IntSlider(Setting<Integer> val ,int x, int y, ModuleButton button) {
        super(button, x, y, button.getWidth() - 2, 13);
        this.value = val;
    }

    private boolean dragging = false;
    private int renderWidth;

    @Override
    public void draw(int mouseX, int mouseY) {
        update(mouseX, mouseY);

        Gui.drawRect(getX(), getY(), getX() + 2, getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());

        Gui.drawRect(getX() + 2, getY(), getX() + 2 + renderWidth, getY() + getHeight() - 1, getColor(mouseX, mouseY));

        Gui.drawRect(getX() + 2, getY() + getHeight() - 1, getX() + 2 + getWidth(), getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());

        Wrapper.getMC().fontRenderer.drawStringWithShadow(value.getName(), getX() + 4, getY() + 2, -1);
        Wrapper.getMC().fontRenderer.drawStringWithShadow(String.valueOf(value.getValue()), getX() + getWidth() - mc.fontRenderer.getStringWidth(String.valueOf(value.getValue())), getY() + 1, -1);
    }

    private int getColor(int mouseX, int mouseY){
        Color color = dragging ? Colorutil.getToggledC() : new Color(50, 50, 50, 200);
        boolean hovered = mouseX > getX() + 2 && mouseY > getY() && mouseX < getX() + 2 + renderWidth && mouseY < getY() + getHeight() - 1;
        return hovered ? color.darker().darker().getRGB() : color.getRGB();
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
        if(isHovered(mouseX, mouseY) && button == 0) {
            //if(button == 0) {
            dragging = true;
            // }
        }
    }

    private void update(int mouseX, int mouseY) {

        // taken from aurora
        float diff = Math.min(getWidth(), Math.max(0, mouseX - getX()));

        float min = value.getMin();
        float max = value.getMax();

        this.renderWidth = (int) (getModuleButton().getWidth() * (value.getValue() - min) / (max - min));
        if(dragging) {
            if(diff == 0.0) {
                this.value.setValue(value.getMin());
            } else {
                DecimalFormat format = new DecimalFormat("##");
                String newValue = format.format(((diff / getWidth()) * (max - min) + min));
                this.value.setValue(Integer.parseInt(newValue));
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;
    }

}
