package nameerror.skillbuilder.Math;

import nameerror.skillbuilder.Math.Shape.Cylinder;
import nameerror.skillbuilder.Math.Shape.Line;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BoxCheck {

    private final BoundingBox box;
    // Maximum of border consider to be outside, so just add tolerance to max before compute.
    private final double tolerance = 0.00000000001;
    public BoxCheck(BoundingBox boundingBox) {
        this.box = boundingBox;
    }

    public boolean isIntersectWithLine(Line line) {
        return box.contains(line.findPositionGivenX(box.getMinX())) ||
                box.contains(line.findPositionGivenX(box.getMaxX() - tolerance)) ||
                box.contains(line.findPositionGivenY(box.getMinY())) ||
                box.contains(line.findPositionGivenY(box.getMaxY() - tolerance)) ||
                box.contains(line.findPositionGivenZ(box.getMinZ())) ||
                box.contains(line.findPositionGivenZ(box.getMaxZ() - tolerance));
    }

    public boolean isIntersectWithCylinder(Cylinder cylinder, boolean includeInside) {
        Set<Double> ans;

        // X
        for (double x: new double[] {box.getMinX(), box.getMaxX() - tolerance}) {
            ans = cylinder.findY(x, box.getMinZ());
            if (isInBoxFromSet(ans, x, 0, box.getMinZ(), 1, includeInside)) {
                return true;
            }
            ans = cylinder.findY(x, box.getMaxZ() - tolerance);
            if (isInBoxFromSet(ans, x, 0, box.getMaxZ() - tolerance, 1, includeInside)) {
                return true;
            }
            ans = cylinder.findZ(x, box.getMinY());
            if (isInBoxFromSet(ans, x, box.getMinY(), 0, 2, includeInside)) {
                return true;
            }
            ans = cylinder.findZ(x, box.getMaxY() - tolerance);
            if (isInBoxFromSet(ans, x, box.getMaxY() - tolerance, 0, 2, includeInside)) {
                return true;
            }
        }

        // Y
        for (double y: new double[] {box.getMinY(), box.getMaxY() - tolerance}) {
            ans = cylinder.findX(y, box.getMinZ());
            if (isInBoxFromSet(ans, 0, y, box.getMinZ(), 0, includeInside)) {
                return true;
            }
            ans = cylinder.findX(y, box.getMaxZ() - tolerance);
            if (isInBoxFromSet(ans, 0, y, box.getMaxZ() - tolerance, 0, includeInside)) {
                return true;
            }
            ans = cylinder.findZ(box.getMinX(), y);
            if (isInBoxFromSet(ans, box.getMinX(), y, 0, 2, includeInside)) {
                return true;
            }
            ans = cylinder.findZ(box.getMaxX() - tolerance, y);
            if (isInBoxFromSet(ans, box.getMaxX() - tolerance, y, 0, 2, includeInside)) {
                return true;
            }
        }

        // Z
        for (double z: new double[] {box.getMinZ(), box.getMaxZ() - tolerance}) {
            ans = cylinder.findX(box.getMinY(), z);
            if (isInBoxFromSet(ans, 0, box.getMinY(), z, 0, includeInside)) {
                return true;
            }
            ans = cylinder.findX(box.getMaxY() - tolerance, z);
            if (isInBoxFromSet(ans, 0, box.getMaxY() - tolerance, z, 0, includeInside)) {
                return true;
            }
            ans = cylinder.findY(box.getMinX(), z);
            if (isInBoxFromSet(ans, box.getMinX(), 0, z, 1, includeInside)) {
                return true;
            }
            ans = cylinder.findY(box.getMaxX() - tolerance, z);
            if (isInBoxFromSet(ans, box.getMaxX() - tolerance, 0, z, 1, includeInside)) {
                return true;
            }
        }

        return false;
    }

    public boolean isInBoxFromSet(Set<Double> set, double x, double y, double z, int unknown, boolean includeInside) {
        List <Double> values = new ArrayList<>(set);
        switch (unknown) {
            case 0:
                for (double num : set) {
                    if (box.contains(new Vector(num, y, z))) {
                        return true;
                    }
                }
                if (includeInside && values.size() == 2) {
                    return box.getMinX() >= Math.min(values.get(0), values.get(1)) && box.getMinX()
                            <= Math.max(values.get(0), values.get(1));
                }
                return false;
            case 1:
                for (double num : set) {
                    if (box.contains(new Vector(x, num, z))) {
                        return true;
                    }
                }
                if (includeInside && values.size() == 2) {
                    return box.getMinY() >= Math.min(values.get(0), values.get(1)) && box.getMinY()
                            <= Math.max(values.get(0), values.get(1));
                }
                return false;
            default:
                for (double num : set) {
                    if (box.contains(new Vector(x, y, num))) {
                        return true;
                    }
                }
                if (includeInside && values.size() == 2) {
                    return box.getMinZ() >= Math.min(values.get(0), values.get(1)) && box.getMinZ()
                            <= Math.max(values.get(0), values.get(1));
                }
                return false;
        }
    }
}
