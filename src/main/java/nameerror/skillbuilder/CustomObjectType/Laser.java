package nameerror.skillbuilder.CustomObjectType;

import javafx.util.Pair;
import nameerror.skillbuilder.Animations.CustomParticle.BasicShape;
import nameerror.skillbuilder.Animations.CustomParticle.ParticleMaker;
import nameerror.skillbuilder.Math.Shape.Cylinder;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Fundamental.SkillManagement.TargetSelector.Search;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

import static java.lang.Math.min;

public class Laser {
    private BukkitTask task;

    private final Entity caller;
    private final World world;
    private Location start;
    private Vector direction;
    private final double range;
    private final double thickness;
    private final long duration;
    private final int refreshTick;

    // Laser should go for one time
    private boolean followCaller = false;
    private boolean followCallerDirection = false;

    private int piercingLevel = 0; // This will pierce entity and blocks
    private int maxEntityPiercing = 0; // Laser will stop when hit i + 1 entity
    private int maxBlockPiercing = 0;  // Laser will stop when hit i + 1 blocks
    private boolean isDestroyBlockOnImpact = false;
    private Set<Material> blockIgnoreSet = new HashSet<>();
    private Set<Material> blockSelectionSet = new HashSet<>();
    private boolean isDestroyInSelection = false;

    // coming soon mobPiercingCount Radius
    // Coming soon destroy Projectile or non-living entities

    public Laser(Entity caller, Location start, Vector direction, double range, double thickness, long duration, int refreshTick) {
        this.caller = caller;
        this.world = caller.getWorld();
        this.start = start;
        this.direction = direction;
        this.range = range;
        this.thickness = thickness;
        this.duration = duration;
        this.refreshTick = refreshTick;
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

    public Vector getDirection() {
        return direction;
    }

    public double getRange() {
        return range;
    }

    public double getThickness() {
        return thickness;
    }

    public long getDuration() {
        return duration;
    }

    public int getRefreshTick() {
        return refreshTick;
    }

    public boolean isFollowCaller() {return followCaller;}

    public boolean isFollowCallerDirection() {return followCallerDirection;}

    public int getPiercingLevel() {
        return piercingLevel;
    }

    public int getMaxEntityPiercing() {
        return maxEntityPiercing;
    }

    public int getMaxBlockPiercing() {
        return maxBlockPiercing;
    }

    public boolean isDestroyBlockOnImpact() {
        return isDestroyBlockOnImpact;
    }

    public Set<Material> getBlockIgnoreSet() {
        return blockIgnoreSet;
    }

    public Set<Material> getBlockSelectionSet() {
        return blockSelectionSet;
    }

    public boolean isDestroyInSelection() {
        return isDestroyInSelection;
    }

    public void setStartLocation(Location location) {
        this.start = location;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public void setPiercing(int piercingLevel, int maxEntityPiercing, int maxBlockPiercing) {
        this.piercingLevel = piercingLevel < 0 ? Integer.MAX_VALUE : piercingLevel;
        this.maxEntityPiercing = maxEntityPiercing < 0 ? Integer.MAX_VALUE : maxEntityPiercing;
        this.maxBlockPiercing = maxBlockPiercing < 0 ? Integer.MAX_VALUE : maxBlockPiercing;
    }

    public void setFollowLocation(boolean followCallerLocation) {
        this.followCaller = followCallerLocation;
    }

    public void setFollowDirection(boolean followCallerDirection) {
        this.followCallerDirection = followCallerDirection;
    }

    private void setDestroyBlockOnImpact(boolean destroyBlockOnImpact) {
        this.isDestroyBlockOnImpact = destroyBlockOnImpact;
    }

    public void setBlockIgnoreSet(Set<Material> blockIgnoreSet) {
        this.blockIgnoreSet = blockIgnoreSet;
    }

    public void setBlockSelectionSet(Set<Material> blockSelectionSet, boolean destroyInSelection) {
        this.blockSelectionSet = blockSelectionSet;
        this.isDestroyInSelection = destroyInSelection;
    }

    public void redirect(Vector vector) {
        this.direction = vector;
    }

    public void cancel() {
        this.task.cancel();
    }

    public void activate() {
        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                if (followCaller) {
                    setStartLocation(((LivingEntity) getCaller()).getEyeLocation());
                }
                if (followCallerDirection) {
                    setDirection(getCaller().getLocation().getDirection());
                }
                spawnLaserPrecise();
            }
        }.runTaskTimer(SkillBuilder.getPlugin(), refreshTick, 0);
        Bukkit.getScheduler().runTaskLater(SkillBuilder.getPlugin(), this::cancel, duration);
    }

    // wall logic not included
    private void spawnLaserPrecise() {
        if (!direction.equals(new Vector(0, 0, 0))) {
            direction.normalize();

        } else {
            return;
        }

        BasicShape ee = new BasicShape(start);
        ParticleMaker particlePackage4 = new ParticleMaker(Particle.REDSTONE,1, 0f, 0f, 0f, 1,
                new Particle.DustOptions(Color.fromRGB(200, 147, 238), 0.5f), true);
        ee.setParticle(particlePackage4);
        ee.createLine(start.clone().add(direction.clone().multiply(range)), 8);

        List<Entity> entities = caller.getWorld().getEntities();
        Cylinder cylinder = new Cylinder(start, direction, thickness / 2);

        TreeMap<Double, List<Entity>> entitySort = Search.entityInCylinder(entities, cylinder, range, true, false);
        List<Pair<Double, Entity>> targets = Search.treeMapAsDistantEntityPairList(entitySort);
        targets.removeIf(target -> (!(target.getValue() instanceof LivingEntity) || (target.getValue()).isDead() ||
                target.getValue().equals(caller)));

        int limit =  min(targets.size() - 1, min(piercingLevel, maxEntityPiercing));
        for (int i = 0; i <= limit; i++) {
            LivingEntity e = (LivingEntity) targets.get(i).getValue();
            e.damage(1);
            e.setNoDamageTicks(0);
        }
    }

    private void spawnLaser() {
        if (!direction.equals(new Vector(0, 0, 0))) {
            direction.normalize();

        } else {
            return;
        }

        double intervalDistance = thickness / 2;

        Set<Entity> entityVisited = new HashSet<>();
        TreeMap<Double, List<LivingEntity>> entitySort = new TreeMap<>();

        Vector adderVector = direction.clone().multiply(intervalDistance);
        Location it = start.clone();
        double searchRange = thickness / 2;

        for (double i = 0; i <= range; i += intervalDistance) {

            Collection<Entity> entities = it.getWorld().getNearbyEntities(it, searchRange, searchRange, searchRange);

            // Searching Entity
            for (Entity entity : entities) {
                if (!(entity instanceof LivingEntity) || entity.equals(caller)
                        || entity.isDead() || entityVisited.contains(entity)) {
                    continue;
                }

                // Target Hit algorithm;
                Vector itToEntity = entity.getBoundingBox().getCenter().subtract(it.toVector());
                double parallelDistance = itToEntity.dot(direction);
                double parallelDistanceSquared = parallelDistance * parallelDistance;
                double perpendicularDistanceSquared = itToEntity.distanceSquared(new Vector(0,0,0)) - parallelDistanceSquared;
                Vector perpendicularVectorByMaxThickness = itToEntity.clone().subtract(direction.clone().
                        multiply(parallelDistance)).normalize().multiply(thickness / 2.0);

                Location inHitBoxCheck = it.clone().add(direction.clone().multiply(parallelDistance)).add(perpendicularVectorByMaxThickness);
                it.getWorld().spawnParticle(Particle.REDSTONE, inHitBoxCheck,1, 0, 0, 0, 1,
                        new Particle.DustOptions(Color.RED, 1),true);

                if (perpendicularDistanceSquared <= thickness * thickness / 4 || entity.getBoundingBox().contains(it.toVector())
                        || entity.getBoundingBox().contains(inHitBoxCheck.toVector())) {
                    Vector startToEntity = entity.getBoundingBox().getCenter().subtract(start.toVector());
                    double parallelDistanceFromStart = startToEntity.dot(direction);

                    if (parallelDistanceFromStart >= 0 && parallelDistanceFromStart <= range) {
                        if (!entitySort.containsKey(parallelDistanceFromStart)) {
                            List<LivingEntity> newList = new ArrayList<LivingEntity>();
                            newList.add((LivingEntity) entity);
                            entitySort.put(parallelDistanceFromStart, newList);

                        } else {
                            entitySort.get(parallelDistanceFromStart).add((LivingEntity) entity);
                        }
                    }

                    entityVisited.add(entity);
                }
            }

            // debug
            it.getWorld().spawnParticle(Particle.REDSTONE, it,1, 0, 0, 0, 1,
                    new Particle.DustOptions(Color.BLACK, 1),true);
            it.add(adderVector);
        }

        // damage entity with piercing level
        for (int i = 0; i <= maxEntityPiercing && i <= piercingLevel;) {
            if (entitySort.isEmpty()) {
                break;
            }
            List<LivingEntity> entityList = entitySort.firstEntry().getValue();
            for (LivingEntity e : entityList) {
                // damage not included
                e.damage(1);
                e.setNoDamageTicks(0);
                i++;
                if (i > maxEntityPiercing || i > piercingLevel) {
                    break;
                }
            }
            entitySort.remove(entitySort.firstKey());
        }
    }
}