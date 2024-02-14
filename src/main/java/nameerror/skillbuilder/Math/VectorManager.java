package nameerror.skillbuilder.Math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorManager {
    public static Vector rotateDCWAboutVector(Vector vector, Vector vectorAxis, float deg) {
        // Rodrigues' rotation formula
        double cosValue = Math.cos(Math.toRadians(deg));
        double sinValue = Math.sin(Math.toRadians(deg));

        Vector vector1  = vector.clone().multiply(cosValue);
        Vector vector2  = vectorAxis.clone().crossProduct(vector).multiply(sinValue);

        Vector vector3  = vectorAxis.clone().multiply(vectorAxis.clone().dot(vector) * (1-cosValue));
        return vector1.add(vector2).add(vector3);
    }

    // Offset Location by vector
    public static Location translateLocationVectorReference(Location location , double left, double up, double front) {
        Vector vec = location.getDirection();
        Vector leftByRef = getLeftVectorByReference(vec);
        Vector upByRef   = getAboveVectorByReference(vec);

        location.clone().add(vec.normalize().multiply(front)).add(leftByRef.multiply(left)).add(upByRef.multiply(up));

        return location;
    }

    // TODO; refactor

    public static Vector getLeftVectorByReference(Location loc) {
        float yaw = loc.getYaw() - 90f;
        double yawRad = yaw * (Math.PI / 180d);
        double z = Math.cos(yawRad);
        double x = -Math.sin(yawRad); // because 90 degrees is -X?!?!!? notch why.
        return new Vector(x, 0d, z);
    }

    public static Vector getAboveVectorByReference(Location loc) {
        return loc.getDirection().crossProduct(getLeftVectorByReference(loc));
    }

    @Deprecated
    public static Vector getLeftVectorByReference(Vector vector) {
        Vector vec = new Vector(0,1,0);
        if (vector.equals(vec)) {
            return new Vector(0,0,1);
        }

        vec.crossProduct(vector);
        if (!vec.equals(new Vector(0,0,0))) {
            vec.normalize();
        }
        return vec;
    }
    @Deprecated
    public static Vector getRightVectorByReference(Vector vector) {
        return getLeftVectorByReference(vector).multiply(-1);
    }

    public static Vector getAboveVectorByReference(Vector vector) {
        Vector vec = vector.clone().crossProduct(getLeftVectorByReference(vector));
        if (!vec.equals(new Vector(0, 0, 0))) {
            vec.normalize();
        }
        return vec;
    }

    public static Vector getDownwardVectorByReference(Vector vector) {
        return getAboveVectorByReference(vector).multiply(-1);
    }
}
