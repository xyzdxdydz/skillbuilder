package nameerror.skillbuilder.Utils;

import nameerror.skillbuilder.Math.SetSpace;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.function.Function;

public class AccelerationField {
    private final Function<Double, Double> F_x;
    private final Function<Double, Double> F_y;
    private final Function<Double, Double> F_z;

    public AccelerationField(
            Function<Double, Double> vecFuncX,
            Function<Double, Double> vecFuncY,
            Function<Double, Double> vecFuncZ) {
        F_x = vecFuncX;
        F_y = vecFuncY;
        F_z = vecFuncZ;
    }

    public void apply(SetSpace setSpace) {
        ArrayList<Entity> entities = setSpace.findEntities(false);

        for (Entity e: entities) {
            Location location = e.getLocation().clone();
            Vector velocity = e.getVelocity();
            Vector delta = new Vector(
                    F_x.apply(location.getX()),
                    F_y.apply(location.getY()),
                    F_z.apply(location.getZ()));
            velocity.add(delta.multiply(0.04f));

            e.setVelocity(velocity);
        }
    }
}
