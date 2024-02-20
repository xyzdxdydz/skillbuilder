package nameerror.skillbuilder.Utils;

import nameerror.skillbuilder.Fundamental.LegacyEntity;
import nameerror.skillbuilder.Fundamental.Matter;
import nameerror.skillbuilder.Fundamental.ObjectManagement.Field;
import nameerror.skillbuilder.Math.VectorManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class TrackedMatter {
    private final Matter matter;
    private Vector locOffset = new Vector(0, 0, 0); //left, above, front if in rotation mode
    private float pitchOffset;
    private float yawOffset;

    private String axisMode = "global";
    private boolean useLocOffset = false;
    private boolean followRotation = false;
    private boolean forceDirection = false;

    private boolean enable = true;
    private boolean isExpired = false;

    public TrackedMatter(Matter matter) {
        this.matter = matter;
    }

    public void setLocationOffset(Vector locOffset) {
        this.locOffset = locOffset;
    }

    public void setRotationOffset(float pitch, float yaw) {
        this.pitchOffset = pitch;
        this.yawOffset = yaw;
    }

    public void setAxisMode(String mode) {
        this.axisMode = mode;
    }

    private Location getNewLocation(Location slaveLocation, Location masterLocation) {
        Location newSlaveLocation = masterLocation.clone();
        float prev_pitch = slaveLocation.getPitch();
        float prev_yaw = slaveLocation.getYaw();

        switch (this.axisMode) {
            case ("local"):
                Vector front_basis = newSlaveLocation.getDirection();
                Vector above_basis = VectorManager.getAboveVectorByReference(newSlaveLocation);
                Vector left_basis  = VectorManager.getLeftVectorByReference(newSlaveLocation);

                Vector newOffset = new Vector(0,0,0);
                newOffset.add( left_basis.multiply(locOffset.getX()));
                newOffset.add(above_basis.multiply(locOffset.getY()));
                newOffset.add(front_basis.multiply(locOffset.getZ()));
                newSlaveLocation.add(newOffset);
                break;

            case ("global"):
                newSlaveLocation.add(this.locOffset);
                break;
        }

        if (!this.forceDirection) {
            newSlaveLocation.setPitch(prev_pitch);
            newSlaveLocation.setYaw(prev_yaw);
        }
        // TODO; sight direction point to master's target;
        // TODO; add custom behavior (callback function based).

        return newSlaveLocation;
    }

    public Matter getMatter() {
        return matter;
    }

    public void update(Location masterLocation) {
        if (this.enable) {
            Location newLocation = getNewLocation(matter.getLocation(), masterLocation);

            if (matter instanceof LegacyEntity) {
                Entity entity = ((LegacyEntity) matter).getEntity();
                entity.setVelocity(new Vector(0, 0, 0)); // prevent fall damage

            } else if (matter instanceof Field) { }

            matter.teleport(newLocation);
        }
    }

//           if (followRotation && !this.trackHead) {
//        Bukkit.broadcastMessage(Verbose.featureWarnning("MovementTrack",
//                "head location is set to false, follow rotation was ignored."));
//    } else {
//        this.followRotation = followRotation;
//    }
}
