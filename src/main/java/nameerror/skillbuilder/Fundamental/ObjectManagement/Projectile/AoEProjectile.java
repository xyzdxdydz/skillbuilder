package nameerror.skillbuilder.Fundamental.ObjectManagement.Projectile;

import nameerror.skillbuilder.Fundamental.ObjectManagement.Projectile.SBProjectile;
import nameerror.skillbuilder.Math.SetSpace;
import org.bukkit.Location;

public abstract class AoEProjectile extends SBProjectile {
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
    public boolean teleport(Location location) {
        setSpace.setLocation(location);
        return super.teleport(location);
    }
}
