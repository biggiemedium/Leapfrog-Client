package dev.leap.frog.GUI.ClickGUI;

import dev.leap.frog.GUI.ClickGUI.Subbutton.BindButton;

import dev.leap.frog.GUI.ClickGUI.Subbutton.Slider;
import dev.leap.frog.GUI.ClickGUI.Subbutton.SliderTypes.FloatSlider;

import dev.leap.frog.GUI.ClickGUI.Subbutton.BooleanButton;
import dev.leap.frog.GUI.ClickGUI.Subbutton.WidgetHandler;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Colorutil;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.gui.Gui;
import scala.tools.nsc.backend.icode.TypeKinds;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ModuleButton {

    private Module module;
    private Frame frame;

    private int x;
    private int y;
    private int count;

    private boolean Svisable;
    public ArrayList<ModuleButton> settingButton;
    public ArrayList<WidgetHandler> handlers;

    private boolean opened;

    private int width;
    private int height;
    private int Colour;
    private int Offset;

    public ModuleButton(Module module, int x, int y,Frame frame, int offset) {
        this.module = module;
        this.x = x;
        this.y = y + 2 + offset;
        this.frame = frame;
        this.width = frame.width;
        this.height = Wrapper.getMC().fontRenderer.FONT_HEIGHT + 4;
        this.Offset = offset;

         // start of subbuttons below

        this.handlers = new ArrayList<>();
        int saveY = 0;

        for(Setting s : LeapFrog.getSettingManager().getSettingsArrayList()) {
            WidgetHandler button = null;
            if(s.getValue() instanceof Integer) {

                handlers.add(new FloatSlider(this, s.getValue()));
                saveY+= Wrapper.getMC().fontRenderer.FONT_HEIGHT + 6;

            } else if(s.getValue() instanceof Boolean) {
                button = new BooleanButton(s, x, y + height + saveY, this);
                saveY += Wrapper.getMC().fontRenderer.FONT_HEIGHT + 6;
            }
        }
        handlers.add(new BindButton(45, 54, this, saveY));

    }

    public Frame getFrame() {
        return frame;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getColour() {
        return Colour;
    }

    public void setColour(int colour) {
        Colour = colour;
    }

    public int getOffset() {
        return Offset;
    }

    public void setOffset(int offset) {
        Offset = offset;
    }

    public Module getModule() {
        return module;
    }

    public void draw(int mouseX, int mouseY)  {
        if (module.isToggled()) {
            Colour = Colorutil.getToggledColor();
        } else {
            Colour = Colorutil.getOffColor();
        }
        Gui.drawRect(this.x, this.y, this.x + width, this.y + this.height, Colour);
        Wrapper.getMC().fontRenderer.drawString(module.getName(), x + 2, y + 2, new Color(255, 255, 255).getRGB());
        if (frame.dragging) {
            setX(mouseX - frame.getPlusX());
            setY(mouseY - frame.getPlusY() + Offset);
        }


            height = Wrapper.getMC().fontRenderer.FONT_HEIGHT + 4;
            if (opened) {
                for (WidgetHandler h : handlers) {
                    h.draw(mouseX, mouseY);
                }
        }
    }

    public void OnClick (int x, int y, int button) throws IOException {
                if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {

                    if (button == 0) {
                        module.toggle();
                    } else if (button == 1) {
                        System.out.println("mouse pressed");
                        opened = !opened;
                    }




                        return;
                    }
                    if (opened) {
                        for (WidgetHandler handler : handlers) {
                            handler.mouseClicked(x, y, button);

                        }
                    }
                }


            public void MouseReleased (int mx, int my, int button){
                           if (Svisable) {
                               for (WidgetHandler h : handlers) {
                                    h.mouseReleased(mx, my, button);
                                }
                            }
                        }
                }