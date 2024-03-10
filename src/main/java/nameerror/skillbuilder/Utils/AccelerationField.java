package nameerror.skillbuilder.Utils;

import nameerror.skillbuilder.Fundamental.ObjectManagement.Field.Field;
import nameerror.skillbuilder.Math.SetSpace;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

import java.util.function.Function;

public class AccelerationField extends Field {

    private Function<Vector, Vector> F;

    public AccelerationField(SetSpace setSpace) {
        super(setSpace.getLocation(), setSpace);
    }

    public void setFunction(Function<Vector, Vector> posFunction) {
        this.F = posFunction;
    }

    @Override
    public boolean teleport(Location location) {
        super.teleport(location);
        super.setSpace.setLocation(location);
        return true;
    }

    @Override
    public void applyToEntity(Entity entity) {
        Location location = entity.getLocation().clone();
        Vector velocity = entity.getVelocity();
        Vector delta = F.apply(location.toVector());
        velocity.add(delta.multiply(0.04f));

        entity.setVelocity(velocity);
    }

    @Override
    public void applyToBlock(Block block) {
        FallingBlock fb = FloatingBlock.makeFloatBlock(block, true);
//            fb.setGravity(false);
    }
}
