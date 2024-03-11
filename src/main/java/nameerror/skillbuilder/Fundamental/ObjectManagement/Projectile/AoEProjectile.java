package nameerror.skillbuilder.Fundamental.ObjectManagement.Projectile;

import nameerror.skillbuilder.Math.SetSpace;
import org.bukkit.Location;
import org.bukkit.projectiles.ProjectileSource;

public abstract class AoEProjectile extends SBProjectile {
    private ProjectileSource shooter = null;
    private SetSpace setSpace;

    public AoEProjectile(SetSpace setSpace) {
        super(setSpace.getLocation());
        this.setSpace = setSpace;
    }

    public SetSpace getSetSpace() {
        return this.setSpace;
    }

    public void setSetSpace(SetSpace setSpace) {
        this.setSpace = setSpace;
    }

    @Override
    public ProjectileSource getShooter() {
        return shooter;
    }

    @Override
    public void setShooter(ProjectileSource source) {
        this.shooter = source;
    }

    @Override
    public boolean teleport(Location location) {
        setSpace.setLocation(location);
        return super.teleport(location);
    }
}
