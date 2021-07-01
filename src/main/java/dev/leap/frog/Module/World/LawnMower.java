package dev.leap.frog.Module.World;

import dev.leap.frog.Event.Movement.EventPlayerMotionUpdate;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Settings;
import dev.leap.frog.Util.Block.Blockutil;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.Comparator;

public class LawnMower extends Module {

    public LawnMower() {

        super("LawnMower", "Cuts grass", Type.WORLD);

    }

    Settings distance = create("Distance", "Distance", 4, 0, 10);
    Settings flowers = create("Flowers", "Flowers", true);
    Settings tallgrass = create("tallGrass", "tallGrass", true);
    Settings hand = create("Hand", "Hand", "main", combobox("main", "offhand"));

    @EventHandler
    private Listener<EventPlayerMotionUpdate> moveListener = new Listener<>(event-> {

        BlockPos pos = Blockutil.getSphere(getPosFloored(), distance.getValue(1), distance.getValue(1), false, true, 0)
                .stream()
                .filter(blockPos -> isValid(blockPos))
                .min(Comparator.comparing(blockPos -> getDistanceOfEntityToBlock(mc.player, blockPos)))
                .orElse(null);

        if(pos != null) { // @Todo: make no rotate

            event.cancel();


            if(hand.in("main")) {
                mc.player.swingArm(EnumHand.MAIN_HAND);
            } else {
                mc.player.swingArm(EnumHand.OFF_HAND);
            }

            mc.playerController.clickBlock(pos, EnumFacing.UP);

        }

    });

    private boolean isValid(BlockPos pos) {

        IBlockState state = mc.world.getBlockState(pos);

        if(state.getBlock() instanceof BlockTallGrass && tallgrass.getValue(true)) {
            return true;
        }

        if(flowers.getValue(true) && state.getBlock() instanceof BlockFlower) {
            return true;
        }

        if(tallgrass.getValue(true) && state.getBlock() instanceof BlockDoublePlant) {
            return true;
        }

        return false;
    }

    public double getDistanceOfEntityToBlock(Entity entity, BlockPos pos) {
        return getDistance(entity.posX, entity.posY, entity.posZ, pos.getX(), pos.getY(), pos.getZ());
    }

    public static double getDistance(double X2, double Y2, double Z2, double x, double y, double z) {
        double d0 = X2 - x;
        double d1 = Y2 - y;
        double d2 = Z2 - z;
        return (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }


    private BlockPos getPosFloored() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

}
