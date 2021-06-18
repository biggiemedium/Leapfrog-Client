package dev.leap.frog.Module.ui;

import dev.leap.frog.LeapFrog;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import org.lwjgl.input.Keyboard;

public class ClickGUIModule extends Module {

    public ClickGUIModule() {
        super("ClickGUI", "CGUI", Type.RENDER);
        setKey(Keyboard.KEY_RSHIFT);
    }

    Settings tag = create("Description", "Description", "side", combobox("Hover", "side"));

    Settings label_frame = create("info", "ClickGUIInfoFrame", "Frames");

    Settings name_frame_r = create("Name R", "ClickGUINameFrameR", 255, 0, 255);
    Settings name_frame_g = create("Name G", "ClickGUINameFrameG", 255, 0, 255);
    Settings name_frame_b = create("Name B", "ClickGUINameFrameB", 255, 0, 255);

    Settings background_frame_r = create("Background R", "ClickGUIBackgroundFrameR", 186, 0, 255);
    Settings background_frame_g = create("Background G", "ClickGUIBackgroundFrameG", 151, 0, 255);
    Settings background_frame_b = create("Background B", "ClickGUIBackgroundFrameB", 231, 0, 255);
    Settings background_frame_a = create("Background A", "ClickGUIBackgroundFrameA", 104, 0, 255);
/*
    Settings border_frame_r = create("Border R", "ClickGUIBorderFrameR", 255, 0, 255);
    Settings border_frame_g = create("Border G", "ClickGUIBorderFrameG", 255, 0, 255);
    Settings border_frame_b = create("Border B", "ClickGUIBorderFrameB", 255, 0, 255);

 */

    Settings label_widget = create("info", "ClickGUIInfoWidget", "Widgets");

    Settings name_widget_r = create("Name R", "ClickGUINameWidgetR", 255, 0, 255);
    Settings name_widget_g = create("Name G", "ClickGUINameWidgetG", 255, 0, 255);
    Settings name_widget_b = create("Name B", "ClickGUINameWidgetB", 255, 0, 255);

    Settings background_widget_r = create("Background R", "ClickGUIBackgroundWidgetR", 255, 0, 255);
    Settings background_widget_g = create("Background G", "ClickGUIBackgroundWidgetG", 255, 0, 255);
    Settings background_widget_b = create("Background B", "ClickGUIBackgroundWidgetB", 255, 0, 255);
    Settings background_widget_a = create("Background A", "ClickGUIBackgroundWidgetA", 100, 0, 255);

    @Override
    public void onUpdate() {
        // Update frame colors.
        LeapFrog.clickGUI.themeFrameNameR = name_frame_r.getValue(1);
        LeapFrog.clickGUI.themeFrameNameG = name_frame_g.getValue(1);
        LeapFrog.clickGUI.themeFrameNameB = name_frame_b.getValue(1);

        LeapFrog.clickGUI.themeFrameBackgroundR = background_frame_r.getValue(1);
        LeapFrog.clickGUI.themeFrameBackgroundG = background_frame_g.getValue(1);
        LeapFrog.clickGUI.themeFrameBackgroundB = background_frame_b.getValue(1);
        LeapFrog.clickGUI.themeFrameBackgroundA = background_frame_a.getValue(1);

        /*
        LeapFrog.clickGUI.themeFrameBorderR = border_frame_r.getValue(1);
        LeapFrog.clickGUI.themeFrameBorderG = border_frame_g.getValue(1);
        LeapFrog.clickGUI.themeFrameBorderB = border_frame_b.getValue(1);

         */

        LeapFrog.clickGUI.themeWidgetNameR = name_widget_r.getValue(1);
        LeapFrog.clickGUI.themeWidgetNameG = name_widget_g.getValue(1);
        LeapFrog.clickGUI.themeWidgetNameB = name_widget_b.getValue(1);

        LeapFrog.clickGUI.themeWidgetBackgroundR = background_widget_r.getValue(1);
        LeapFrog.clickGUI.themeWidgetBackgroundG = background_widget_g.getValue(1);
        LeapFrog.clickGUI.themeWidgetBackgroundB = background_widget_b.getValue(1);
        LeapFrog.clickGUI.themeWidgetBackgroundA = background_widget_a.getValue(1);

    }


    @Override
    public void onEnable() {
        System.out.println("GUI Enabled");
        if(mc.player != null && mc.world != null) {
            mc.displayGuiScreen(dev.leap.frog.GUI.ClickGUI.getInstance);
        }
    }


}
