package dev.leap.frog.GUI.Click;


import dev.leap.frog.GUI.AbstractWidget;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import dev.leap.frog.Util.Render.Drawutil;

import java.util.ArrayList;

public class ModuleButton {

    private Module module;
    private Frame  master;

    private ArrayList<AbstractWidget> widget;

    private String moduleName;

    private boolean opened;

    private int x;
    private int y;

    private int width;
    private int height;

    private int opened_height;

    private int save_y;

    private Drawutil font = new Drawutil(1);

    private int border_a    = 200;
    private int border_size = 1;

    private int master_height_cache;

    public int settings_height;

    private int count;

    public ModuleButton(Module module, Frame master) {
        /**
         * A value to save the y. When move the frame the save_y does the work.
         * @param save_y;
         **/

        this.module = module;
        this.master = master;

        this.widget = new ArrayList();

        this.moduleName = module.getName();

        this.x = 0;
        this.y = 0;

        this.width  = font.getStringWidth(module.getName()) + 5;
        this.height = font.getStringHeight();

        this.opened_height = this.height;

        this.save_y = 0;

        this.opened = false;

        this.master_height_cache = master.getHeight();

        this.settings_height = this.y + 10;

        this.count = 0;

        for (Settings settings : LeapFrog.getSettingsManager().getSettingByModule(module)) {
            if (settings.getType().equals("button")) {
                this.widget.add(new Button(master, this, settings.getTag(), this.settings_height));

                this.settings_height += 10;

                this.count++;
            }

            if (settings.getType().equals("combobox")) {
                this.widget.add(new ComboBox(master, this, settings.getTag(), this.settings_height));

                this.settings_height += 10;

                this.count++;
            }

            if (settings.getType().equals("label")) {
                this.widget.add(new Label(master, this, settings.getTag(), this.settings_height));

                this.settings_height += 10;

                this.count++;
            }

            if (settings.getType().equals("doubleslider") || settings.getType().equals("integerslider")) {
                this.widget.add(new Slider(master, this, settings.getTag(), this.settings_height));

                this.settings_height += 10;

                this.count++;
            }
        }

        int size = LeapFrog.getSettingsManager().getSettingByModule(module).size();

        if (this.count >= size) {
            this.widget.add(new SetBind(master, this, "bind", this.settings_height));

            this.settings_height += 10;
        }
    }

    public Module getModule() {
        return this.module;
    }

    public Frame getMaster() {
        return this.master;
    }

    public void setPressed(boolean value) {
        this.module.setToggled(value);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setOpen(boolean value) {
        this.opened = value;
    }

    public boolean getState() {
        return this.module.isToggled();
    }

    public int getSettingsHeight() {
        return this.settings_height;
    }

    public int getCacheHeight() {
        return this.master_height_cache;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getSaveY() {
        return this.save_y;
    }

    public boolean isOpen() {
        return this.opened;
    }

    public boolean isBinding() {
        boolean value_requested = false;

        for (AbstractWidget widgets : this.widget) {
            if (widgets.isBinding()) {
                value_requested = true;
            }
        }

        return value_requested;
    }

    public boolean motion(int mx, int my) {
        if (mx >= getX() && my >= getSaveY() && mx <= getX() + getWidth() && my <= getSaveY() + getHeight()) {
            return true;
        }

        return false;
    }

    public void doesWidgetsCan(boolean can) {
        for (AbstractWidget widgets : this.widget) {
            widgets.doesCan(can);
        }
    }

    public void bind(char char_, int key) {
        for (AbstractWidget widgets : this.widget) {
            widgets.bind(char_, key);
        }
    }

    public void mouse(int mx, int my, int mouse) {
        for (AbstractWidget widgets : this.widget) {
            widgets.mouse(mx, my, mouse);
        }

        if (mouse == 0) {
            if (motion(mx, my)) {
                this.master.doesCan(false);

                setPressed(!getState());
            }
        }

        if (mouse == 1) {
            if (motion(mx, my)) {
                this.master.doesCan(false);

                setOpen(!isOpen());

                this.master.refresh_frame(this, 0);
            }
        }
    }

    public void buttonRelease(int mx, int my, int mouse) {
        for (AbstractWidget widgets : this.widget) {
            widgets.release(mx, my, mouse);
        }

        this.master.doesCan(true);
    }

    public void render(int mx, int my, int separe) {
        setWidth(this.master.getWidth() - separe);

        this.save_y = this.y + this.master.getY() - 10;

        int nm_r = LeapFrog.clickGUI.themeWidgetNameR;
        int nm_g = LeapFrog.clickGUI.themeWidgetNameG;
        int nm_b = LeapFrog.clickGUI.themeWidgetNameB;
        int nm_a = LeapFrog.clickGUI.themeWidgetNameA;

        int bg_r = LeapFrog.clickGUI.themeWidgetBackgroundR;
        int bg_g = LeapFrog.clickGUI.themeWidgetBackgroundG;
        int bg_b = LeapFrog.clickGUI.themeWidgetBackgroundB;
        int bg_a = LeapFrog.clickGUI.themeWidgetBackgroundA;

        int bd_r = LeapFrog.clickGUI.themeWidgetBorderR;
        int bd_g = LeapFrog.clickGUI.themeWidgetBorderG;
        int bd_b = LeapFrog.clickGUI.themeWidgetBorderB;

        if (this.module.isToggled()) {
            Drawutil.drawRect(this.x, this.save_y, this.x + this.width - separe, this.save_y + this.height, bg_r, bg_g, bg_b, bg_a);

            font.drawString(this.moduleName, this.x + separe, this.save_y, nm_r, nm_g, nm_b, nm_a);
        } else {
            font.drawString(this.moduleName, this.x + separe, this.save_y, nm_r, nm_g, nm_b, nm_a);
        }

        for (AbstractWidget widgets : this.widget) {
            widgets.setX(getX());

            boolean is_passing_in_widget = this.opened ? widgets.motionPass(mx, my) : false;

            if (motion(mx, my) || is_passing_in_widget) {
                Drawutil.drawRect(this.master.getX() - 1, this.save_y, this.master.getWidth() + 1, this.opened_height, bd_r, bd_g, bd_b, border_a, this.border_size, "right-left");
            }

            if (this.opened) {
                this.opened_height = this.height + this.settings_height - 10;

                widgets.render(getSaveY(), separe, mx, my);
            } else {
                this.opened_height = this.height;
            }
        }
    }

}
