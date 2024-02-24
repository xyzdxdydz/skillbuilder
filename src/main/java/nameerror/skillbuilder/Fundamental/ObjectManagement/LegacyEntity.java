package nameerror.skillbuilder.Fundamental.ObjectManagement;

import nameerror.skillbuilder.Fundamental.Matter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.HashMap;

public class LegacyEntity extends Matter {
    private static HashMap<Entity, LegacyEntity> entityMap = new HashMap<>();
    private Entity entity;

    /*
        Wrapper class of entity;
     */
    private LegacyEntity(Entity entity) {
        super(entity.getLocation());
        this.entity = entity;
    }

    public static LegacyEntity get(Entity entity) {
        if (entityMap.containsKey(entity)) {
            return entityMap.get(entity);

        } else {
            LegacyEntity legacyEntity = new LegacyEntity(entity);
            entityMap.put(entity, legacyEntity);
            return legacyEntity;
        }
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
