package dev.leap.frog.Module.Movement;

import dev.leap.frog.Event.Network.EventPacket;
import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Module.Module;
import dev.leap.frog.Settings.Setting;
import dev.leap.frog.Util.Render.Renderutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class Nofall extends Module {

    public Nofall() {
        super("No fall", "Prevents you from falling", Type.MOVEMENT);
    }

    Setting<Mode> mode = create("Mode", Mode.Packet);
    Setting<Integer> height = create("Height", 5, 3, 255);

    private enum Mode {
        Packet,
        Web,
        WaterBucket,
        TP
    }

    public int findWebsInHotbar() {
        for (int i = 0; i < 9; i++) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }
            Block block = ((ItemBlock) stack.getItem()).getBlock();

            if (block instanceof BlockWeb) {
                return i;
            }

        }
        return -1;
    }

    @Override
    public void onUpdate() {
        if(UtilManager.nullCheck()) return;
        if(mc.player.fallDistance >= height.getValue()) {
            switch (mode.getValue()) {
                case Web:
                    if(findWebsInHotbar() != -1) {
                        if (mc.player.inventory.currentItem != findWebsInHotbar()) {
                            mc.player.inventory.currentItem = findWebsInHotbar();
                        }

                        mc.player.setPosition(Math.floor(mc.player.posX) + 0.5d, mc.player.posY, Math.floor(mc.player.posZ) + 0.5d);
                        mc.player.rotationPitch = 90;
                        mc.playerController.processRightClickBlock(mc.player, mc.world, new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ).down(), EnumFacing.DOWN, new Vec3d(mc.player.posX, mc.player.posY, mc.player.posZ).subtract(0, 0, 0), EnumHand.MAIN_HAND);
                    }
                    break;
                case WaterBucket:
                    if(mc.player.getHeldItemMainhand().getItem() != Items.WATER_BUCKET) {
                        for(int i = 0; i < 9; i++) {
                            if(mc.player.inventory.getStackInSlot(i).getItem() == Items.WATER_BUCKET) {
                                mc.player.inventory.currentItem = i;
                                mc.playerController.updateController();
                                break;
                            }
                        }
                    }

                    mc.player.rotationPitch = 90;
                    mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
                    break;
                case TP:
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, 1000, mc.player.posZ, mc.player.onGround));
                    break;
            }
        }
    }

    @EventHandler
    private Listener<EventPacket.SendPacket> packetListener = new Listener<>(event -> {

        if(event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer packet = (CPacketPlayer) event.getPacket();
            if(mode.getValue() == Mode.Packet) {
                if(mc.player.fallDistance > 3) {
                    packet.onGround = true;
                }
            }
        }

    });
}
