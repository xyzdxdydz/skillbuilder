package nameerror.skillbuilder.Fundamental.ObjectManagement.Domain;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DomainCreator {
    private String worldName;

    public DomainCreator(String worldName) {
        this.worldName = worldName;
    }

    public void create() {
        WorldCreator worldCreator = new WorldCreator("domain_infinite_void");
        worldCreator.type(WorldType.NORMAL);
        worldCreator.environment(World.Environment.THE_END);
//        worldCreator.biomeProvider(); // not necessary now
//        worldCreator.generateStructures();
        worldCreator.keepSpawnInMemory(false);

        ChunkGenerator chunkGenerator = new ChunkGenerator() {
            @Override
            public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData) {
                super.generateNoise(worldInfo, random, chunkX, chunkZ, chunkData);
                SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(worldInfo.getSeed()), 6);
                generator.setScale(0.008);

                Material material = Material.AIR;
//                if (worldInfo.getEnvironment() == World.Environment.NORMAL) {
//                    material = Material.STONE;
//                } else {
//                    material = Material.NETHERRACK;
//                }

                int worldX = chunkX * 16;
                int worldZ = chunkZ * 16;

                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        double noise = generator.noise(worldX + x, worldZ + z, 1, 1, true);
                        int height = (int) (noise * 1);
                        height += 0;
                        if (height > chunkData.getMaxHeight()) {
                            height = chunkData.getMaxHeight();
                        }
                        for (int y = 0; y < height; y++) {
                            chunkData.setBlock(x, y, z, material);
                        }
                    }
                }
            }
        };

        worldCreator.generator(chunkGenerator);
        worldCreator.createWorld();
    }
}
