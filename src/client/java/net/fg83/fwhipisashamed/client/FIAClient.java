package net.fg83.fwhipisashamed.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

import net.fg83.fwhipisashamed.client.task.TestBlocksTask;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.*;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;

import net.minecraft.item.Items;

import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class FIAClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClient()){
                ClientWorld clientWorld = MinecraftClient.getInstance().world;
                assert clientWorld != null;

                BlockPos clickedPos = hitResult.getBlockPos();
                BlockState clickedState = clientWorld.getBlockState(clickedPos);
                Block clickedBlock = clickedState.getBlock();
                BlockEntity blockEntity = clientWorld.getBlockEntity(clickedPos);


                if (!player.isSneaking()){
                    if (blockEntity != null){
                        if (
                                blockEntity instanceof BeaconBlockEntity ||
                                blockEntity instanceof BedBlockEntity ||
                                blockEntity instanceof BellBlockEntity ||
                                blockEntity instanceof ComparatorBlockEntity ||
                                blockEntity instanceof DaylightDetectorBlockEntity ||
                                blockEntity instanceof DecoratedPotBlockEntity ||
                                blockEntity instanceof EnchantingTableBlockEntity ||
                                blockEntity instanceof EnderChestBlockEntity ||
                                blockEntity instanceof LockableContainerBlockEntity ||
                                blockEntity instanceof SignBlockEntity
                        ){
                            return ActionResult.PASS;
                        }
                    }
                    if (
                            clickedBlock.equals(Blocks.CRAFTING_TABLE) ||
                            clickedBlock.equals(Blocks.CARTOGRAPHY_TABLE) ||
                            clickedBlock.equals(Blocks.ANVIL) ||
                            clickedBlock.equals(Blocks.LOOM) ||
                            clickedBlock.equals(Blocks.SMITHING_TABLE) ||
                            clickedBlock.equals(Blocks.STONECUTTER) ||
                            clickedBlock.equals(Blocks.GRINDSTONE)
                    ){
                        return ActionResult.PASS;
                    }
                }

                if (player.getStackInHand(hand).getItem().equals(Items.ENDER_CHEST)){
                    new Thread(new TestBlocksTask((ClientPlayerEntity) player, hitResult)).start();
                }
            }

            return ActionResult.PASS;
        });
    }
}
