package net.fg83.fwhipisashamed.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class Admonishment {

    ClientPlayerEntity player;
    List<BlockPos> foundEnderChests;

    List<String> singleAdmonishment = List.of(
            "Huh, sort of feels like someone did that here already...",
            "ANOTHA ONE",

    );
    List<String> multipleAdmonishments = List.of(
            "Oh for Pete's s----THERE ARE % E CHESTS AROUND YOU ALREADY!",

    );
    List<String> generalAdmonishments = List.of(
            "Would you please just use your eyes? Good grief.",
            "Dawg, you're surrounded by those already.",
            "Have you considered--and hear me out--looking around first?",
    );

    public Admonishment(ClientPlayerEntity player, List<BlockPos> foundEnderChests){
        this.player = player;
        this.foundEnderChests = foundEnderChests;
    }

    public String get(){
        String playerName = player.getName().getString();

    }
}
