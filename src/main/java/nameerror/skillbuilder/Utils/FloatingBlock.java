package nameerror.skillbuilder.Utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;

public class FloatingBlock {

    public static FallingBlock makeFloatBlock(Block block, boolean withBlockData) {
        if (!block.getType().equals(Material.AIR)) {
            World world = block.getWorld();
            Location location = block.getLocation();
            BlockData blockData = withBlockData ? block.getBlockData() : block.getType().createBlockData();

            FallingBlock fallingBlock = world.spawnFallingBlock(location, blockData);
            block.setType(Material.AIR);

            return fallingBlock;
        }

        return null;
    }
}
