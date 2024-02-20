package nameerror.skillbuilder.Fundamental;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class LegacyEntity extends Matter {
    private Entity entity;

    /*
        Wrapper class of entity;
     */
    public LegacyEntity(Entity entity) {
        super(entity.getLocation());
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public Location getLocation() { // they have own decision in each tick, use this is more suitable.
        return entity.getLocation();
    }

    @Override
    public boolean teleport(Location location) {
        super.teleport(location);
        entity.teleport(location);
        // TODO; Implement fail teleport.
        return true; // teleport successful
    }
}
