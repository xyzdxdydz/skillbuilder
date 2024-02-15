package nameerror.skillbuilder.Utils;

import nameerror.skillbuilder.Math.SetSpace;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.function.Function;

public class AccelerationField {
    private final Function<Vector, Vector> F;

    public AccelerationField(Function<Vector, Vector> vecFunc) {
        F = vecFunc;
    }

    public void apply(SetSpace setSpace) {
        ArrayList<Entity> entities = setSpace.findEntities(false);

        for (Entity e: entities) {
            Location location = e.getLocation().clone();
            Vector velocity = e.getVelocity();
            Vector delta = F.apply(location.toVector());
            velocity.add(delta.multiply(0.04f));

            e.setVelocity(velocity);
        }
    }
}
