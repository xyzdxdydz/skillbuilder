package nameerror.skillbuilder.Math;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.ArrayList;

public abstract class SetSpace {
    private Location location;

    protected SetSpace(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public boolean setLocation(Location location) {
        this.location = location;
        return true;
    }

//    @Override
//    public abstract ArrayList<Entity> itemInside(ArrayList<Location> locations);
    // TODO; find by bounding box
    // TODO; try to make it generic (if possible)

    public abstract ArrayList<Entity> findEntities(boolean useBoundingBox);
    public abstract ArrayList<Block> findBlocks(boolean useBoundingBox);
}
