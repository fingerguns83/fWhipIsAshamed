package net.fg83.fwhipisashamed.client.task;

import net.fg83.fwhipisashamed.client.Admonishment;
import net.fg83.fwhipisashamed.client.ClientStatStorage;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class TestBlocksTask implements Runnable {
    ClientPlayerEntity player;
    BlockHitResult hitResult;
    ClientStatStorage stats = ClientStatStorage.load();

    public TestBlocksTask(ClientPlayerEntity player, HitResult hitResult){
        this.player = player;
        this.hitResult = (BlockHitResult) hitResult;
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
                    assert MinecraftClient.getInstance().world != null;
                    if (MinecraftClient.getInstance().world.getBlockState(pos).getBlock().equals(Blocks.ENDER_CHEST)){
                        foundEnderChests.add(pos);
                    }
                }
            }
        }

        foundEnderChests.remove(hitResult.getBlockPos().offset(hitResult.getSide()));

        if (!foundEnderChests.isEmpty()){
            stats.incrementBlocksPlaced();
            String plurality = "time";
            int statistic = stats.getBlocksPlaced();
            if (statistic > 1){
                plurality += "s";
            }
            MutableText message = Text.literal(new Admonishment(player, foundEnderChests).get()).formatted(Formatting.AQUA, Formatting.ITALIC);
            player.sendMessage(
                    message.append(Text.literal(" (You've made this mistake " + statistic + " " + plurality + ".)").formatted(Formatting.RED)),
                    false
            );
        }
    }
}
