package eu.caec.freakgenerator;

import eu.caec.freakgenerator.populators.SugarCanePopulator;
import eu.caec.freakgenerator.populators.ToppingPopulator;
import eu.caec.freakgenerator.populators.TreePopulator;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SurfacePopulator extends BlockPopulator {
    TreePopulator treePopulator = new TreePopulator();
    ToppingPopulator toppingPopulator = new ToppingPopulator();
    SugarCanePopulator sugarCanePopulator = new SugarCanePopulator();

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        Biome b = world.getBiome( chunk.getX()*16 + ThreadLocalRandom.current().nextInt(15),
                chunk.getZ()*16 + ThreadLocalRandom.current().nextInt(15) );

        switch (b) {
            case FOREST:
                treePopulator.populate(world, chunk, random, TreeType.TREE);
                break;
            case BIRCH_FOREST:
                if (random.nextBoolean()) {
                    treePopulator.populate(world, chunk, random, TreeType.TALL_BIRCH);
                } else {
                    treePopulator.populate(world, chunk, random, TreeType.BIRCH);
                }
                break;
            case ICE_PLAINS:
                treePopulator.populate(world, chunk, random, TreeType.REDWOOD);
                break;
            case JUNGLE:
                if (random.nextBoolean()) {
                    treePopulator.populate(world, chunk, random, TreeType.JUNGLE);
                } else {
                    treePopulator.populate(world, chunk, random, TreeType.SMALL_JUNGLE);
                }
                break;
            case SWAMPLAND:
                treePopulator.populate(world, chunk, random, TreeType.SWAMP);
                break;
            case SAVANNA:
                treePopulator.populate(world, chunk, random, TreeType.ACACIA, 1, 2);
        }

        toppingPopulator.populate(world, chunk, random);
        sugarCanePopulator.populate(world, chunk, random);
    }
}
