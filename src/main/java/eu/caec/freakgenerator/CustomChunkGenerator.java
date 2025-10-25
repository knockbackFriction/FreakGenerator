package eu.caec.freakgenerator;

import eu.caec.freakgenerator.noise.FastNoiseLite;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CustomChunkGenerator extends ChunkGenerator {
    FileConfiguration config = Main.instance.getConfig();
    int seed = config.getInt("seed");

    private final FastNoiseLite terrainNoise = new FastNoiseLite(seed);

    public CustomChunkGenerator() {
        // Set frequencies, lower frequency = slower change.
        terrainNoise.SetFrequency(0.001f);
        terrainNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
        terrainNoise.SetFractalOctaves(8);
    }

    int currentHeight = 46;
    PillarPlacer pillarPlacer = new PillarPlacer();
    BiomeManager biomeManager = new BiomeManager(seed);

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);

        Biome pillarBiome = null;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                float noise1 = terrainNoise.GetNoise(chunkX * 16 + x, chunkZ * 16 + z);
                currentHeight = 63 + (int)(noise1 * 63);
                pillarBiome = biomeManager.getBiome(x, currentHeight, z, chunkX, chunkZ);
                chunk = pillarPlacer.placePillars(x, currentHeight, z, chunk, pillarBiome);
                biome.setBiome(x, z, pillarBiome);
            }
        }
        return chunk;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList(new SurfacePopulator());
    }
}