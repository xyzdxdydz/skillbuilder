package nameerror.skillbuilder.Testing.TestModule;

import nameerror.skillbuilder.Math.Shape.Sphere;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Testing.TestModuleTemplate;
import nameerror.skillbuilder.Utils.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FieldTest extends TestModuleTemplate {
    private final Map<String, Function<Player, Integer>> functionMap = new HashMap<>();

    public FieldTest() {
        initTest();
    }

    /**
     * Expected behavior
     * The complete version of each feature must get 'passed' on both 'basic' and 'advanced'
     * convergent:
     *      Basic: // passed
     *          1. all entity have acceleration to the center
     *      Advanced:
     *          1. should follow mathematics rules (interpolating calculation)
     *          2. have mass feature
     * divergent:
     *      Basic: // passed
     *          1. all entity get pushed away.
     *      Advanced:
     *          1. should follow mathematics rules (interpolating calculation)
     * maximum_output_blue:
     *      Basic: //passed
     *          1. matter correctly follows user
     *          2. all entity have acceleration to the center
     *          3. center of force depends on center of field
     *          4. Block transformed to FallingBlock with the same appearance
     *          5. Have nullifier (FallingBlock to itemStack)
     *      Advanced:
     *          1. should follow mathematics rules (interpolating calculation)
     *          2. FallingBlock should have more mass
     *          3. Center of SetSpace is the same as Field
     * velocity_field:
     *      Basic:
     *          1. entity velocity set to output velocity
     */
    private void initTest() {
        // acceleration field
        functionMap.put("convergent", this::convergentTest); // passed
        functionMap.put("divergent", this::divergentTest); // passed
        functionMap.put("maximum_output_blue", this::maximumOutputBlue); // passed

        // velocity field
        functionMap.put("velocity_field", this::velocityField); // passed
    }

    public ArrayList<String> getTestCases() {
        return new ArrayList<>(functionMap.keySet());
    }

    public void test(String testCaseName, Player requester) { functionMap.get(testCaseName).apply(requester); }

    public Integer convergentTest(Player player) {
        Sphere sphere = new Sphere(player.getLocation(), 20);
        AccelerationField field = new AccelerationField(sphere);
        field.setOwner(player);
        field.setApplyToEntities(true);
        field.setFunction((Vector posVector) -> {
//        law of universal gravitation
            Vector center = field.getLocation().toVector();
            Vector dff = center.clone().subtract(posVector);
            if (dff.isZero()) {
                return new Vector(0, 0, 0);
            }
            return dff.normalize().multiply(posVector.distanceSquared(center) > 4.0 ?
                    100.0 / posVector.distanceSquared(center) : 0);
        });
        field.setIgnoreOwner(true);

        Bukkit.getServer().getScheduler().runTaskTimer(SkillBuilder.getPlugin(), field::step, 0, 1);
        return 0;
    }

    public Integer divergentTest(Player player) {
        Sphere sphere = new Sphere(player.getLocation(), 20);
        AccelerationField field = new AccelerationField(sphere);
        field.setOwner(player);
        field.setApplyToEntities(true);
        field.setFunction((Vector posVector) -> {
            Vector center = sphere.getLocation().toVector();
            Vector dff = posVector.clone().subtract(center);
            if (dff.isZero()) {
                return new Vector(0, 0, 0);
            }
            return dff.normalize().multiply(posVector.distanceSquared(center) > 1.0 ?
                    500.0 / posVector.distance(center) : 0);
        });

        field.step();
        return 0;
    }

    public Integer maximumOutputBlue(Player player) {
        Sphere sphere = new Sphere(player.getLocation(), 5);
        AccelerationField field = new AccelerationField(sphere);
        field.setOwner(player);
        field.setApplyToEntities(true);
        field.setApplyToBlocks(true);
        field.setFunction((Vector posVector) -> {
            Vector center = field.getLocation().toVector();
            Vector dff = center.clone().subtract(posVector);
            if (dff.isZero()) {
                return new Vector(0, 0, 0);
            }
            return dff.normalize().multiply(posVector.distance(center) / 2.0f);
        });
        field.setIgnoreOwner(true);
        TrackedMatter tm = MovementTrackingHandler.attachTracker(field);
        tm.setAxisMode("local");
        tm.setLocationOffset(new Vector(0, 0, 10)); // left, above, front
        MovementTrackingHandler.register(player, tm);

        Sphere sphere2 = new Sphere(player.getLocation(), 2);
        FallingBlockNullifierField nullifier = new FallingBlockNullifierField(sphere2);
        nullifier.setOwner(player);
        nullifier.setApplyToEntities(true);

        nullifier.setIgnoreOwner(true);
        TrackedMatter tm2 = MovementTrackingHandler.attachTracker(nullifier);
        tm2.setAxisMode("local");
        tm2.setLocationOffset(new Vector(0, 0, 10)); // left, above, front
        MovementTrackingHandler.register(player, tm2);

        Bukkit.getServer().getScheduler().runTaskTimer(SkillBuilder.getPlugin(), () -> {field.step(); nullifier.step();}, 0, 1);

        return 0;
    }

    public Integer velocityField(Player player) {
        Sphere sphere = new Sphere(player.getLocation(), 2);
        VelocityField field = new VelocityField(sphere);
        field.setOwner(player);
        field.setApplyToEntities(true);
        field.setFunction((Vector posVector) -> new Vector(0, 0, 0));
        field.setIgnoreOwner(true);

        TrackedMatter tm = MovementTrackingHandler.attachTracker(field);
        MovementTrackingHandler.register(player, tm);

        Bukkit.getServer().getScheduler().runTaskTimer(SkillBuilder.getPlugin(), field::step, 0, 1);

        return 0;
    }
}
