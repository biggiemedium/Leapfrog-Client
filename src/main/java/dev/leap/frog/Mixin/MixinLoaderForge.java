package dev.leap.frog.Mixin;

import dev.leap.frog.LeapFrog;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

public class MixinLoaderForge implements IFMLLoadingPlugin {

    public MixinLoaderForge() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.leapfrog.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
      //  LeapFrog.log.info("Mixins out");
        System.out.println("Mixins working");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }


    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
