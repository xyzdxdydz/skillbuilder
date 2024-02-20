package nameerror.skillbuilder.Testing.TestModule;

import nameerror.skillbuilder.Fundamental.LegacyEntity;
import nameerror.skillbuilder.Testing.TestModuleTemplate;
import nameerror.skillbuilder.Utils.MovementTrackingHandler;
import nameerror.skillbuilder.Utils.TrackedMatter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TrackedMatterTest extends TestModuleTemplate {
    private final Map<String, Function<Player, Integer>> functionMap = new HashMap<>();

    public TrackedMatterTest() {
        initTest();
    }

    /**
     * Expected behavior
     * The complete version of each feature must get 'passed' on both 'basic' and 'advanced'
     * basic:
     *      Basic: // passed
     *          1. Llama will be on the same location as Creeper.
     *      Advanced: // passed
     *          1. Llama can look around
     * loc_offset:
     *      Basic: // passed
     *          1. Llama will be on location that have global offset with Creeper.
     *      Advanced: // passed
     *          1. Llama can look around
     * follow_rotation:
     *      Basic: // passed
     *          1. Creeper will always on Allay's view with any references.
     *      Advanced: // passed
     *          1. Creeper can look around
     * final_test:
     *      Basic: // passed
     *          1. Blaze will be aligned and always on Allay's view with any references.
     *      Advanced: // passed
     *          1. Blaze can look around
     */
    private void initTest() {
        // Add function references to the map
        functionMap.put("basic", TrackedMatterTest::simpleMovementTracking); // passed
        functionMap.put("loc_offset", TrackedMatterTest::MovementTrackingWithLocOffset); // passed
        functionMap.put("follow_rotation", TrackedMatterTest::MovementTrackingWithFollowRotation); // passed
        functionMap.put("final_test", TrackedMatterTest::MovementTrackingFinalTest); // passed
    }

    public ArrayList<String> getTestCases() {
        return new ArrayList<>(functionMap.keySet());
    }

    public void test(String testCaseName, Player requester) { functionMap.get(testCaseName).apply(requester); }

    private static Integer simpleMovementTracking(Player requester) {
        Location spawnLocation = requester.getLocation().add(requester.getLocation().getDirection().multiply(5));

        World world = requester.getWorld();
        Creeper creeper = world.spawn(spawnLocation, Creeper.class);
        Llama m2 = world.spawn(spawnLocation, Llama.class);
        creeper.setPowered(true);
        MovementTrackingHandler.register(creeper, MovementTrackingHandler.attachTracker(new LegacyEntity(m2)));

        return 1;
    }

    private static Integer MovementTrackingWithLocOffset(Player requester) {
        Location spawnLocation = requester.getLocation().add(requester.getLocation().getDirection().multiply(5));

        World world = requester.getWorld();
        Creeper creeper = world.spawn(spawnLocation, Creeper.class);
        Llama m2 = world.spawn(spawnLocation, Llama.class);
        creeper.setPowered(true);

        TrackedMatter tm = MovementTrackingHandler.attachTracker(new LegacyEntity(m2));
        tm.setAxisMode("global");
        tm.setLocationOffset(new Vector(3, 3, 3));
        MovementTrackingHandler.register(creeper, tm);

        return 1;
    }

    private static Integer MovementTrackingWithFollowRotation(Player requester) {
        Location spawnLocation = requester.getLocation().add(requester.getLocation().getDirection().multiply(5));

        World world = requester.getWorld();
        Allay allay = world.spawn(spawnLocation, Allay.class);
        Creeper creeper = world.spawn(spawnLocation, Creeper.class);

        TrackedMatter tm = MovementTrackingHandler.attachTracker(new LegacyEntity(creeper));
        tm.setAxisMode("local");
        tm.setLocationOffset(new Vector(3, 1, 3)); // left, above, front
        MovementTrackingHandler.register(allay, tm);

        return 1;
    }

    public static void createSlave(LivingEntity master, LivingEntity slave, Vector dirRefVector) {
        TrackedMatter tm = MovementTrackingHandler.attachTracker(new LegacyEntity(slave));
        tm.setAxisMode("local");
        tm.setLocationOffset(dirRefVector); // left, above, front
        MovementTrackingHandler.register(master, tm);
    }

    private static Integer MovementTrackingFinalTest(Player requester) {
        Location spawnLocation = requester.getLocation().add(requester.getLocation().getDirection().multiply(5));

        World world = requester.getWorld();
        Allay allay = world.spawn(spawnLocation, Allay.class);
        Blaze b1 = world.spawn(spawnLocation, Blaze.class);
        Blaze b2 = world.spawn(spawnLocation, Blaze.class);
        Blaze b3 = world.spawn(spawnLocation, Blaze.class);
        Blaze b4 = world.spawn(spawnLocation, Blaze.class);
        Blaze b5 = world.spawn(spawnLocation, Blaze.class);
        Blaze b6 = world.spawn(spawnLocation, Blaze.class);
        Blaze b7 = world.spawn(spawnLocation, Blaze.class);
        Blaze b8 = world.spawn(spawnLocation, Blaze.class);

        createSlave(allay, b1, new Vector(0, 3, 3));
        createSlave(allay, b2, new Vector(3, 3, 3));
        createSlave(allay, b3, new Vector(3, 3, 0));
        createSlave(allay, b4, new Vector(3, 3, -3));
        createSlave(allay, b5, new Vector(0, 3, -3));
        createSlave(allay, b6, new Vector(-3, 3, -3));
        createSlave(allay, b7, new Vector(-3, 3, 0));
        createSlave(allay, b8, new Vector(-3, 3, 3));

        return 1;
    }

}
