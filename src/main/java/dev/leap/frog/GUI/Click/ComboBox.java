package dev.leap.frog.GUI.Click;

import dev.leap.frog.GUI.AbstractWidget;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Settings.Settings;
import dev.leap.frog.Util.Render.Drawutil;

import java.util.ArrayList;

public class ComboBox extends AbstractWidget {

    private ArrayList<String> values;

    private Frame        frame;
    private ModuleButton master;
    private Settings setting;

    private String comboboxName;

    private int x;
    private int y;

    private int width;
    private int height;

    private int comboboxActualValue;

    private int saveY;

    private boolean can;

    private Drawutil font = new Drawutil(1);

    private int borderSize = 0;

    public ComboBox(Frame frame, ModuleButton master, String tag, int updatePostion) {
        this.values  = new ArrayList<>();
        this.frame   = frame;
        this.master  = master;
        this.setting = LeapFrog.getSettingsManager().getSettingsbyTag(master.getModule(), tag);

        this.x = master.getX();
        this.y = updatePostion;

        this.saveY = this.y;

        this.width  = master.getWidth();
        this.height = font.getStringHeight();

        this.comboboxName = this.setting.getName();

        this.can = true;

        int count = 0;

        for (String values : this.setting.get_values()) {
            this.values.add(values);

            count++;
        }

        for (int i = 0; i >= this.values.size(); i++) {
            if (this.values.get(i).equals(this.setting.getCurrentValue())) {
                this.comboboxActualValue = i;

                break;
            }
        }
    }

    public Settings get_setting() {
        return this.setting;
    }

    @Override
    public void doesCan(boolean value) {
        this.can = value;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public int get_save_y() {
        return this.saveY;
    }

    @Override
    public boolean motionPass(int mx, int my) {
        return motion(mx, my);
    }

    public boolean motion(int mx, int my) {
        if (mx >= getX() && my >= get_save_y() && mx <= getX() + getWidth() && my <= get_save_y() + getHeight()) {
            return true;
        }

        return false;
    }

    public boolean can() {
        return this.can;
    }

    @Override
    public void mouse(int mx, int my, int mouse) {
        if (mouse == 0) {
            if (motion(mx, my) && this.master.isOpen() && can()) {
                this.frame.doesCan(false);

                this.setting.setCurrentValue(this.values.get(this.comboboxActualValue));

                this.comboboxActualValue++;
            }
        }
    }

    @Override
    public void render(int master_y, int separe, int absolute_x, int absolute_y) {
        setWidth(this.master.getWidth() - separe);

        String zbob = "me";

        this.saveY = this.y + master_y;

        int ns_r = LeapFrog.clickGUI.themeWidgetNameR;
        int ns_g = LeapFrog.clickGUI.themeWidgetNameG;
        int ns_b = LeapFrog.clickGUI.themeWidgetNameB;
        int ns_a = LeapFrog.clickGUI.themeWidgetNameB;

        int bg_r = LeapFrog.clickGUI.themeWidgetBackgroundR;
        int bg_g = LeapFrog.clickGUI.themeWidgetBackgroundG;
        int bg_b = LeapFrog.clickGUI.themeWidgetBackgroundB;
        int bg_a = LeapFrog.clickGUI.themeWidgetBackgroundA;

        int bd_r = LeapFrog.clickGUI.themeWidgetBorderR;
        int bd_g = LeapFrog.clickGUI.themeWidgetBorderG;
        int bd_b = LeapFrog.clickGUI.themeWidgetBorderB;
        int bd_a = 100;

        Drawutil.drawString(this.comboboxName + " " + this.setting.getCurrentValue(), this.x + 2, this.saveY, ns_r, ns_g, ns_b, ns_a);

        if (this.comboboxActualValue >= this.values.size()) {
            this.comboboxActualValue = 0;
        }
    }

}
