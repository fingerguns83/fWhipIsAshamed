package net.fg83.fwhipisashamed;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

import net.fg83.fwhipisashamed.task.TestBlocksTask;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.*;

import net.minecraft.item.Items;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import net.minecraft.server.network.ServerPlayerEntity;

import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;

import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;


public class FIA implements ModInitializer {

    public static final Identifier PLACE_DUPLICATE_ENDER_CHEST = Identifier.of("fwhipisashamed", "place_duplicate_ender_chest");

    @Override
    public void onInitialize() {
        Registry.register(Registries.CUSTOM_STAT, "place_duplicate_ender_chest", PLACE_DUPLICATE_ENDER_CHEST);
        Stats.CUSTOM.getOrCreateStat(PLACE_DUPLICATE_ENDER_CHEST, StatFormatter.DEFAULT);

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!world.isClient()){

                BlockPos clickedPos = hitResult.getBlockPos();
                BlockState clickedState = world.getBlockState(clickedPos);
                Block clickedBlock = clickedState.getBlock();
                BlockEntity blockEntity = world.getBlockEntity(clickedPos);

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
                    new Thread(new TestBlocksTask((ServerPlayerEntity) player, hitResult)).start();
                }
            }

            return ActionResult.PASS;
        });
    }
}
