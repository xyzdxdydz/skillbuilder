package nameerror.skillbuilder.Fundamental;

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
}
