package nameerror.skillbuilder.Fundamental.ObjectManagement.Projectile;

import org.bukkit.Location;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public abstract class RegularProjectile extends AbstractProjectile {
    private Projectile projectile;

    public RegularProjectile(Projectile projectile) {
        super(projectile.getLocation());
        this.projectile = projectile;
    }

    public void setDead(boolean b) {
        this.projectile.remove();
    }

    @Override
    public ProjectileSource getShooter() {
        return this.projectile.getShooter();
    }

    @Override
    public void setShooter(ProjectileSource source) {
        this.projectile.setShooter(source);
    }

    @Override
    public boolean isDead() { return this.projectile.isDead(); }

    @Override
    public void remove() {
        this.projectile.remove();
    }

    public Projectile getProjectile() {
        return projectile;
    }

    @Override
    public Location getLocation() { // they have own decision in each tick, use this is more suitable.
        return projectile.getLocation();
    }

    @Override
    public boolean teleport(Location location) {
        super.teleport(location);
        projectile.teleport(location);
        // TODO; Implement when teleport failed.
        return true; // teleport successful
    }

    @Override
    public Vector getVelocity() {
        return this.projectile.getVelocity();
    }

    @Override
    public void setVelocity(Vector velocity) {
        this.projectile.setVelocity(velocity);
    }

    @Override
    public void step() {
        onFly();
    }

    @Override
    public void onLaunch() { }

    @Override
    public void onHit(ProjectileHitInfo hitInfo) { }

    @Override
    public void onFly() { }
}
