package eu.caec.freakgenerator;

import eu.caec.freakgenerator.noise.FastNoiseLite;
import org.bukkit.block.Biome;

public class BiomeManager {
    private final FastNoiseLite temperatureNoise;
    private final FastNoiseLite humidityNoise;

    public BiomeManager(int seed){
        temperatureNoise = new FastNoiseLite(seed * 34501);
        temperatureNoise.SetFrequency(0.002f);
        temperatureNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
        temperatureNoise.SetFractalOctaves(4);

        humidityNoise = new FastNoiseLite(seed * 48611);
        humidityNoise.SetFrequency(0.002f);
        humidityNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
        humidityNoise.SetFractalOctaves(4);
    }

    public Biome getBiome(int X, int Y, int Z, int chunkX, int chunkZ) {
        float temperature = temperatureNoise.GetNoise(X + (chunkX*16), Z + (chunkZ*16));
        float humidity = humidityNoise.GetNoise(X + (chunkX*16), Z + (chunkZ*16));

        if (Y > 62) {
            if (temperature > 0.6f) {
                return Biome.DESERT;
            } else if (temperature > 0.2f) {
                if (humidity > 0.0f) {
                    return (Y < 68) ? Biome.SWAMPLAND : Biome.JUNGLE;
                } else {
                    return Biome.SAVANNA;
                }
            } else if (temperature > -0.2f) {
                return (humidity > 0.0f) ? Biome.FOREST : Biome.PLAINS;
            } else if (temperature > -0.6f) {
                return (humidity > 0.0f) ? Biome.BIRCH_FOREST : Biome.PLAINS;
            } else {
                return Biome.ICE_PLAINS;
            }
        } else {
            if (humidity > 0.0f && temperature < 0.6f && temperature > 0.2f && Y > 56) {
                return Biome.SWAMPLAND;
            } else {
                return temperature > -0.68f ? Biome.OCEAN : Biome.FROZEN_OCEAN;
            }
        }
    }
}
