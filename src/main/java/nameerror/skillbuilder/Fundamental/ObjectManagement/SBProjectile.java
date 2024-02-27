package nameerror.skillbuilder.Fundamental.ObjectManagement;

import nameerror.skillbuilder.Fundamental.Matter;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public abstract class SBProjectile extends Matter {
    // consider read this

    Vector velocity; // no gravity
    double k = 1; // decay rate (drag)
    long lastPropelledTick = 0;

    public SBProjectile(Location location) {
        super(location);
    }

    public void setVelocity(Vector v) {
        this.velocity = v;
        lastPropelledTick = 0;
    }

    public Vector getVelocity() {
        return velocity.clone();
    }

    public void step() {
        if (lastPropelledTick > 0)
            velocity.multiply(k);
        lastPropelledTick++;

        onFly();
        Location newLocation = getLocation().add(velocity.clone().multiply(1.0/20.0));
        newLocation.getWorld().spawnParticle(Particle.CHERRY_LEAVES, newLocation, 1);
//        org.bukkit.Particle.REDSTONE, 1, 1, 0, 0, 0, 1, new Particle.DustOptions(Color.RED, 1), true)
        this.teleport(newLocation);
    }

    @Override
    public boolean teleport(Location location) {
        return super.teleport(location);
    }

    public abstract void onLaunch();
    public abstract void onHit();
    public abstract void onFly();

}
