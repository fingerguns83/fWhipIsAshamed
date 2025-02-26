package net.fg83.fwhipisashamed.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Admonishment {

    ClientPlayerEntity player;
    List<BlockPos> foundEnderChests;

    public List<String> singleAdmonishments = new ArrayList<>();
    public List<String> multipleAdmonishments = new ArrayList<>();
    public List<String> generalAdmonishments = new ArrayList<>();

    public Admonishment(ClientPlayerEntity player, List<BlockPos> foundEnderChests){
        this.player = player;
        this.foundEnderChests = foundEnderChests;
        setupAdmonishments();
    }

    public String get(){
        shuffleLists();

        if (Math.random() < 0.5){
            if (foundEnderChests.size() == 1){
                return formatAdmonishment(singleAdmonishments.getFirst());
            }
            else {
                return formatAdmonishment(multipleAdmonishments.getFirst());
            }
        }
        else {
            return formatAdmonishment(generalAdmonishments.getFirst());
        }
    }

    public void setupAdmonishments(){
        singleAdmonishments.addAll(List.of(
                "Huh, sort of feels like someone did that here already...",
                "ANOTHA ONE",
                "Have you checked your surroundings? Of course you didn't, or you wouldn't be doing this.",
                "A little spacial awareness would go a long way, %n."
        ));
        multipleAdmonishments.addAll(List.of(
                "Oh for Pete's s...THERE ARE %i ENDER CHESTS AROUND YOU ALREADY!",
                "Really? Did you think we needed yet another one of those here?",
                "*Whistles three blind mice*",
                "Dawg, you're surrounded by those already.",
                "You should call your optometrist."
        ));
        generalAdmonishments.addAll(List.of(
                "Would you please just use your eyes? Good grief.",
                "Have you considered--and hear me out--looking around first?",
                "*conspicuous cough* Try *conspicuous cough* checking *conspicuous cough* %p *conspicuous cough*",
                "Oh not again...",
                "*Facepalm*",
                "Dangit, %n, look around!"
        ));
    }

    public void shuffleLists(){
        Collections.shuffle(singleAdmonishments);
        Collections.shuffle(multipleAdmonishments);
        Collections.shuffle(generalAdmonishments);
    }

    public String formatAdmonishment(String admonishment){

        String output = admonishment.replace("%i", Integer.toString(foundEnderChests.size()));
        output = output.replace("%n", player.getNameForScoreboard());
        output = output.replace("%p", getEnderChestPos());

        return output;
    }

    public String getEnderChestPos(){
        Collections.shuffle(foundEnderChests);
        BlockPos first = foundEnderChests.getFirst();
        return first.getX() + ", " + first.getY() + ", " + first.getZ();
    }
}
