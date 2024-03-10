package nameerror.skillbuilder.Fundamental.ObjectManagement.Explosion;

import nameerror.skillbuilder.Fundamental.Matter;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

public class Explosion extends Matter {
    private Entity owner;
    private float power;

    private boolean breakBlock;
    private boolean setFire;

    public Explosion(Entity owner, Location location, float power, boolean setFire, boolean breakBlock) {
        super(location);
        this.owner = owner;
        this.setFire = setFire;
        this.power = power;
        this.breakBlock = breakBlock;
    }

    public void explode() {
        this.getLocation().getWorld().createExplosion(this.getLocation(), power, setFire, breakBlock, owner);

        if (!breakBlock) {
            float count = (float) Math.sqrt(power);
            this.getLocation().getWorld().spawnParticle(
                    Particle.EXPLOSION_LARGE,
                    this.getLocation(), (int) count,
//                    offset,offset,offset,
                    0,0,0,
                    power,
                    null, true);
        }
    }
}
