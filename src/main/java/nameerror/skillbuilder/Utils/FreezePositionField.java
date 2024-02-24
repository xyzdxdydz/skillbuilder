package nameerror.skillbuilder.Utils;

import nameerror.skillbuilder.Fundamental.ObjectManagement.Field;
import nameerror.skillbuilder.Fundamental.ObjectManagement.LegacyEntity;
import nameerror.skillbuilder.Math.SetSpace;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class FreezePositionField extends Field {

    public FreezePositionField(SetSpace setSpace) {
        super(setSpace.getLocation(), setSpace);
    }

    @Override
    public boolean teleport(Location location) {
        super.teleport(location);
        super.setSpace.setLocation(location);
        return true;
    }

    @Override
    public void applyToEntity(Entity entity) {
        TrackedMatter tm = MovementTrackingHandler.attachTracker(LegacyEntity.get(entity));
        tm.setLocationOffset(entity.getLocation().toVector().subtract(setSpace.getLocation().toVector())); // left, above, front
        MovementTrackingHandler.register(this, tm);
    }
}
