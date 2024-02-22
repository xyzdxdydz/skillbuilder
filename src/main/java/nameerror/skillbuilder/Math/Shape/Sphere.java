package nameerror.skillbuilder.Math.Shape;


import nameerror.skillbuilder.Math.SetSpace;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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

    @Override
    public ArrayList<Block> getBlockOnSurface() {
        ArrayList<Block> result = new ArrayList<>();
        Set<Vector> resHistory = new HashSet<>();

        double lengthStep = 0.35;
        double delta = lengthStep/radius;

        // re align was important
        Location center = this.getLocation().getBlock().getLocation().clone().add(new Vector(0.5, 0.5, 0.5));
        for (double alpha = 0; alpha <= Math.PI; alpha += delta) {
            double y = Math.cos(alpha) * radius;
            double r_y = Math.sin(alpha) * radius;

            for (double theta = 0; theta <= Math.PI * 2; theta += delta) {
                double x = Math.cos(theta) * r_y;
                double z = Math.sin(theta) * r_y;

                Location blockLocation = center.add(x, y, z);
                Vector alignedBlockLoc = blockLocation.getBlock().getLocation().toVector();
                if (!resHistory.contains(alignedBlockLoc)) {
                    resHistory.add(alignedBlockLoc);
                    result.add(blockLocation.getBlock());
                }
                center.subtract(x, y, z);
            }
        }

        return result;
    }

// another version of making hsphere
//        Vector rotationAxis = new Vector(0, 1, 0);
//
//        for (double alpha=0; alpha <= Math.PI; alpha+=delta) {
//            double y = radius * Math.cos(alpha);
//            double circleRadius = Math.sqrt(radius*radius - y*y);
//            Location center = this.getLocation().clone().add(0, y, 0).getBlock().getLocation();
//            Location blockLocation = center.clone().add(circleRadius, 0, 0);
//
//            for (double theta=0; theta<=2*Math.PI; theta+=delta) {
//                Vector alignedBlockLoc = blockLocation.getBlock().getLocation().toVector();
//                if (!resHistory.contains(alignedBlockLoc)) {
//                    resHistory.add(alignedBlockLoc);
//                    result.add(blockLocation.getBlock());
//                }
//
//                Vector dff = blockLocation.toVector().subtract(center.toVector());
//                blockLocation = center.clone().add(
//                        VectorManager.rotateDCWAboutVector(dff, rotationAxis, (float) Math.toDegrees(delta)));
//            }
//        }

    // make it to pair of T, location
//    public ArrayList<Entity> itemInside(ArrayList<Location> locations) {
//        Collection<Entity> entities = location.getWorld().getNearbyEntities(location, 2*radius, 2*radius, 2*radius);
//        locations.removeIf(e -> this.getLocation().clone().distance(e) > radius);
//        return (ArrayList<Entity>) entities;
//    }
}
