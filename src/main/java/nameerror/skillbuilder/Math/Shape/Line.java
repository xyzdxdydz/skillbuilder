package nameerror.skillbuilder.Math.Shape;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import javax.swing.*;

public class Line implements Shape{
    private final Vector vector;
    private final Location location;

    public Line(Location location, Vector direction) {
        try {
            if (direction.equals(new Vector(0,0,0))) {
                throw new ArithmeticException();
            }
        } catch (ArithmeticException e) {
            JOptionPane.showMessageDialog(null, "Vector is zero!");
        }

        this.vector = direction.normalize();
        this.location = location;
    }

    public Vector getDirection() {
        return vector;
    }

    public Location getLocation() {
        return location;
    }

    public Vector findPosition(double length) {
        return location.toVector().add(vector.clone().multiply(length));
    }

    public Vector findPositionGivenX(double x) {
        double t = (x - location.getX()) / vector.getX();
        return findPosition(t);
    }

    public Vector findPositionGivenY(double y) {
        double t = (y - location.getY()) / vector.getY();
        return findPosition(t);
    }

    public Vector findPositionGivenZ(double z) {
        double t = (z - location.getZ()) / vector.getZ();
        return findPosition(t);
    }

    public boolean isOnLine(Vector position) {
        double tx = (position.getX() - location.getX()) / vector.getX();
        double ty = (position.getY() - location.getY()) / vector.getY();
        double tz = (position.getZ() - location.getZ()) / vector.getZ();
        return  tx == ty && tx == tz;
    }

    public Location getRandomLocation(double rangeFromStart) {
        double randomValue = Math.random() * rangeFromStart;
        return location.clone().add(vector.clone().multiply(randomValue));
    }
}