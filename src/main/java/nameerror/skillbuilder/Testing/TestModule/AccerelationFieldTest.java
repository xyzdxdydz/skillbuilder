package nameerror.skillbuilder.Testing.TestModule;

import nameerror.skillbuilder.Math.Shape.Sphere;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Testing.TestModuleTemplate;
import nameerror.skillbuilder.Utils.AccelerationField;
import nameerror.skillbuilder.Utils.FallingBlockNullifierField;
import nameerror.skillbuilder.Utils.MovementTrackingHandler;
import nameerror.skillbuilder.Utils.TrackedMatter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class AccerelationFieldTest extends TestModuleTemplate {
    private final Map<String, Function<Player, Integer>> functionMap = new HashMap<>();

    public AccerelationFieldTest() {
        initTest();
    }

    private void initTest() {
        // Add function references to the map
        functionMap.put("convergent", this::convergentTest); // passed
        functionMap.put("divergent", this::divergentTest); // passed
        functionMap.put("maximum_output_blue", this::maximumOutputBlue); // passed
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
}
