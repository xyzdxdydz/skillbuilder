package nameerror.skillbuilder.Utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;

public class FloatingBlock {

    public FallingBlock makeFloatBlock(Block block) {
        World world = block.getWorld();
        Location location = block.getLocation();
        FallingBlock sandEntity = world.spawnFallingBlock(location, block.getType().createBlockData());
        return sandEntity;
    }
}
