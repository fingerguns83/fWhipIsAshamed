package net.fg83.fwhipisashamed.task;

import net.fg83.fwhipisashamed.FIA;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class TestBlocksTask implements Runnable {
    BlockHitResult hitResult;
    ServerPlayerEntity player;

    public TestBlocksTask(ServerPlayerEntity player, HitResult hitResult){
        this.player = player;
        this.hitResult = (BlockHitResult) hitResult;
    }

    @Override
    public void run() {
        int initX = player.getBlockX();
        int initY = player.getBlockY();
        int initZ = player.getBlockZ();

        TestLoop:
        for (int x = initX - 5; x < initX + 5; x++){
            for (int y = initY - 5; y < initY + 5; y++){
                for (int z = initZ - 5; z < initZ + 5; z++){
                    BlockPos pos = new BlockPos(x, y, z);
                    if (player.getWorld().getBlockState(pos).getBlock().equals(Blocks.ENDER_CHEST)){
                        if (!hitResult.getBlockPos().offset(hitResult.getSide()).equals(pos)){
                            player.incrementStat(FIA.PLACE_DUPLICATE_ENDER_CHEST);
                            break TestLoop;
                        }
                    }
                }
            }
        }
    }
}
