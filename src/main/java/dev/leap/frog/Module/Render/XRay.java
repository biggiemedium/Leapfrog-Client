package dev.leap.frog.Module.Render;

import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;

import java.util.ArrayList;

public class XRay extends Module {

    public XRay() {
        super("XRay", "Shows hidden blocks", Type.RENDER);
    }

    public static XRay INSTANCE = new XRay();
    private ArrayList<String> blocks = new ArrayList<>();

    Setting<Boolean> diamonds = create("Diamonds", true);
    Setting<Boolean> redstone = create("Redstone", false);
    Setting<Boolean> iron = create("Iron", true);
    Setting<Boolean> coal = create("Coal", true);
    Setting<Boolean> gold = create("Gold", true);
    Setting<Boolean> liquids = create("Liquids", 50, 1, 100);
    Setting<Integer> opacity = create("Opactiy", 50, 1, 100);

    public boolean isLiquidVisible() {
        boolean b = Boolean.valueOf(liquids.getValue());

        return b;
    }

    @Override
    public void onEnable() {

        if(diamonds.getValue()) {
            blocks.add(diamonds.getName());
        }
        if(redstone.getValue()) {
            blocks.add(redstone.getName());
        }
        if(iron.getValue()) {
            blocks.add(iron.getName());
        }
        if(coal.getValue()) {
            blocks.add(coal.getName());
        }
        if(gold.getValue()) {
            blocks.add(gold.getName());
        }
    }

    @Override
    public void onDisable() {
        blocks.clear();
    }

    public ArrayList<String> getBlocks() {
        return blocks;
    }
}
