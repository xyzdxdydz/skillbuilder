package nameerror.skillbuilder.OldCode.CustomObjectType;

import nameerror.skillbuilder.Animations.CustomParticle.BasicShape;
import nameerror.skillbuilder.Animations.CustomParticle.ParticleMaker;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.*;

public class StaticAOE {
    private final Entity caller;
    private final World world;
    private final Location start;
    private final Vector normalDirection;
    private final double range;

    private String shape;

    private final boolean isDestroyBlockOnImpact = false;
    private final Set<Material> blockSelectionSet = new HashSet<>();
    private final boolean isDestroyInSelection = false;

    public StaticAOE(Entity caller, World world, Location start, Vector normalDirection, double range) {
        this.caller = caller;
        this.world = world;
        this.start = start;
        this.normalDirection = normalDirection;
        this.range = range;
    }

    /**
     * Set the clear weather duration.
     *
     * The clear weather ticks determine whether or not the world will be
     * allowed to rain or storm. If clear weather ticks are &gt; 0, the world will
     * not naturally do either until the duration has elapsed.
     *
     * This method is equivalent to calling {@code /weather clear} with a set
     * amount of ticks.
     *
     * @param angle duration in degree
     */

    // เก็ขข้อมูลพวกนี้อย่างไร
    public void spawnConic(double angle, boolean sphereCrop) {
        double searchRange = range;
        // bad algorithm
        Collection<Entity> entities = caller.getWorld().getEntities();
        Vector startVector = start.toVector();

        for (Entity entity : entities) {
            if (!(entity instanceof LivingEntity) || entity.equals(caller) || entity.isDead()) {
                continue;
            }

            Vector startToEntity = entity.getBoundingBox().getCenter().subtract(startVector);

            if (sphereCrop) {
                if (startToEntity.lengthSquared() <= range * range && startToEntity.angle(normalDirection) <= Math.toRadians(angle)) {
                    ((LivingEntity) entity).damage(1);
                }

            } else {
                double parallelDistanceFromStart = startToEntity.dot(normalDirection);
                if (parallelDistanceFromStart >= 0 && parallelDistanceFromStart <= range &&
                        startToEntity.angle(normalDirection) <= Math.toRadians(angle)) {
                    ((LivingEntity) entity).damage(1);
                    ((LivingEntity) entity).setNoDamageTicks(0);
                }
            }
        }

        BasicShape ee = new BasicShape(start);
        ParticleMaker particlePackage4 = new ParticleMaker(Particle.REDSTONE,1, 0f, 0f, 0f, 1,
                new Particle.DustOptions(Color.fromRGB(200, 147, 238), 10f), true);
        ee.setParticle(particlePackage4);
        ee.createHelicalConedCoil(start.clone().add(normalDirection.multiply(range)), Math.tan(Math.toRadians(angle)), 0.1, Math.random() * 359, true, 2);
    }

    public void spawnSphere() {

    }

    public Entity getCaller() {
        return caller;
    }

    public World getWorld() {
        return world;
    }

    public Location getStart() {
        return start;
    }

    public Vector getNormalDirection() {
        return normalDirection;
    }

    public double getRange() {
        return range;
    }

    private void spawn() {

    }
}
