package nameerror.skillbuilder.Utils;

import nameerror.skillbuilder.Fundamental.Matter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MovementTrackingHandler {
    private static final HashMap<Matter, Set<TrackedMatter>> trackMap = new HashMap<>();
    private static final HashMap<Matter, TrackedMatter> matterMap = new HashMap<>();

    public static void register(Matter master, TrackedMatter slave) {
        if (!trackMap.containsKey(master)) {
            trackMap.put(master, new HashSet<>());
        }
        trackMap.get(master).add(slave);

    }

    public static void remove(Entity master, TrackedMatter slave) {
        trackMap.get(master).remove(slave);
    }

    public static void clear() {
        trackMap.clear();
    }

    public static Runnable update() {
        for (HashMap.Entry<Matter, Set<TrackedMatter>> entry : trackMap.entrySet()) {
            Location parentLoc = entry.getKey().getLocation();

            for (TrackedMatter tackedObject : entry.getValue()) {
                refresh(tackedObject, parentLoc);
            }
        }
        return null;
    }

    private static void refresh(TrackedMatter matter, Location location) {
        matter.update(location);
    }

    public static TrackedMatter attachTracker(Matter matter) {
        if (matterMap.containsKey(matter)) {
            return matterMap.get(matter);

        } else {
            TrackedMatter tm = new TrackedMatter(matter);
            matterMap.put(matter, tm);
            return tm;
        }
    }
}
