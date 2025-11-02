package eu.caec.freakgenerator;

import eu.caec.freakgenerator.noise.FastNoiseLite;
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
    private final FastNoiseLite detailNoise = new FastNoiseLite(seed * 5393);

    public CustomChunkGenerator() {
        // Set frequencies, lower frequency = slower change.
        terrainNoise.SetFrequency(0.001f);
        terrainNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
        terrainNoise.SetFractalOctaves(7);

        detailNoise.SetFrequency(0.0035f);
        detailNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
        detailNoise.SetFractalOctaves(4);
    }

    int currentHeight = 46;
    PillarPlacer pillarPlacer = new PillarPlacer(seed * 86573);
    BiomeManager biomeManager = new BiomeManager(seed * 294001);

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);

        Biome pillarBiome;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                float noise1 = terrainNoise.GetNoise(chunkX * 16 + x, chunkZ * 16 + z);
                float noise2 = detailNoise.GetNoise(chunkX * 16 + x, chunkZ * 16 + z);
                currentHeight = 63 + (int)(noise1 * 55);

                if (noise2 > 0.3f) {
                    currentHeight += (int) ( 63 * (noise2-0.3f) );
                }

                pillarBiome = biomeManager.getBiome(x, currentHeight, z, chunkX, chunkZ);
                biome.setBiome(x, z, pillarBiome);
                chunk = pillarPlacer.placePillars(x, currentHeight, z, chunk, pillarBiome, chunkX, chunkZ);
            }
        }
        return chunk;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList(new SurfacePopulator()/*, new CaveDigger(seed)*/);
    }
}
