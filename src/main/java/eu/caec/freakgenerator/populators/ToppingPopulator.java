package eu.caec.freakgenerator.populators;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;

import java.util.Random;

public class ToppingPopulator {
    public void populate(World world, Chunk chunk, Random random) {
        Biome b;
        for (int X = 0; X < 16; X++) {
            for (int Z = 0; Z < 16; Z++) {
                b = world.getBiome( chunk.getX()*16 + X, chunk.getZ()*16 + Z );
                int Y;
                switch (b) {
                    case ICE_PLAINS:
                        for (Y = 144; chunk.getBlock(X, Y, Z).getType() == Material.AIR; Y--);
                        chunk.getBlock(X,Y+1,Z).setType(Material.SNOW);
                        break;
                    case DESERT:
                        for (Y = 60; chunk.getBlock(X, Y, Z).getType() != Material.AIR; Y++);
                        if (random.nextInt(31) == 1) {
                            if (random.nextBoolean() && random.nextBoolean()) {
                                chunk.getBlock(X, Y, Z).setType(Material.CACTUS);
                                if (random.nextBoolean()) {
                                    chunk.getBlock(X, Y+1, Z).setType(Material.CACTUS);
                                    if (random.nextBoolean()) {
                                        chunk.getBlock(X, Y+2, Z).setType(Material.CACTUS);
                                    }
                                }
                            } else {
                                chunk.getBlock(X, Y, Z).setType(Material.DEAD_BUSH);
                            }
                        }
                        break;
                    case OCEAN, FROZEN_OCEAN:
                        break;
                    case SWAMPLAND:
                        if (chunk.getBlock(X, 63, Z).getType() == Material.STATIONARY_WATER) {
                            if (random.nextInt(24) == 1) {
                                chunk.getBlock(X,64,Z).setType(Material.WATER_LILY);
                            }
                        }
                        break;
                    default:
                        if (random.nextBoolean() && random.nextBoolean()) {
                            for (Y = 60; chunk.getBlock(X, Y, Z).getType() != Material.AIR; Y++) {
                                if (chunk.getBlock(X, Y, Z).getType() == Material.LOG) return;
                                if (chunk.getBlock(X, Y, Z).getType() == Material.LOG_2) return;
                                if (chunk.getBlock(X, Y, Z).getType() == Material.LEAVES) return;
                                if (chunk.getBlock(X, Y, Z).getType() == Material.LEAVES_2) return;
                            }
                            if (random.nextInt(24) == 1) {
                                if (random.nextBoolean()) {
                                    chunk.getBlock(X,Y,Z).setType(Material.YELLOW_FLOWER);
                                } else {
                                    chunk.getBlock(X,Y,Z).setType(Material.RED_ROSE);
                                    chunk.getBlock(X,Y,Z).setData((byte) random.nextInt(9));
                                }
                            } else {
                                chunk.getBlock(X,Y,Z).setType(Material.LONG_GRASS);
                                chunk.getBlock(X,Y,Z).setData((byte) 1);
                            }
                        }
                }
            }
        }
    }
}
