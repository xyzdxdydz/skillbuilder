package nameerror.skillbuilder.Math.Shape;

import nameerror.skillbuilder.Math.SetSpace;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.ArrayList;

public class WholeWorld extends SetSpace {
    public WholeWorld(Location origin) {
        super(origin);
    }

    @Override
    public ArrayList<Entity> findEntities(boolean useBoundingBox) {
        return (ArrayList<Entity>) this.getLocation().getWorld().getEntities();
    }

    @Override
    public ArrayList<Block> findBlocks(boolean useBoundingBox) {
        return null;
    }
}
