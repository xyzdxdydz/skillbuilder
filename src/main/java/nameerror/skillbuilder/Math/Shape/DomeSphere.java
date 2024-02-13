package nameerror.skillbuilder.Math.Shape;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Set;

public class DomeSphere extends Sphere {

    private final Vector normal;

    public DomeSphere(Location location, double radius, Vector normal) {
        super(location, radius);
        this.normal = normal;
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

}
