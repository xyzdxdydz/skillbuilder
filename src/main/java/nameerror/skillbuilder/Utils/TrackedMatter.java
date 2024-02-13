package nameerror.skillbuilder.Utils;

import nameerror.skillbuilder.Math.VectorManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class TrackedMatter {
    private final Entity matter;
    private Vector locOffset = new Vector(0, 0, 0); //left, above, front if in rotation mode
    private float pitchOffset;
    private float yawOffset;

    private String axisMode = "global";
    private boolean useLocOffset = false;
    private boolean followRotation = false;
    private boolean lockDirection = false;

    private boolean enable = true;
    private boolean isExpired = false;

    public TrackedMatter(Entity matter) {
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

    public void update(Location newLocation) {
        if (this.enable) {
            if (matter instanceof Entity) {
                float prev_pitch = matter.getLocation().getPitch();
                float prev_yaw = matter.getLocation().getYaw();
                matter.setVelocity(new Vector(0, 0, 0)); // prevent fall damage
                newLocation = newLocation.clone();

                switch (this.axisMode) {
                    case ("local"):
                        Vector front_basis = newLocation.getDirection();
                        Vector above_basis = VectorManager.getAboveVectorByReference(newLocation);
                        Vector left_basis  = VectorManager.getLeftVectorByReference(newLocation);

                        Vector newOffset = new Vector(0,0,0);
                        newOffset.add( left_basis.multiply(locOffset.getX()));
                        newOffset.add(above_basis.multiply(locOffset.getY()));
                        newOffset.add(front_basis.multiply(locOffset.getZ()));
                        newLocation.add(newOffset);
                        break;

                    case ("global"):
                        newLocation.add(this.locOffset);
                        break;
                }

                if (!this.lockDirection) {
                    newLocation.setYaw(prev_yaw);
                    newLocation.setPitch(prev_yaw);
                }

                // TODO; sight direction point to master's target;
                // TODO; add custom behavior (callback function based).
                matter.teleport(newLocation);
            }
//        TODO; custom entity
//        else
        }
    }

//           if (followRotation && !this.trackHead) {
//        Bukkit.broadcastMessage(Verbose.featureWarnning("MovementTrack",
//                "head location is set to false, follow rotation was ignored."));
//    } else {
//        this.followRotation = followRotation;
//    }
}
