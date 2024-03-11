package nameerror.skillbuilder.Fundamental.ObjectManagement.Projectile;

import nameerror.skillbuilder.Fundamental.Matter;
import org.bukkit.Location;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public abstract class AbstractProjectile extends Matter {
    public AbstractProjectile(Location location) {
        super(location);
    }

    public abstract ProjectileSource getShooter();

    public abstract void setShooter(ProjectileSource source);

    public abstract boolean isDead();

    public abstract void remove();

    public abstract void step();

    public abstract Vector getVelocity();

    public abstract void setVelocity(Vector velocity);

    public abstract void onLaunch();

    public abstract void onHit(ProjectileHitInfo hitInfo);

    public abstract void onFly();

    public abstract void onDead();
}
