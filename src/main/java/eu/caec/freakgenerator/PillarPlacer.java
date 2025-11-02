package eu.caec.freakgenerator;

import eu.caec.freakgenerator.noise.FastNoiseLite;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

public class PillarPlacer {
    private final FastNoiseLite alternateMaterialNoise;

    public PillarPlacer(int seed) {
        alternateMaterialNoise = new FastNoiseLite(seed);
        alternateMaterialNoise.SetFrequency(0.01f);
        alternateMaterialNoise.SetFractalType(FastNoiseLite.FractalType.Ridged);
        alternateMaterialNoise.SetFractalOctaves(3);
    }

    enum BiomeType {
        Green,
        Desert,
        Snow
    }

    public ChunkGenerator.ChunkData placePillars(int X, int Y, int Z, ChunkGenerator.ChunkData chunk, Biome biome, int chunkX, int chunkZ) {
        BiomeType biomeType;

        if (biome == Biome.DESERT) {
            biomeType = BiomeType.Desert;
        } else if (biome == Biome.FROZEN_OCEAN) {
            biomeType = BiomeType.Snow;
        } else {
            biomeType = BiomeType.Green;
        }

        if (Y < 63) {
            for (int i = 62; i > Y; i--)
                chunk.setBlock(X, i, Z, Material.STATIONARY_WATER);

            chunk.setBlock(X, 63, Z, biomeType == BiomeType.Snow ? Material.ICE : Material.STATIONARY_WATER);

            float noise1 = alternateMaterialNoise.GetNoise(chunkX * 16 + X, chunkZ * 16 + Z);

            chunk.setBlock(X, Y, Z, noise1 > 0.4 ? Material.GRAVEL : Material.DIRT);
            chunk.setBlock(X, Y-1, Z, noise1 > 0.5 ? Material.GRAVEL : Material.DIRT);
            chunk.setBlock(X, Y-2, Z, noise1 > 0.6 ? Material.GRAVEL : Material.DIRT);
            chunk.setBlock(X, Y-3, Z, noise1 > 0.7 ? Material.GRAVEL : Material.DIRT);
            for (int i = Y-4; i > 0; i--)
                chunk.setBlock(X, i, Z, Material.STONE);
            chunk.setBlock(X, 0, Z, Material.BEDROCK);
        } else {
            switch (biomeType) {
                case Green:
                    chunk.setBlock(X, Y, Z, Material.GRASS);
                    chunk.setBlock(X, Y-1, Z, Material.DIRT);
                    chunk.setBlock(X, Y-2, Z, Material.DIRT);
                    chunk.setBlock(X, Y-3, Z, Material.DIRT);
                    for (int i = Y-4; i > 0; i--)
                        chunk.setBlock(X, i, Z, Material.STONE);
                    chunk.setBlock(X, 0, Z, Material.BEDROCK);
                    break;
                case Desert:
                    chunk.setBlock(X, Y, Z, Material.SAND);
                    chunk.setBlock(X, Y-1, Z, Material.SAND);
                    chunk.setBlock(X, Y-2, Z, Material.SAND);
                    chunk.setBlock(X, Y-3, Z, Material.SANDSTONE);
                    chunk.setBlock(X, Y-4, Z, Material.SANDSTONE);
                    chunk.setBlock(X, Y-5, Z, Material.SANDSTONE);
                    for (int i = Y-6; i > 0; i--)
                        chunk.setBlock(X, i, Z, Material.STONE);
                    chunk.setBlock(X, 0, Z, Material.BEDROCK);
                    break;
            }
        }
        return chunk;
    }
}
