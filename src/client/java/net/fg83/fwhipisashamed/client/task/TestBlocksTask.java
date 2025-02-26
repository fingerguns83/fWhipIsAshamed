package net.fg83.fwhipisashamed.client.task;

import net.fg83.fwhipisashamed.client.Admonishment;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class TestBlocksTask implements Runnable {
    ClientPlayerEntity player;
    public TestBlocksTask(ClientPlayerEntity player){
        this.player = player;
    }

    @Override
    public void run() {
        int initX = player.getBlockX();
        int initY = player.getBlockY();
        int initZ = player.getBlockZ();

        List<BlockPos> foundEnderChests = new ArrayList<>();

        for (int x = initX - 5; x < initX + 5; x++){
            for (int y = initY - 5; y < initY + 5; y++){
                for (int z = initZ - 5; z < initZ + 5; z++){
                    BlockPos pos = new BlockPos(x, y, z);
                    if (player.getWorld().getBlockState(pos).getBlock().equals(Blocks.ENDER_CHEST)){
                        foundEnderChests.add(pos);
                    }
                }
            }
        }

        player.sendMessage(
                Text.literal(new Admonishment(player, foundEnderChests).get(), true);
        )
    }
}
