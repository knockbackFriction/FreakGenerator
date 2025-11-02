package eu.caec.freakgenerator;

import eu.caec.freakgenerator.noise.FastNoiseLite;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class CaveDigger extends BlockPopulator { //i was just testing, i will never actually use this
    private final FastNoiseLite noiseH;
    private final FastNoiseLite noiseY;

    public CaveDigger(int seed) {
        noiseH = new FastNoiseLite(seed * 10399);
        noiseY = new FastNoiseLite(seed * 81629);
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 80; y++) {
                    float noise1 = noiseH.GetNoise(chunk.getX() * 16 + x, chunk.getZ() * 16 + z);
                    float noise2 = noiseY.GetNoise(chunk.getX() * 16 + x, y);
                    if (noise1 * noise2 > 0.75f) {
                        int[] coords = {chunk.getX() * 16 + x, y, chunk.getZ() * 16 + z};
                        if ( chunk.getBlock(coords[0], coords[1], coords[2]).getType() != Material.WATER ) {
                            chunk.getBlock(coords[0], coords[1], coords[2]).setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }
}
