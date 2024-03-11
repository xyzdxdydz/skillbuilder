package nameerror.skillbuilder.Fundamental.ObjectManagement.Projectile;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public abstract class SBProjectile extends AbstractProjectile {
    // custom projectile

    private Vector velocity; // no gravity
    private double k = 1; // decay rate (drag)
    private long lastPropelledTick = 0;

    private boolean isDead = false;

    public SBProjectile(Location location) {
        super(location);
    }

    @Override
    public boolean isDead() { return this.isDead; }

    @Override
    public void remove() {
        this.isDead = true;
    }

    @Override
    public void setVelocity(Vector v) {
        this.velocity = v;
        lastPropelledTick = 0;
    }

    @Override
    public Vector getVelocity() {
        return velocity.clone();
    }

    @Override
    public void step() {
        if (lastPropelledTick > 0)
            velocity.multiply(k);
        lastPropelledTick++;

        onFly();

        Location newLocation = getLocation().add(velocity.clone().multiply(1.0/20.0));
        newLocation.getWorld().spawnParticle(Particle.CHERRY_LEAVES, newLocation, 1);
        this.teleport(newLocation);
    }

    @Override
    public boolean teleport(Location location) {
        return super.teleport(location);
    }

}
