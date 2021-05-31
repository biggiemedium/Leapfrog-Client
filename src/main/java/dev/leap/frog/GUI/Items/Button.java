package dev.leap.frog.GUI.Items;

import dev.leap.frog.GUI.AbstractWidget;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Settings.Settings;
import dev.leap.frog.Util.Render.Drawutil;

public class Button extends AbstractWidget {

    private Frame        frame;
    private ModuleButton master;
    private Settings setting;

    private String button_name;

    private int x;
    private int y;

    private int width;
    private int height;

    private int saveY;

    private boolean can;

    private Drawutil font = new Drawutil(1);

    private int border_size = 0;

    public Button(Frame frame, ModuleButton master, String tag, int update_postion) {
        this.frame   = frame;
        this.master  = master;
        this.setting = LeapFrog.getSettingsManager().getSettingsbyTag(master.getModule(), tag);

        this.x = master.getX();
        this.y = update_postion;

        this.saveY = this.y;

        this.width  = master.getWidth();
        this.height = font.getStringHeight();

        this.button_name = this.setting.getName();

        this.can = true;
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

                this.setting.setValue(!(this.setting.getValue(true)));
            }
        }
    }

    @Override
    public void render(int master_y, int separe, int absolute_x, int absolute_y) {
        setWidth(this.master.getWidth() - separe);

        this.saveY = this.y + master_y;

        int ns_r = LeapFrog.clickGUI.themeWidgetNameR;
        int ns_g = LeapFrog.clickGUI.themeWidgetNameG;
        int ns_b = LeapFrog.clickGUI.themeWidgetNameB;
        int ns_a = LeapFrog.clickGUI.themeWidgetNameA;

        int bg_r = LeapFrog.clickGUI.themeWidgetBackgroundR;
        int bg_g = LeapFrog.clickGUI.themeWidgetBackgroundG;
        int bg_b = LeapFrog.clickGUI.themeWidgetBackgroundB;
        int bg_a = LeapFrog.clickGUI.themeWidgetBackgroundA;

        int bd_r = LeapFrog.clickGUI.themeWidgetBorderR;
        int bd_g = LeapFrog.clickGUI.themeWidgetBorderG;
        int bd_b = LeapFrog.clickGUI.themeWidgetBorderB;

        if (this.setting.getValue(true)) { // filling in the button box if button is enabled
            Drawutil.drawRect(getX(), this.saveY, getX() + this.width, this.saveY + this.height, bg_r, bg_g, bg_b, bg_a);
        }
        Drawutil.drawString(this.button_name, this.x + 2, this.saveY, ns_r, ns_g, ns_b, ns_a);
    }

}
