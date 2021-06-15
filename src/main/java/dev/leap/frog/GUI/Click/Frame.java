package dev.leap.frog.GUI.Click;


import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Util.Render.Drawutil;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;

public class Frame {

    private Module.Type category;

    private ArrayList<ModuleButton> module_button;

    private int x;
    private int y;

    private int width;
    private int height;

    private int width_name;
    private int width_abs;

    private String frameName;
    private String frameTag;

    private Drawutil font = new Drawutil(1);

    private boolean first = false;
    private boolean move;

    private int move_x;
    private int move_y;

    private boolean can;

    private final Minecraft mc = Minecraft.getMinecraft();

    public Frame(Module.Type category) {
        this.x = 10;
        this.y = 10;

        this.width  = 100;
        this.height = 27;

        this.category = category;

        this.module_button = new ArrayList<>();

        this.width_name = font.getStringWidth(this.category.getName());
        this.width_abs  = this.width_name;

        this.frameName = category.getName();
        this.frameTag = category.getName();

        this.move_x = 0;
        this.move_y = 0;

        int size  = LeapFrog.getModuleManager().getModuleByType(category).size();
        int count = 0;

        for (Module modules : LeapFrog.getModuleManager().getModuleByType(category)) {
            ModuleButton buttons = new ModuleButton(modules, this);

            buttons.setY(this.height);

            this.module_button.add(buttons);

            count++;

            if (count >= size) {
                this.height += 10;
            } else {
                this.height += 17;
            }
        }

        this.move = false;
        this.can  = true;
    }

    public void refresh_frame(ModuleButton button, int combo_height) {

        this.height = 25;

        int size  = LeapFrog.getModuleManager().getModuleByType(this.category).size();
        int count = 0;

        for (ModuleButton buttons : this.module_button) {
            buttons.setY(this.height);

            count++;

            int compare;
            if (count >= size) {
                compare = 10;
            } else {
                compare = 17;
            }

            if (buttons.isOpen()) {
                if (compare == 10) {
                    this.height += buttons.getSettingsHeight() - compare;
                } else {
                    this.height += buttons.getSettingsHeight();
                }
            } else {
                this.height += compare;
            }
        }
    }

    public void doesCan(boolean value) {
        this.can = value;
    }

    public void setMove(boolean value) {
        this.move = value;
    }

    public void setMoveX(int x) {
        this.move_x = x;
    }

    public void setMoveY(int y) {
        this.move_y = y;
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

    public String getName() {
        return this.frameName;
    }

    public String getTag() {
        return this.frameTag;
    }

    public boolean isMoving() {
        return this.move;
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

    public boolean can() {
        return this.can;
    }

    public boolean motion(int mx, int my) {
        return mx >= getX() && my >= getY() && mx <= getX() + getWidth() && my <= getY() + getHeight();
    }

    public boolean motion(String tag, int mx, int my) {
        return mx >= getX() && my >= getY() && mx <= getX() + getWidth() && my <= getY() + font.getStringHeight();
    }

    public void crush(int mx, int my) {

        int screen_x = (mc.displayWidth / 2);
        int screen_y = (mc.displayHeight / 2);

        setX(mx - this.move_x);
        setY(my - this.move_y);

        if (this.x + this.width >= screen_x) {
            this.x = screen_x - this.width - 1;
        }

        if (this.x <= 0) {
            this.x = 1;
        }

        if (this.y + this.height >= screen_y) {
            this.y = screen_y - this.height - 1;
        }

        if (this.y <= 0) {
            this.y = 1;
        }

        if (this.x % 2 != 0) {
            this.x += this.x % 2;
        }

        if (this.y % 2 != 0) {
            this.y += this.y % 2;
        }
    }

    public boolean isBinding() {
        boolean value_requested = false;

        for (ModuleButton buttons : this.module_button) {
            if (buttons.isBinding()) {
                value_requested = true;
            }
        }

        return value_requested;
    }

    public void doesButtonForDoWidgetsCan(boolean can) {
        for (ModuleButton buttons : this.module_button) {
            buttons.doesWidgetsCan(can);
        }
    }

    public void bind(char char_, int key) {
        for (ModuleButton buttons : this.module_button) {
            buttons.bind(char_, key);
        }
    }

    public void mouse(int mx, int my, int mouse) {
        for (ModuleButton buttons : this.module_button) {
            buttons.mouse(mx, my, mouse);
        }
    }

    public void mouseRelease(int mx, int my, int mouse) {
        for (ModuleButton buttons : this.module_button) {
            buttons.buttonRelease(mx, my, mouse);
        }
    }

    public void render(int mx, int my) {
        float[] tick_color = {
                (System.currentTimeMillis() % (360 * 32)) / (360f * 32)
        };

        int color_a = Color.HSBtoRGB(tick_color[0], 1, 1);

        int border_a;
        if ((color_a) <= 50) {
            border_a = 50;
        } else border_a = Math.min((color_a), 120);

        int nc_r = LeapFrog.clickGUI.themeFrameNameR;
        int nc_g = LeapFrog.clickGUI.themeFrameNameG;
        int nc_b = LeapFrog.clickGUI.themeFrameNameB;
        int nc_a = LeapFrog.clickGUI.themeFrameNameA;

        int bg_r = LeapFrog.clickGUI.themeFrameBackgroundR;
        int bg_g = LeapFrog.clickGUI.themeFrameBackgroundG;
        int bg_b = LeapFrog.clickGUI.themeFrameBackgroundB;
        int bg_a = LeapFrog.clickGUI.themeFrameBackgroundA;

        int bd_r = LeapFrog.clickGUI.themeFrameBorderR;
        int bd_g = LeapFrog.clickGUI.themeFrameBorderG;
        int bd_b = LeapFrog.clickGUI.themeFrameBorderB;
        int bd_a = border_a;

        this.frameName = this.category.getName();
        this.width_name = font.getStringWidth(this.category.getName());

        Drawutil.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, bg_r, bg_g, bg_b, bg_a);
        int border_size = 1;
        Drawutil.drawRect(this.x - 1, this.y, this.width + 1, this.height, bd_r, bd_g, bd_b, bd_a, border_size, "left-right");

        Drawutil.drawString(this.frameName, this.x + 4, this.y + 4, nc_r, nc_g, nc_b, nc_a);

        if (isMoving()) {
            crush(mx, my);
        }

        for (ModuleButton buttons : this.module_button) {
            buttons.setX(this.x + 2);

            buttons.render(mx, my, 2);
        }

        tick_color[0] += 1;
    }

}
