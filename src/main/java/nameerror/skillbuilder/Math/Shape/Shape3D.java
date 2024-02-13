package nameerror.skillbuilder.Math.Shape;

import java.util.Set;

public interface Shape3D {

    Set<Double> findX(double givenY, double givenZ);

    Set<Double> findY(double givenX, double givenZ);

    Set<Double> findZ(double givenX, double givenY);
}
