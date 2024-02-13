package nameerror.skillbuilder.Math.Shape;


import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Set;

public class Sphere implements Shape3D, Shape {
    private final Location location;
    private final double radius;

    public Sphere(Location location, double radius) {
        this.location = location;
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
        return location.clone().add(new Vector(0, 1, 0).multiply(randomRange).rotateAroundAxis(normal, randomPhi)
                .rotateAroundAxis(new Vector(0, 1, 0), randomTheta));
    }
}
