package eu.caec.freakgenerator.populators;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;

import java.util.Random;

public class TreePopulator {
    public void populate(World world, Chunk chunk, Random random, TreeType tt) {
        int amount = random.nextInt(3) + 2;
        for (int i = 0; i < amount; i++) {
            int X = random.nextInt(15);
            int Z = random.nextInt(15);
            int Y;
            for (Y = 60; chunk.getBlock(X, Y, Z).getType() != Material.AIR; Y++);
            world.generateTree(chunk.getBlock(X, Y, Z).getLocation(), tt);
        }
    }

    public void populate(World world, Chunk chunk, Random random, TreeType tt, int min, int extra) {
        int amount = random.nextInt(extra) + min;
        for (int i = 0; i < amount; i++) {
            int X = random.nextInt(15);
            int Z = random.nextInt(15);
            int Y;
            for (Y = 60; chunk.getBlock(X, Y, Z).getType() != Material.AIR; Y++);
            world.generateTree(chunk.getBlock(X, Y, Z).getLocation(), tt);
        }
    }
}
