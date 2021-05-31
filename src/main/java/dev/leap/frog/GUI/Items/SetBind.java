package dev.leap.frog.GUI.Items;

import dev.leap.frog.GUI.AbstractWidget;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Util.Render.Drawutil;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class SetBind extends AbstractWidget {

    private Frame        frame;
    private ModuleButton master;

    private String button_name;
    private String points;

    private int x;
    private int y;

    private int width;
    private int height;

    private int save_y;

    private float tick;

    private boolean can;
    private boolean waiting;

    private Drawutil font = new Drawutil(1);

    private int border_size = 0;

    public SetBind(Frame frame, ModuleButton master, String tag, int update_postion) {
        this.frame   = frame;
        this.master  = master;

        this.x = master.getX();
        this.y = update_postion;

        this.save_y = this.y;

        this.width  = master.getWidth();
        this.height = font.getStringHeight();

        this.button_name = tag;

        this.can    = true;
        this.points = ".";
        this.tick   = 0;
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
        return this.save_y;
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
    public boolean isBinding() {
        return this.waiting;
    }

    @Override
    public void bind(char char_, int key) {
        if (this.waiting) {
            switch (key) {
                case Keyboard.KEY_ESCAPE: {
                    this.waiting = false;

                    break;
                }

                case Keyboard.KEY_DELETE: {
                    this.master.getModule().setKey(-1);

                    this.waiting = false;

                    break;
                }

                default : {
                    this.master.getModule().setKey(key);

                    this.waiting = false;

                    break;
                }
            }
        }
    }

    @Override
    public void mouse(int mx, int my, int mouse) {
        if (mouse == 0) {
            if (motion(mx, my) && this.master.isOpen() && can()) {
                this.frame.doesCan(false);

                this.waiting = true;
            }
        }
    }

    @Override
    public void render(int master_y, int separe, int absolute_x, int absolute_y) {
        setWidth(this.master.getWidth() - separe);

        float[] tick_color = {
                (System.currentTimeMillis() % (360 * 32)) / (360f * 32)
        };

        int color_a = Color.HSBtoRGB(tick_color[0], 1, 1);

        int bd_a = (color_a);

        if ((color_a) <= 100) {
            bd_a = 100;
        } else if ((color_a) >= 200) {
            bd_a = 200;
        } else {
            bd_a = (color_a);
        }

        if (this.waiting) {
            if (this.tick >= 15) {
                this.points = "..";
            }

            if (this.tick >= 30) {
                this.points = "...";
            }

            if (this.tick >= 45) {
                this.points = ".";
                this.tick   = 0.0f;
            }
        }

        boolean zbob = true;

        this.save_y = this.y + master_y;

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

        if (this.waiting) {
            Drawutil.drawRect(getX(), this.save_y, getX() + this.width, this.save_y + this.height, bg_r, bg_g, bg_b, bg_a);

            this.tick += 0.5f;

            Drawutil.drawString("Listening " + this.points, this.x + 2, this.save_y, ns_r, ns_g, ns_b, ns_a);
        } else {
            Drawutil.drawString("Bind <" + this.master.getModule().keyToString("string") + ">", this.x + 2, this.save_y, ns_r, ns_g, ns_b, ns_a);
        }

        tick_color[0] += 5;
    }

}
