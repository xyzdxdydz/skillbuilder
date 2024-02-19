package nameerror.skillbuilder.Math.Shape;


import nameerror.skillbuilder.Math.SetSpace;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class Sphere extends SetSpace implements Shape3D, Shape {
    private final double radius;

    public Sphere(Location location, double radius) {
        super(location);
        this.radius = radius;
    }

    @Override
    public Set<Double> findX(double givenY, double givenZ) {
        return null;
    }

    @Override
    public Set<Double> findY(double givenX, double givenZ) {
        return null;
    }

    @Override
    public Set<Double> findZ(double givenX, double givenY) {
        return null;
    }

    public Location getRandomLocation() {
        double randomRange = Math.random() * radius;
        double randomPhi = Math.random() * 180;
        double randomTheta = Math.random() * 360;
        Vector normal = new Vector(0, 0, 1);
        return this.getLocation().clone().add(new Vector(0, 1, 0).multiply(randomRange).rotateAroundAxis(normal, randomPhi)
                .rotateAroundAxis(new Vector(0, 1, 0), randomTheta));
    }

    @Override
    public ArrayList<Entity> findEntities(boolean useBoundingBox) {
        Collection<Entity> entities = this.getLocation().getWorld().getNearbyEntities(this.getLocation(), 2*radius, 2*radius, 2*radius);
        entities.removeIf(e -> this.getLocation().clone().distance(e.getLocation()) > radius);
        return (ArrayList<Entity>) entities;
    }

    @Override
    public ArrayList<Block> findBlocks(boolean useBoundingBox) {
        ArrayList<Block> blockResult = new ArrayList<>();

        for (double x =-radius; x<=radius; x++) {
            for (double y=-radius; y<=radius; y++) {
                for (double z=-radius; z<=radius; z++) {

                    Vector locOffset = new Vector(x, y, z);
                    if (locOffset.distanceSquared(new Vector(0, 0, 0)) <= radius*radius) {
                        Location blockLoc = this.getLocation().clone().add(locOffset);
                        blockResult.add(blockLoc.getBlock());
                    }
                }
            }
        }

        return blockResult;
    }

    // make it to pair of T, location
//    public ArrayList<Entity> itemInside(ArrayList<Location> locations) {
//        Collection<Entity> entities = location.getWorld().getNearbyEntities(location, 2*radius, 2*radius, 2*radius);
//        locations.removeIf(e -> this.getLocation().clone().distance(e) > radius);
//        return (ArrayList<Entity>) entities;
//    }
}
