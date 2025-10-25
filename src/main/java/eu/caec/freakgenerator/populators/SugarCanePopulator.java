package eu.caec.freakgenerator.populators;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Random;

public class SugarCanePopulator {
    public void populate(World world, Chunk chunk, Random random) {
        int x = random.nextInt(14) + 1;
        int z = random.nextInt(14) + 1;
        attemptPlacement(world, chunk, random, x, z);
        attemptPlacement(world, chunk, random, x+1, z);
        attemptPlacement(world, chunk, random, x, z+1);
        attemptPlacement(world, chunk, random, x-1, z);
        attemptPlacement(world, chunk, random, x, z-1);
    }
    
    public void attemptPlacement(World world, Chunk chunk, Random random, int X, int Z) {
        if (chunk.getBlock(X, 63, Z).getType() == Material.GRASS || chunk.getBlock(X, 63, Z).getType() == Material.SAND) {
            if (reedPlaceable(world, X + chunk.getX()*16, Z + chunk.getZ()*16)) {
                chunk.getBlock(X, 64, Z).setType(Material.SUGAR_CANE_BLOCK);
                if (random.nextBoolean()) {
                    chunk.getBlock(X, 65, Z).setType(Material.SUGAR_CANE_BLOCK);
                    if (random.nextBoolean()) {
                        chunk.getBlock(X, 66, Z).setType(Material.SUGAR_CANE_BLOCK);
                    }
                }
            }
        }
    }

    private boolean reedPlaceable(World world, int x, int z) {
        Material x0z1 = world.getBlockAt(x, 63, z+1).getType();
        Material x0z_1 = world.getBlockAt(x, 63, z-1).getType();
        Material x1z0 = world.getBlockAt(x+1, 63, z).getType();
        Material x_1z0 = world.getBlockAt(x-1, 63, z).getType();
        return (x1z0==Material.STATIONARY_WATER || x_1z0==Material.STATIONARY_WATER ||
                x0z1==Material.STATIONARY_WATER || x0z_1==Material.STATIONARY_WATER);
    }
}
