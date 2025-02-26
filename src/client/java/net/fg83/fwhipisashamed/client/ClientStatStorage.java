package net.fg83.fwhipisashamed.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ClientStatStorage {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File STAT_FILE = new File("config/fwhipisashamed/stats.json");

    private int blocksPlaced = 0;  // Example tracked stat

    // Load stats from file
    public static ClientStatStorage load() {
        if (STAT_FILE.exists()) {
            try (FileReader reader = new FileReader(STAT_FILE)) {
                return GSON.fromJson(reader, ClientStatStorage.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ClientStatStorage(); // Return default if no file exists
    }

    // Save stats to file
    public void save() {
        try {
            if (!STAT_FILE.getParentFile().exists()) STAT_FILE.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(STAT_FILE)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Example getter & setter
    public int getBlocksPlaced() {
        return blocksPlaced;
    }

    public void setBlocksPlaced(int count) {
        this.blocksPlaced = count;
        save();
    }

    public void incrementBlocksPlaced() {
        this.blocksPlaced++;
        save();
    }

}
