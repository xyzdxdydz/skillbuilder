package nameerror.skillbuilder.Fundamental.SkillManagement.TargetSelector;


import javafx.util.Pair;
import nameerror.skillbuilder.Math.BoxCheck;
import nameerror.skillbuilder.Math.Formula;
import nameerror.skillbuilder.Math.Shape.Cylinder;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Search {

    public static List<Entity> treeMapAsList(TreeMap<Double, List<Entity>> treeMap) {
        List<Entity> result = new ArrayList<>();
        while (!treeMap.isEmpty()) {
            List<Entity> entityList = treeMap.firstEntry().getValue();
            result.addAll(entityList);
            treeMap.remove(treeMap.firstKey());
        }
        return result;
    }

    public static List<Pair<Double, Entity>> treeMapAsDistantEntityPairList(TreeMap<Double, List<Entity>> treeMap) {
        List<Pair<Double, Entity>> result = new ArrayList<>();
        while (!treeMap.isEmpty()) {
            List<Entity> entityList = treeMap.firstEntry().getValue();
            for (Entity entity : entityList) {
                result.add(new Pair<>(treeMap.firstKey(), entity));
            }
            treeMap.remove(treeMap.firstKey());
        }
        return result;
    }

    public static TreeMap<Double, List<Entity>> entityInCylinder(List<Entity> entities, Cylinder cylinder, double range, boolean isFinite, boolean perpendicularCal) {
        TreeMap<Double, List<Entity>> entitySort = new TreeMap<>();
        Location start = cylinder.getLocation();
        Vector direction = cylinder.getDirection();

        for (Entity entity : entities) {
            BoxCheck boxCheck = new BoxCheck(entity.getBoundingBox());
            if (boxCheck.isIntersectWithLine(cylinder) || boxCheck.isIntersectWithCylinder(cylinder, true)) {
                Vector startToEntity = entity.getBoundingBox().getCenter().subtract(start.toVector());
                double parallelDistanceFromStart = startToEntity.dot(direction);

                if (!isFinite || (parallelDistanceFromStart >= 0 && parallelDistanceFromStart <= range)) {
                    double x;
                    if (perpendicularCal) {
                        // Perpendicular distant from start;
                        x = Math.sqrt(startToEntity.distanceSquared(new Vector(0,0,0)) -
                                Formula.Pow2(parallelDistanceFromStart));
                    } else {
                        x = parallelDistanceFromStart;
                    }

                    if (!entitySort.containsKey(x)) {
                        List<Entity> newList = new ArrayList<>();
                        newList.add(entity);
                        entitySort.put(x, newList);

                    } else {
                        entitySort.get(x).add(entity);
                    }
                }
            }
        }

        return entitySort;
    }
}