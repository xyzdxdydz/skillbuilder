package nameerror.skillbuilder.Math.Shape;

import nameerror.skillbuilder.Math.Formula;
import nameerror.skillbuilder.Math.VectorManager;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Set;

public class Cylinder extends Line implements Shape3D {

    private final double radius;
    private final double yaw;
    private final double pitch;

    private final double sinp;
    private final double cosp;
    private final double sinq;
    private final double cosq;

    public Cylinder(Location location, Vector direction, double radius) {
        super(location, direction);
        this.radius = radius;

        Location converter = location.clone().setDirection(direction);
        this.yaw = converter.getYaw();
        this.pitch = -converter.getPitch();
        this.sinp = Math.sin(Math.toRadians(pitch));
        this.cosp = Math.cos(Math.toRadians(pitch));
        this.sinq = Math.sin(Math.toRadians(yaw));
        this.cosq = Math.cos(Math.toRadians(yaw));
    }

    public double getRadius() {
        return radius;
    }

    public double getYaw() {
        return yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public Set<Double> findX(double givenY, double givenZ) {
        givenY -= super.getLocation().getY();
        givenZ -= super.getLocation().getZ();
        double a = givenZ * sinq;
        double b = givenY * cosp;
        double c = givenZ * cosq;
        double d = sinp * sinq;
        double e = b - c * sinp;
        return Formula.solveQuadratic(Formula.Pow2(cosq) + Formula.Pow2(d),
                2 * a * cosq + 2 * d * e,Formula.Pow2(a) + Formula.Pow2(e) - Formula.Pow2(radius), super.getLocation().getX());
    }

    public Set<Double> findY(double givenX, double givenZ) {
        givenX -= super.getLocation().getX();
        givenZ -= super.getLocation().getZ();
        double a = givenZ * sinq + givenX * cosq;
        double b = sinp * (givenZ * cosq - givenX * sinq);
        return Formula.solveQuadratic(Formula.Pow2(cosp), -2 * cosp * b,
                Formula.Pow2(a) + Formula.Pow2(b) - Formula.Pow2(radius), super.getLocation().getY());
    }

    public Set<Double> findZ(double givenX, double givenY) {
        givenX -= super.getLocation().getX();
        givenY -= super.getLocation().getY();
        double a = givenX * cosq;
        double b = givenY * cosp;
        double c = givenX * sinq;
        double d = -sinp * cosq;
        double e = b + c * sinp;
        return Formula.solveQuadratic(Formula.Pow2(sinq) + Formula.Pow2(d),
                2 * a * sinq + 2 * d * e,Formula.Pow2(a) + Formula.Pow2(e) - Formula.Pow2(radius), super.getLocation().getZ());
    }

    @Override
    public Location getRandomLocation(double rangeFromStart) {
        double randomRange = Math.random() * radius;
        double randomAngle = Math.random() * 360;
        Vector vector = VectorManager.getLeftVectorByReference(getDirection()).rotateAroundAxis(getDirection(), randomAngle);
        return super.getRandomLocation(rangeFromStart).add(vector.multiply(randomRange));
    }
}
